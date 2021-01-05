package com.example.task.helpers;

import java.util.ArrayList;

public class Constant {
    public static String ROLE_CEO = "2";
    public static String ROLE_DIRECTOR = "3";
    public static String ROLE_MANAGER = "4";
    public static String ROLE_STAFF = "5";
    public static ArrayList<String> LIST_ROLE = new ArrayList<String>(){{
        add("DUMMY");
        add("Super Administrator");
        add("CEO");
        add("Director");
        add("Manager");
        add("Staff");
    }};

    public static ArrayList<String> LIST_TASK_STATUS = new ArrayList<String>(){{
        add("BARU");
        add("DIASSIGN");
        add("PENDING");
        add("CLOSED");
    }};

}
