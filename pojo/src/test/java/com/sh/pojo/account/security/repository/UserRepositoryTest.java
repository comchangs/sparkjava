package com.sh.pojo.account.security.repository;

import com.sh.pojo.account.AccountRepository;
import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.security.domain.User;
import com.sh.pojo.config.db.Dao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private static Account account;
    private static Account getAccount;
    private static AccountRepository accountRepository = Dao.getInstance(AccountRepository.class);
    private static UserRepository userRepository = Dao.getInstance(UserRepository.class);
    private static String nickname = "sunhwa21";
    private static String email = "test0715@email.com";
    private static String pwd = "pass1234";

    @BeforeAll
    static void  beforeAll() {
        account = Account.createNewAccount(nickname, email, pwd);
        accountRepository.save(account);

        getAccount = accountRepository.findByNickname(nickname);
    }


    @Test
    @DisplayName("security 정상 저장")
    void saveUserIsOk() {
        User user = User.createUser(getAccount);
        boolean save = userRepository.save(user);
        assertTrue(save);

        User getUser = userRepository.findByAccountId(getAccount.getId());
        assertAll(
                () -> assertEquals(getAccount.getNickname(), getUser.getName(), "security saved user name"),
                () -> assertEquals(getAccount.getId(), getUser.getAccountId(), "security saved user id"),
                () -> assertNull(user.getSessionId(), "before Authentication, the sessionId empty"));

    }


}