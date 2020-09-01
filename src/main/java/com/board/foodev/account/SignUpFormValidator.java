package com.board.foodev.account;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



@Component
@RequiredArgsConstructor //private final 선언된 멤버의 생성자를 자동으로 만들어 준다.
public class SignUpFormValidator implements Validator {

    private final AccountRepositroy accountRepositroy;  //requriargCons 해당 에노팅션으로 인해 자동으로 주입 받음


    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        SignUpForm signUpForm = (SignUpForm)errors;
        if(accountRepositroy.existsByEmail(signUpForm.getEmail())){
            errors.rejectValue("email","invalid.email",new Object[]{signUpForm.getEmail()},"이미 사용중인 이메일 입니다.");
        }

        if(accountRepositroy.existsByNickname(signUpForm.getNickname())){
            errors.rejectValue("nickname","invalid.nickname",new Object[]{signUpForm.getNickname()},"이미 사용중인 닉네임 입니다.");
        }


    }
}
