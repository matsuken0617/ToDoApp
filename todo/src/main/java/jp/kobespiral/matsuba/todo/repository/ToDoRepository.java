package jp.kobespiral.matsuba.todo.repository;

import org.springframework.stereotype.Repository;

import jp.kobespiral.matsuba.todo.entity.ToDo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

    // 全件取得
    List<ToDo> findAll();
    // ユーザ個人のToDoリスト取得
    List<ToDo> findByMidAndDoneFalse(String mid);
    // ユーザ個人のDoneリスト取得
    List<ToDo> findByMidAndDoneTrue(String mid);
    // 全員のToDoリスト取得
    List<ToDo> findByDoneFalse();
    // 全員のDoneリスト取得
    List<ToDo> findByDoneTrue();
}
