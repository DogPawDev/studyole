package com.board.foodev.account;

import lombok.Data;

@Data
public class SignUpForm {
    //회원가입 할 때 받아올 Form
    private String nickname;

    private String email;

    private String password;
}
