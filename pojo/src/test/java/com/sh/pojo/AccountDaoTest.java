package com.sh.pojo;

import com.sh.pojo.account.AccountRepository;
import com.sh.pojo.account.domain.Account;
import com.sh.pojo.config.db.DaoFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {

    Account account;
    AccountRepository accountRepository = DaoFactory.getInstance(AccountRepository.class);
    private String nickname = "sunhwa07";
    private String email = "test07@email.com";
    private String pwd = "pass1234";

    @BeforeEach
    void beforeEach() {
        account = Account.createNewAccount(nickname, email, pwd);
    }

    @AfterEach
    void afterEach() {
        accountRepository.deleteAll();
    }

    @Test
    @DisplayName("사용자 - 저장, 조건조회, id 조회, 비밀번호 수정")
    void accountDaoCRUD()  {
        // 저장, 조회
        accountRepository.save(account);
        Account getAccount = accountRepository.findByNickname(account.getNickname());
        assertNotNull(getAccount);
        assertEquals(getAccount.getEmail(), account.getEmail());

        // 수정
        getAccount.updatePassword("TestOf_changePwd");
        boolean  resultOfUpdatePwd = accountRepository.updatePassword(getAccount);
        assertTrue(resultOfUpdatePwd);
        // 수정 데이터 확인
        Account checkResult = (Account) accountRepository.findById(getAccount.getId());
        assertNotEquals(checkResult.getPassword(), getAccount.getPassword());
    }
}