package com.sh.pojo.account.security;

import com.sh.pojo.account.security.domain.User;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityContext {

    private static final SecurityContext SECURITY_CONTEXT = new SecurityContext();
    private Set<String> cookie = ConcurrentHashMap.newKeySet();
    private SecurityContext() {}

    public static SecurityContext getContext(){
        return SECURITY_CONTEXT;
    }

    public void setAuthentication(String sessionId){
        cookie.add(sessionId);
    }

    public boolean isAuthenticated(User user) {
        return cookie.contains("sessionId");
    }

    // AccountService login시 setAuthentication 등록 (새 token 발급 -> 새 쿠키 발급)

    // 새 token UserRepository에 저장 => SecurityService 통해서
    // 저장 정보 SecurityContext map 에 로그인 동안 보관 - 로그아웃시 로그아웃, 쿠키 삭제


}
