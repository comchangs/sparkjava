package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountResponse;
import com.sh.pojo.account.domain.form.PasswordForm;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.account.exception.AccountNotFoundException;
import com.sh.pojo.account.exception.AccountWrongPasswordException;
import com.sh.pojo.config.PasswordHashing;
import com.sh.pojo.config.db.DaoFactory;

import java.time.LocalDate;
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
        Account account = new Account();
        account.setNickname(form.getNickname());
//        account.setPassword(PasswordHashing.encode(form.getPassword()));
        account.setPassword(form.getPassword());  // TODO hashing
        account.setEmail(form.getEmail());
        account.setJoinedAt(LocalDate.now());
        return this.save(account);

    }

    private boolean save(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> accounts(){
        return accountRepository.findByAll();
    }

    public boolean isPasswordMach(Account account, String inputPassword) {
        return account.getPassword().equals(PasswordHashing.encode(inputPassword));
    }

    public boolean signOut(Long id) {
        if(getAccountById(id) == null) return false;  // todo exception or 사용자 확인..
        int result = accountRepository.deleteById(id);
        return result > 0;
    }

    public Account getAccountById(Long id) {
        Account getAccount = (Account) accountRepository.findById(id);
        if(getAccount == null ) throw new AccountNotFoundException();
        return getAccount;
    }

    public void updatePassword(PasswordForm password) {
        Account getAccount = getAccountById(password.getId());
        if (!isPasswordMach(getAccount, password.getPassword())) throw new AccountWrongPasswordException();
    }

    public AccountResponse update(Account account) {
    }
}
