package com.board.foodev.account;

import com.board.foodev.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AccountRepositroy extends JpaRepository<Account,Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);


    Account findByEmail(String email);
}
