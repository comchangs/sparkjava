package com.sh.pojo;

import com.sh.pojo.account.AccountRepository;
import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountAdminResponse;
import com.sh.pojo.account.domain.form.request.AccountRequest;
import com.sh.pojo.common.Page;
import com.sh.pojo.config.db.Dao;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest {

    Account account;
    AccountRepository accountRepository = Dao.getInstance(AccountRepository.class);
    private String nickname = "sunhwa07";
    private String email = "test07@email.com";
    private String pwd = "pass1234";

    @BeforeEach
    void beforeEach() {
        account = Account.createNewAccount(nickname, email, pwd);
    }

//    @AfterEach
//    void afterEach() {
//        accountRepository.deleteAll();
//    }

    @Test
    @DisplayName("account_저장,조회, 비밀번호변경")
    void accountDaoCRUD()  {
        // 저장, 조회
        accountRepository.save(account);
        Account getAccount = accountRepository.findByNickname(account.getNickname());
        assertNotNull(getAccount);
        assertEquals(getAccount.getEmail(), account.getEmail());

        boolean result = accountRepository.existsByNickname("testNew_Sunhwa");
        assertTrue(result);

        // 수정
        getAccount.updatePassword("TestOf_changePwd");
        boolean  resultOfUpdatePwd = accountRepository.updatePassword(getAccount);
        assertTrue(resultOfUpdatePwd);
        // 수정 데이터 확인
        Account checkResult = (Account) accountRepository.findById(getAccount.getId());
        assertNotEquals(checkResult.getPassword(), getAccount.getPassword());
    }

    @Test
    @DisplayName("account_저장,조회")
    void accountDaoCR()  {
        // 저장, 조회
        Account newNicknameAccount = Account.createNewAccount("testNew_Sunhwa", email, pwd);
        accountRepository.save(newNicknameAccount);
        Account getAccount = accountRepository.findByNickname(newNicknameAccount.getNickname());
        assertNotNull(getAccount);
        assertEquals(getAccount.getEmail(), newNicknameAccount.getEmail());

        Account checkResult = accountRepository.findById(getAccount.getId());
        assertEquals(checkResult.getNickname(), getAccount.getNickname());
    }

    @Test
    @DisplayName("account_정보 수정")
    void updateAccountInfo() {
        accountRepository.save(account);
        Account getAccount = accountRepository.findByNickname(account.getNickname());
        assertNotNull(getAccount);
        assertEquals(getAccount.getEmail(), account.getEmail());

        AccountRequest request = new AccountRequest();
        request.setEmail("changeTest@email.com");
        request.setNickname("change_test_nickname");
//        request.setAlarmChangePassword(true);  // 관리자만
        request.setReceiveEmail(false);
        getAccount.update(request);

        Boolean resultBoolean = accountRepository.update(getAccount);
        assertTrue(resultBoolean);

        Account testResult = accountRepository.findById(getAccount.getId());
        assertAll(
                () -> assertEquals(testResult.getEmail(), request.getEmail(),"email 확인"),
                () -> assertNotEquals(testResult.getEmail(), account.getEmail(),"email 변경 확인"),
                () -> assertEquals(testResult.getNickname(), request.getNickname(),"nickname 확인"),
                () -> assertFalse(testResult.isReceiveEmail(),"이메일 수신 설정")
                );
    }

    @Test
    @DisplayName("account_목록")
    void accountDadList() {
        Page page = new Page(1,10);
        List<AccountAdminResponse> byAll = accountRepository.findByAll(page);
        assertTrue(byAll.size()>2);
    }

    @Disabled
//    @Test
    @DisplayName("account_목록 삭제")
    void accountDeleteAll() {
        Boolean result = accountRepository.deleteAll();
        assertTrue(result);
        List<AccountAdminResponse> byAll = accountRepository.findByAll(new Page(1,10));
        assertTrue(byAll.size() == 0);

    }
}