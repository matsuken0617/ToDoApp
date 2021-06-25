package jp.kobespiral.matsuba.todo.dto;

import java.util.Date;

import jp.kobespiral.matsuba.todo.entity.ToDo;
import lombok.Data;

@Data
public class ToDoForm {

    // タイトル
    String title;

    public ToDo toEntity(String mid) {
        ToDo td = new ToDo(
            null,      //通し番号
            title,     //題目
            mid,       //作成者
            false,     //完了フラグ
            new Date(),   //作成日時
            null      //完了日時
        );
        return td;
    }
    
}
