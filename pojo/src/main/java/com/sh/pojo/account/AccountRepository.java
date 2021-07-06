package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.config.db.Repository;

import java.util.List;

public interface AccountRepository extends Repository<Account, Long> {

    boolean save(Account account);

    List<Account> findByAll();

    boolean existsByNickname(SignUpForm form);

    boolean existsByEmail(SignUpForm form);
}
