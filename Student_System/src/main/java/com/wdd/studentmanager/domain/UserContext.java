package com.wdd.studentmanager.domain;

import org.json.JSONObject;

public class UserContext {
    private static final ThreadLocal<JSONObject> userHolder = new ThreadLocal<>();

    public static void setUser(JSONObject user) {
        userHolder.set(user);
    }

    public static JSONObject getUser() {
        return userHolder.get();
    }

    public static void clear() {
        userHolder.remove();
    }
}

