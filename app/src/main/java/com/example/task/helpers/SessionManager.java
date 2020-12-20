package com.example.task.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int mode = 0;
    final Activity activity = (Activity) context;

    private static final  String pref_name = "TASK";
    private static final  String is_login = "is_login";
    private static final  String id_user = "id_user";
    private static final  String username = "username";
    private static final  String role = "role";

    public SessionManager(Context context){
        this.context = context;
        pref = context.getSharedPreferences(pref_name, mode);
        editor = pref.edit();
    }

    public void createSession(String id_user, String username, String role){
        editor.putBoolean(is_login, true);
        editor.putString(this.id_user, id_user);
        editor.putString(this.username, username);
        editor.putString(this.role, role);
        editor.commit();
    }

    public Boolean getIsLogin(){return pref.getBoolean(this.is_login, false);}
    public  Boolean is_login(){
        return pref.getBoolean(this.is_login, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(this.pref_name, pref.getString(this.pref_name, null));
        user.put(this.id_user, pref.getString(this.id_user, null));
        user.put(this.username, pref.getString(this.username, null));
        user.put(this.role, pref.getString(this.role, null));
        return  user;
    }

}
