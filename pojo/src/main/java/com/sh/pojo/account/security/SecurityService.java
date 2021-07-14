package com.sh.pojo.account.security;

import com.sh.pojo.account.security.domain.User;

public abstract class SecurityService {

    public SecurityService() {
    }

    public abstract User loadUserByUsername(String loginId);

}
