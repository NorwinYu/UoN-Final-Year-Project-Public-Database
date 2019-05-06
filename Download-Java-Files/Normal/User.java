package com.example.usk.glotus_final.System.loginFiles;

import java.io.UnsupportedEncodingException;

import okio.ByteString;

public class User {
    public static String username;
    public static String password;
    public static boolean process=false;
    public static String cred;



    public User(String username, String password){
        this.username=username;
        this.password=password;
        process=true;
    }

    public void setUsername(String username){
        this.username=username;
    }
    public void setPassword(String password){
        this.password=password;
    }

    //Шифрование на Base64 для авторизации в сайт
    public static String getCredential(){
        try {
            String usernameAndPassword =  username+ ":" + password;
            byte[] bytes = usernameAndPassword.getBytes("UTF-8");
            String encoded = ByteString.of(bytes).base64();
            cred="Basic " + encoded;
            return "Basic " + encoded;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }

    public static void setCred(String cred) {
        User.cred = cred;
    }
}
