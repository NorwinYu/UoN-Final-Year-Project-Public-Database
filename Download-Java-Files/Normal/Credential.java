package com.example.usk.glotus_final.System.Encryption;

import java.io.UnsupportedEncodingException;

import okio.ByteString;

public class Credential {

    public String getCredential(String login,String pass){
        try {
            String usernameAndPassword =  login+ ":" + pass;
            byte[] bytes = usernameAndPassword.getBytes("UTF-8");
            String encoded = ByteString.of(bytes).base64();
            return "Basic " + encoded;
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }
    }
}
