package com.example.form;

import jakarta.validation.GroupSequence;

// バリデーションの順を設定する。左から設定されたインタフェースの順番でバリデーションが実施される
@GroupSequence({ValidGroup1.class, ValidGroup2.class})
public interface GroupOrder {

}
