package com.example.task.helpers;

public class UrlHelper {
    public static String ip_address = "http://192.168.1.2";
    public static String base_url = ip_address+"/task/web";
    public static String base_api = base_url+"/api";
    public static String base_user = base_api+"/user";
    public static String base_task = base_api+"/task";

    public static String login = base_user+"/login";
    public static String ajukan_sub_task = base_api+"/sub-task/ajukan/";
    public static String view_sub_task = base_api+"/sub-task/view";
//    public static String create_task = base_task+"/create";
//    public static String update_task = base_task+"/update";
//    public static String delete_task = base_task+"/delete";
    public static String asset = base_url+"/assets/";
}
