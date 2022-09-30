package com.example.training.thread;

import java.io.Serializable;
/**
 * This class is thread safe to manage the user context invoking the APIs
 * This class is set once the authentication is done
 * This class can be used to get user info
 */
public class UserContext implements Serializable {
    private static final ThreadLocal<UserContext> USER_CONTEXT = ThreadLocal
            .withInitial(() -> new UserContext("UNKNOW"));
    private String userName;
    public UserContext(String userName) {
        this.userName = userName;
    }
    public static UserContext get() {
        return USER_CONTEXT.get();
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void unload() {
        USER_CONTEXT.remove(); // Compliant
    }
}
