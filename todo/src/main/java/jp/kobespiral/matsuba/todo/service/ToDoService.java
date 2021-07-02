package jp.kobespiral.matsuba.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.matsuba.todo.dto.ToDoForm;
import jp.kobespiral.matsuba.todo.entity.ToDo;
import jp.kobespiral.matsuba.todo.exception.ToDoAppException;
import jp.kobespiral.matsuba.todo.repository.ToDoRepository;

@Service
public class ToDoService {
    @Autowired
    ToDoRepository tdRepo;

    /**
     * メンバーmidが新しくToDoを作成する
     * @param mid
     * @param form
     * @return
     */
    public ToDo createToDo(String mid, ToDoForm form) {
        ToDo td = form.toEntity(mid);
        return tdRepo.save(td);
    }

    /**
     * 番号を指定してToDoを取得
     * @param seq
     * @return
     */
    public ToDo getToDo(Long seq) {
        ToDo td = tdRepo.findById(seq).orElseThrow(
            () -> new ToDoAppException(ToDoAppException.NO_SUCH_TODO_EXISTS, "No such todo exists.")
        );
        return td;
    }

    /**
     * midのToDoリストを取得
     * @param mid
     * @return
     */
    public List<ToDo> getToDoList(String mid){
        List<ToDo> tdList = tdRepo.findByMidAndDoneFalse(mid);
        return tdList;
    }

    /**
     * midのDoneリストを取得
     * @param mid
     * @return
     */
    public List<ToDo> getDoneList(String mid){
        List<ToDo> dList = tdRepo.findByMidAndDoneTrue(mid);
        return dList;
    }

    /**
     * 全員のToDoリストを取得
     * @return
     */
    public List<ToDo> getToDoList(){
        List<ToDo> tdList = tdRepo.findByDoneFalse();
        return tdList;
    }

    /**
     * 全員のDoneリストを取得
     * @return
     */
    public List<ToDo> getDoneList(){
        List<ToDo> tdList = tdRepo.findByDoneTrue();
        return tdList;
    }

    /**
     * 指定したToDoをDoneに変更
     */
    public ToDo checkDone(Long seq) {
        ToDo td = getToDo(seq);
        td.setDone(true);
        td.setDoneAt(new Date());
        return tdRepo.save(td);
    }

    /**
     * 指定したToDoのタイトルを更新
     * @param mid
     * @param seq
     * @param form
     * @return
     */
    public ToDo updateToDo(String mid, Long seq, ToDoForm form) {
        ToDo td = getToDo(seq);
        td.setTitle(form.toEntity(mid).getTitle());
        return tdRepo.save(td);
    }

    /**
     * 指定したToDoを削除
     * @param mid
     * @param seq
     */
    public void deleteToDo(String mid, Long seq) {
        tdRepo.deleteById(seq);
    }
}
