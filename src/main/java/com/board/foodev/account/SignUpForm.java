package com.board.foodev.account;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class SignUpForm {
    //회원가입 할 때 받아올 Form

    @NotBlank
    @Length(min = 3,max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}",message = "특수 문자는 사용할 수 없습니다.") //정규식을 사용해 패턴을 정의 한다.
    private String nickname;


    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max=50)
    private String password;
}
