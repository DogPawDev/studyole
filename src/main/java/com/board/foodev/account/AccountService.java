package com.board.foodev.account;

import com.board.foodev.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepositroy accountRepositroy;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    public void processNewAccount(SignUpForm signUpForm) {

        Account newAccount = saveNewAccount(signUpForm);

        newAccount.generaateEmailCheckToken();

        sendSignUpConfirmEmail(newAccount);
    }

    private Account saveNewAccount(@ModelAttribute @Valid SignUpForm signUpForm) {
        Account account = Account.builder()
                .email(signUpForm.getEmail())
                .nickname(signUpForm.getNickname())
                .password(passwordEncoder.encode(signUpForm.getPassword())) //해시 인코딩 성공
                .studyCreateByWeb(true)
                .studyUpdateByWeb(true)
                .build();

        return accountRepositroy.save(account);
    }

    private void sendSignUpConfirmEmail(Account newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getEmail());
        mailMessage.setSubject("스터딛 올래 , 회원가입 인증"); // 제목
        mailMessage.setText("check-email-token?token="+newAccount.getEmailCheckToken()+"&email="+newAccount.getEmail());//메일 본문
        javaMailSender.send(mailMessage);
    }


}
