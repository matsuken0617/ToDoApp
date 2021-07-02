package jp.kobespiral.matsuba.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.kobespiral.matsuba.todo.dto.MemberForm;
import jp.kobespiral.matsuba.todo.entity.Member;
import jp.kobespiral.matsuba.todo.exception.ToDoAppException;
import jp.kobespiral.matsuba.todo.service.MemberService;
import jp.kobespiral.matsuba.todo.service.ToDoService;

/**
 * Memberの操作，CRUDを行うAPI
 */
@RestController
@RequestMapping("/api")
public class MemberRestController {
    @Autowired
    MemberService memberService;
    @Autowired
    ToDoService toDoService;

    /**
     * C: Memberを作成する
     * @param form
     * @return
     */
    @PostMapping("/users")
    Member createMember(@Validated @RequestBody MemberForm form) {
        return memberService.createMember(form);
    }
    
    /**
     * R: Memberを確認する
     * @param mid
     * @return
     */
    @GetMapping("/{mid}")
    Member showMember(@PathVariable String mid) {
        return memberService.getMember(mid);
    }

    /**
     * R: Member一覧を確認する
     * @return
     */
    @GetMapping("/users")
    List<Member> showAllMember(){
        return memberService.getAllMembers();
    }

    /**
     * D: Memberを削除する
     * @param mid
     * @return
     */
    @DeleteMapping("/{mid}")
    boolean deleteMember(@PathVariable String mid) {
        memberService.deleteMember(mid);
        return true;
    }

    /*--------------------- エラーハンドラー -----------------------------*/
    /*
     * 本当は@RestControllerAdviceにまとめて書きたいが，@ControllerAdviceと競合するので，
     * Controllerに書いている．
     */
    @ExceptionHandler(ToDoAppException.class)
    public ResponseEntity<Object> handleToDoException(ToDoAppException ex) {
        HttpStatus status;
        switch (ex.getCode()) {
            // 存在しない系例外
            case ToDoAppException.NO_SUCH_MEMBER_EXISTS:
            case ToDoAppException.NO_SUCH_TODO_EXISTS:
                status = HttpStatus.NOT_FOUND;
                break;
            // パラメタ異常系例外
            case ToDoAppException.MEMBER_ALREADY_EXISTS:
            case ToDoAppException.INVALID_MEMBER_INFO:
            case ToDoAppException.INVALID_TODO_INFO:
                status = HttpStatus.BAD_REQUEST;
                break;
            // 認可失敗系例外
            case ToDoAppException.INVALID_MEMBER_OPERATION:
            case ToDoAppException.INVALID_TODO_OPERATION:
                status = HttpStatus.FORBIDDEN;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(ex, status);
    }

    /* -- バリデーション失敗 -- */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }

    /* -- その他の例外 -- */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
    
}
