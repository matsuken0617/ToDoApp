package jp.kobespiral.matsuba.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import jp.kobespiral.matsuba.todo.dto.LoginForm;
import jp.kobespiral.matsuba.todo.dto.ToDoForm;
import jp.kobespiral.matsuba.todo.entity.ToDo;
import jp.kobespiral.matsuba.todo.service.MemberService;
import jp.kobespiral.matsuba.todo.service.ToDoService;

@Controller
public class ToDoController {
    @Autowired
    ToDoService tService;
    @Autowired
    MemberService mService;
    
    /**
     * トップページ HTTP-GET /index
     * @return
     */
    @GetMapping("/index")
    String showIndex(Model model) {
        LoginForm form = new LoginForm();
        model.addAttribute("LoginForm", form);
        return "index";
    }

    /**
     * ログイン処理，リダイレクト
     * @param form
     * @param model
     * @return
     */
    @PostMapping("/login")
    String loginUser(@ModelAttribute(name = "LoginForm") LoginForm form, Model model) {
        String mid = form.getMid();
        // メンバー存在チェック
        mService.getMember(mid);
        return "redirect:/" + mid + "/list";
    }

    /**
     * ログイン後の画面
     * @param mid
     * @param model
     * @return
     */
    @GetMapping("/{mid}/list")
    String showToDoList(@PathVariable String mid, Model model) {
        // ToDoリスト
        List<ToDo> todos = tService.getToDoList(mid);
        model.addAttribute("todos", todos);
        // Doneリスト
        List<ToDo> dones = tService.getDoneList(mid);
        model.addAttribute("dones", dones);
        // ToDo登録フォーム
        ToDoForm form = new ToDoForm();
        model.addAttribute("ToDoForm", form);
        return "list";
    }

    /**
     * ToDoを追加する
     */
    @PostMapping("/{mid}/list")
    String addToDo(
        @ModelAttribute(name = "ToDoForm") ToDoForm form, 
        @PathVariable String mid, 
        Model model
    ) {
        tService.createToDo(mid, form);
        return "redirect:/" + mid + "/list";
    }

    @PutMapping("/{mid}/list")
    String 

}
