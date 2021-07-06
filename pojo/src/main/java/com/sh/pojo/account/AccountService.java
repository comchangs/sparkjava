package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.SignUpForm;
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
        account.setPassword(form.getPassword());
        account.setEmail(form.getEmail());
        account.setJoinedAt(LocalDate.now());
        return this.save(account);

    }

    public List<Account> accounts(){
        return accountRepository.findByAll();
    }

    private boolean save(Account account) {
        return accountRepository.save(account);
    }
}
