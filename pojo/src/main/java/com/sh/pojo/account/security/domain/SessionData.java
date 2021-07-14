package com.sh.pojo.account.security.domain;

public class SessionData {

    private String sessionId;

    private Long userId;

    public static SessionData create(Authentication authentication) {
        SessionData data = new SessionData();
        data.setUserId(authentication.getUserId());
        data.setSessionId(authentication.getSessionId());
        return data;
    }

    public String getSessionId() {
        return sessionId;
    }

    private void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    private void setUserId(Long userId) {
        this.userId = userId;
    }
}
