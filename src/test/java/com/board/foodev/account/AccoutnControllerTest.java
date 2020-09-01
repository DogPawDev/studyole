package com.board.foodev.account;

import com.board.foodev.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AccoutnControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AccountRepositroy accountRepositroy;

    @MockBean
    JavaMailSender javaMailSender;

    @DisplayName("회원 가입 화면 보이는지 테스트")
    @Test
    void signUpForm() throws Exception{
        mockMvc.perform(get("/sign-up"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"))
                .andExpect(model().attributeExists("signUpForm"))
        ; //스프링 시큐리티 설정을 하지 않으면 깨진다. - SecurtiyConfig

    }


    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    void signUpSubmit_with_worng_input() throws Exception{
        mockMvc.perform(post("/sign-up")
                .param("nickname","foodev")
                .param("email","emaile..")
                .param("password","12345")
                .with(csrf())) //mock을 통해 사용할 떄는 해당 토큰이 있어야한다.
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
        //스프링 시큐리티에서 CSRF 타 사이트에서 나의 사이트에 이상한 파라미터를 계속 보내는 것을 방지하는 기술이 사용됨.

    }
    @DisplayName("회원 가입 처리 - 입력값 정")
    @Test
    void signUpSubmit_with_correct_input() throws Exception{
        mockMvc.perform(post("/sign-up")
                .param("nickname","foodev")
                .param("email","foodev@email.com")
                .param("password","12345123")
                .with(csrf())) //mock을 통해 사용할 떄는 해당 토큰이 있어야한다.
                .andExpect(status().is3xxRedirection());

            assertTrue(accountRepositroy.existsByEmail("foodev@email.com"));
            then(javaMailSender).should().send(any(SimpleMailMessage.class));
            //해당 타입에서 센드 호출 됨>?
    }

}
