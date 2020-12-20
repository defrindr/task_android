package com.example.task.helpers;

public class UrlHelper {
    public static String ip_address = "http://192.168.1.21";
    public static String base_url = ip_address+"/task/web/api";
    public static String base_user = base_url+"/user";
    public static String base_task = base_url+"/task";
    public static String login = base_user+"/login";
    public static String create_task = base_task+"/create";
    public static String update_task = base_task+"/update";
    public static String delete_task = base_task+"/delete";
}
