package com.sh.pojo.account;

import com.sh.pojo.account.domain.Account;
import com.sh.pojo.account.domain.form.AccountAdminResponse;
import com.sh.pojo.common.Page;
import com.sh.pojo.config.db.Repository;

import java.util.List;

public interface AccountRepository extends Repository<Account, Long> {

    List<AccountAdminResponse> findByAll(Page pageNumber);

    Account findByNickname(String nickname);

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);

    Boolean updatePassword(Account account);
}
