package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountAdminResponse;
import com.sh.pojo.account.domain.form.SignUpForm;
import com.sh.pojo.common.Page;
import com.sh.pojo.config.db.Repository;

import java.util.List;

public interface AccountRepository extends Repository<Account, Long> {

    List<AccountAdminResponse> findByAll(Page pageNumber);

    Account findByNickname(String nickname);

    boolean existsByNickname(SignUpForm form);

    boolean existsByEmail(SignUpForm form);

    Boolean updatePassword(Account account);
}
