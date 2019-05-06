package com.terwergreen.jvue.utils;

import com.alibaba.fastjson.JSON;
import com.terwergreen.jvue.util.EncryptAndDecryptUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class EncryptAndDecryptTest {

    @Test
    public void encrypt() {
        Map map = new HashMap();
        map.put("loginId", 1);
        map.put("loginName", "admin");
        map.put("role", "ROLE_ADMIN");
        System.out.println(EncryptAndDecryptUtil.encrypt(JSON.toJSONString(map)));
    }

    @Test
    public void decrypt() {
        Map map = new HashMap();
        map.put("loginId", 1);
        map.put("loginName", "admin");
        map.put("role", "ROLE_ADMIN");
        System.out.println(EncryptAndDecryptUtil.decrypt(EncryptAndDecryptUtil.encrypt(JSON.toJSONString(map))));
    }

    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("loginId", 1);
        map.put("loginName", "admin");
        map.put("role", "ROLE_ADMIN");
        System.out.println(EncryptAndDecryptUtil.encrypt(JSON.toJSONString(map)));
        System.out.println(EncryptAndDecryptUtil.decrypt(EncryptAndDecryptUtil.encrypt(JSON.toJSONString(map))));
    }
}