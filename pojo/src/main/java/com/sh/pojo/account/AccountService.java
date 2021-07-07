package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountRequest;
import com.sh.pojo.account.domain.form.AccountResponse;
import com.sh.pojo.account.domain.form.PasswordForm;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.account.exception.AccountNotFoundException;
import com.sh.pojo.account.exception.AccountWrongPasswordException;
import com.sh.pojo.common.DatePattern;
import com.sh.pojo.config.PasswordHashing;
import com.sh.pojo.config.db.DaoFactory;

import java.util.List;

public class AccountService {

    private final AccountRepository accountRepository;

    private static final AccountService accountService = new AccountService(DaoFactory.getBean(AccountRepository.class));

    private AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public static AccountService getBean(){
        return accountService;
    }

    public boolean isValidSignUp(SignUpForm form) {
        return !accountRepository.existsByNickname(form) &&
                !accountRepository.existsByEmail(form);
    }

    public boolean joined(SignUpForm form) {
        Account newAccount = Account.createNewAccount(form.getNickname(), form.getEmail(), form.getPassword());
        return this.save(newAccount);
    }

    private boolean save(Account account) {
        return accountRepository.save(account);
    }

    public boolean isPasswordMach(Account account, String inputPassword) {
        return account.getPassword().equals(PasswordHashing.encode(inputPassword));
    }

    private Account getAccountById(Long id) {
        Account getAccount = (Account) accountRepository.findById(id);
        if(getAccount == null ) throw new AccountNotFoundException();
        return getAccount;
    }

    public AccountResponse getAccount(Long id) {
        return response(getAccountById(id));
    }

    public boolean signOut(Long id) {
        getAccountById(id);
        int result = accountRepository.deleteById(id);
        return result > 0;
    }

    public void modifyPassword(PasswordForm password) {
        Account getAccount = getAccountById(password.getId());
        getAccount.updatePassword(password.getPassword());
        this.updatePassword(getAccount);
    }

    private void updatePassword(Account account) {
        if(!accountRepository.updatePassword(account)) throw new RuntimeException("Server 500");
    }

    public AccountResponse modifyAccountInfo(AccountRequest account) {
        //TODO nickname, email 중복체크
        Account getAccount = getAccountById(account.getId());
        getAccount.update(account);
        this.updateAccountInfo(getAccount);
        return response(getAccount);
    }

    private void updateAccountInfo(Account account) {
        if(!accountRepository.update(account)) throw new RuntimeException("Server 500");
    }
    public List<Account> accounts(){
        return accountRepository.findByAll();
    }

    private AccountResponse response(Account account) {
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setId(account.getId());
        accountResponse.setNickname(account.getNickname());
        accountResponse.setEmail(account.getEmail());
        accountResponse.setJoinedAt(account.getJoinedAt());
        accountResponse.setReceiveEmail(account.isReceiveEmail());
        return accountResponse;
//        return Header.OK(userApiResponse);
    }


}
