package com.sh.pojo.account.security;

import com.sh.pojo.account.security.domain.Authentication;
import com.sh.pojo.account.security.domain.SessionData;
import com.sh.pojo.account.security.domain.User;
import com.sh.pojo.account.security.repository.UserRepository;
import com.sh.pojo.config.db.Dao;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityContext {

    private final UserRepository userRepository;

    private static final SecurityContext SECURITY_CONTEXT = new SecurityContext(Dao.getInstance(UserRepository.class));
    private Set<String> cookie = ConcurrentHashMap.newKeySet();
    private SecurityContext(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static SecurityContext getContext(){
        return SECURITY_CONTEXT;
    }

    public SessionData isAuthenticated(User user) {
        Authentication authentication = new Authentication(user);
        if(user.isLogined()){
            userRepository.deleteById(user.getAccountId());
            //authentication.alarmDuplicatedLogin();         // ... SessionData 로
        }

        int count = 0;
        while (cookie.contains(authentication.getSessionId())){
            cookie.remove(authentication.getSessionId());
            authentication.changeToken();
            count++;
            if(count >=10) break;
        }

        user.newSessionId(authentication.getSessionId());
        cookie.add(authentication.getSessionId());
        userRepository.save(user);
        return SessionData.create(authentication);
    }

}
