package com.board.foodev.account;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/sign-up")
    public String signUpForm(Model model){
        model.addAttribute(new SignUpForm());
       // model.addAttribute("signUpForm",new SignUpForm());// 생략이 가능함 SignUpForm 카멜 케이스로 넘어간다.


        return "account/sign-up"; //스프링 부트가 제공하는 자동설정으로 인해 탬플릿 디렉토리 밑에서 부터 찾는다.
    }
}
