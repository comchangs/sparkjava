package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.*;
import com.sh.pojo.account.domain.form.request.AccountRequest;
import com.sh.pojo.account.domain.form.request.LoginRequest;
import com.sh.pojo.account.domain.form.request.PasswordForm;
import com.sh.pojo.account.domain.form.request.SignUpForm;
import com.sh.pojo.account.exception.AccountNotFoundException;
import com.sh.pojo.account.security.SecurityContext;
import com.sh.pojo.account.security.SecurityService;
import com.sh.pojo.account.security.domain.SessionData;
import com.sh.pojo.account.security.domain.User;
import com.sh.pojo.common.Page;
import com.sh.pojo.config.PasswordHashing;
import com.sh.pojo.config.db.Dao;

import java.util.List;
import java.util.Objects;

public class AccountService extends SecurityService {

    private final AccountRepository accountRepository;

    private static final AccountService accountService = new AccountService(Dao.getInstance(AccountRepository.class));

    private AccountService(AccountRepository accountRepository){
        super();
        this.accountRepository = accountRepository;
    }

    public static AccountService getBean(){
        return accountService;
    }


    public boolean isPasswordMach(Account account, String inputPassword) {
        return account.getPassword().equals(PasswordHashing.encode(inputPassword));
    }

    public SessionData login(LoginRequest loginRequest) {
        String nickOrEmail = loginRequest.getLoginId();
        User getUser = loadUserByUsername(nickOrEmail);
        return SecurityContext.getContext().isAuthenticated(getUser);
    }

    @Override
    public User loadUserByUsername(String nickOrEmail) {
        Account getAccount = accountRepository.findByNickname(nickOrEmail);
        if (getAccount == null) {
            getAccount = accountRepository.findByEmail(nickOrEmail);
        }
        if(Objects.isNull(getAccount)) throw new AccountNotFoundException();
        return User.createUser(getAccount);
    }


    public boolean isValidSignUp(SignUpForm form) {
        return !accountRepository.existsByNickname(form.getNickname()) &&
                !accountRepository.existsByEmail(form.getEmail());
    }

    public boolean joined(SignUpForm form) {
        Account newAccount = Account.createNewAccount(form.getNickname(), form.getEmail(), form.getPassword());
        return this.save(newAccount);
    }

    private boolean save(Account account) {
        return accountRepository.save(account);
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
        Boolean result = accountRepository.deleteById(id);
        return result;
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
        //TODO nickname, email ????????????
        Account getAccount = getAccountById(account.getId());
        getAccount.update(account);
        this.updateAccountInfo(getAccount);
        return response(getAccount);
    }

    private void updateAccountInfo(Account account) {
        if(!accountRepository.update(account)) throw new RuntimeException("Server 500");
    }

    public List<AccountAdminResponse> getAccountList(Page page) {
        return accountRepository.findByAll(page);
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
