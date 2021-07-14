package com.sh.pojo.account.security;

import com.sh.pojo.account.security.domain.Authentication;
import com.sh.pojo.account.security.domain.User;
import com.sh.pojo.account.security.repository.UserRepository;

public abstract class SecurityService {

    UserRepository userRepository;

    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SecurityService() {

    }

    public void usernameMakeToken(User user){

        this.userRepository.findByAccountId(user.getAccountId());

//        return authentication;
    }

    public abstract User loadUserByUsername(String loginId);

}
