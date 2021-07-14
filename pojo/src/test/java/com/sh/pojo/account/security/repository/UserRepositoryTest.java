package com.sh.pojo.account.security.repository;

import com.sh.pojo.account.AccountRepository;
import com.sh.pojo.account.domain.Account;
import com.sh.pojo.config.db.Dao;
import org.junit.jupiter.api.BeforeAll;

class UserRepositoryTest {

    Account account;
    AccountRepository accountRepository = Dao.getInstance(AccountRepository.class);
    private String nickname = "sunhwa07";
    private String email = "test07@email.com";
    private String pwd = "pass1234";

    @BeforeAll
    void beforeEach() {
        account = Account.createNewAccount(nickname, email, pwd);
        accountRepository.save(account);
    }




}