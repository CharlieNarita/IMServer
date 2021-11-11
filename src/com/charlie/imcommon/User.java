package com.charlie.imcommon;

import java.io.Serializable;

/**
 * represent user contains user information
 *
 * @author AC
 * @version 1.0
 * @date 10/13/2021
 */
public class User implements Serializable {
    private static final long serialVersionUID = 6721013918260079059L;
    private String userId;
    private String password;

    public User(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
