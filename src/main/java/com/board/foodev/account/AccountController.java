package com.board.foodev.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AccountController {


    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.addValidators(signUpFormValidator);
        //signUpForm 객체의 검증을 해주는 부부닝다. signUpSubmit에서 사용되는 SignUpForm 객체의
        // 카멜 케이스로 자동으로 적용이 되기 때문에 이메일, 닉네임 검증이 이부분을 통해 가능해진다.
    }

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute(new SignUpForm());
       // model.addAttribute("signUpForm",new SignUpForm());// 생략이 가능함 SignUpForm 카멜 케이스로 넘어간다.


        return "account/sign-up"; //스프링 부트가 제공하는 자동설정으로 인해 탬플릿 디렉토리 밑에서 부터 찾는다.
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors){
        if (errors.hasErrors()){
            System.out.println(errors.getAllErrors());
            return "account/sign-up";
        }



        return "redirect:/";
    }
}
