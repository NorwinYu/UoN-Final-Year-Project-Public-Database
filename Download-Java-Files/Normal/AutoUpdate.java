package com.example.usk.glotus_final.System.Catalog;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;

import com.example.usk.glotus_final.System.Encryption.AES;
import com.example.usk.glotus_final.System.connection.Server;
import com.example.usk.glotus_final.System.loginFiles.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AutoUpdate {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void update() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        //Catalog Пользыватели
       String res=process("http://185.209.23.53/InfoBase/odata/standard.odata/InformationRegister_%D0%98%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5%D0%94%D0%B0%D0%BD%D0%BD%D1%8B%D1%85?$format=json&$filter=Period%20gt%20datetime%27"+UpdateInf.lastupdate.getAll().get("last")+"%27and%20%D0%9E%D0%B1%D1%8A%D0%B5%D0%BA%D1%82_Type%20eq%20%27StandardODATA.Catalog_%D0%9F%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D0%B8%27",
                "GET", User.cred,"{}");
        setCatalogs( URLEncoder.encode("Catalog_Пользователи", java.nio.charset.StandardCharsets.UTF_8.toString()),Mdnames.mdpreferences,res,
                "Description");

        //Catalog Kontragenty
       res=process("http://185.209.23.53/InfoBase/odata/standard.odata/InformationRegister_%D0%98%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5%D0%94%D0%B0%D0%BD%D0%BD%D1%8B%D1%85?$format=json&$filter=Period%20gt%20datetime%27"+UpdateInf.lastupdate.getAll().get("last")+"%27and%20%D0%9E%D0%B1%D1%8A%D0%B5%D0%BA%D1%82_Type%20eq%20%27StandardODATA.Catalog_Контрагенты%27",
                "GET", User.cred,"{}");
        setCatalogs( URLEncoder.encode("Catalog_Контрагенты", java.nio.charset.StandardCharsets.UTF_8.toString()),Kontragent.kontrpreferences,res,
                "Description");

        //Catalog Podrazd
        /*res=process("http://185.209.23.53/InfoBase/odata/standard.odata/InformationRegister_%D0%98%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5%D0%94%D0%B0%D0%BD%D0%BD%D1%8B%D1%85?$format=json&$filter=Period%20gt%20datetime%27"+UpdateInf.lastupdate.getAll().get("last")+"%27and%20%D0%9E%D0%B1%D1%8A%D0%B5%D0%BA%D1%82_Type%20eq%20%27StandardODATA.Catalog_Подразделения%27",
                "GET", User.cred,"{}");
        setCatalogs( URLEncoder.encode("Catalog_Подразделения", java.nio.charset.StandardCharsets.UTF_8.toString()),Podrazd.pdpreferences,res,
                "Description");*/

        //Catalog Transport
       /* res=process("http://185.209.23.53/InfoBase/odata/standard.odata/InformationRegister_%D0%98%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5%D0%94%D0%B0%D0%BD%D0%BD%D1%8B%D1%85?$format=json&$filter=Period%20gt%20datetime%27"+UpdateInf.lastupdate.getAll().get("last")+"%27and%20%D0%9E%D0%B1%D1%8A%D0%B5%D0%BA%D1%82_Type%20eq%20%27StandardODATA.Catalog_Транспорт%27",
                "GET", User.cred,"{}");
        setCatalogs( URLEncoder.encode("Catalog_Транспорт", java.nio.charset.StandardCharsets.UTF_8.toString()),Transport.trpreferences,res,
                "Description");*/

        //Catalog Adress
        res=process("http://185.209.23.53/InfoBase/odata/standard.odata/InformationRegister_%D0%98%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5%D0%94%D0%B0%D0%BD%D0%BD%D1%8B%D1%85?$format=json&$filter=Period%20gt%20datetime%27"+UpdateInf.lastupdate.getAll().get("last")+"%27and%20%D0%9E%D0%B1%D1%8A%D0%B5%D0%BA%D1%82_Type%20eq%20%27StandardODATA.Catalog_Адресаты%27",
                "GET", User.cred,"{}");
        setCatalogs( URLEncoder.encode("Catalog_Адресаты", java.nio.charset.StandardCharsets.UTF_8.toString()),Adress.adresspreferences,res,
                "Description");

        fupdate();






    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setCatalogs(String catalog, SharedPreferences preferences, String res, String code) throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        ArrayList<String> refs =  new ArrayList<>();

        JSONArray array = null;
        JSONObject jsonObj=null;
        try {
            jsonObj = new JSONObject(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array = jsonObj.getJSONArray("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < array.length(); i++){
            try {
                if (!refs.contains(array.getJSONObject(i).getString("Объект")))
                refs.add(array.getJSONObject(i).getString("Объект"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i=0;i<refs.size();i++){
            String ex=process("http://185.209.23.53/InfoBase/odata/standard.odata/"+catalog+"(guid'"+refs.get(i)+"')?$format=json","GET",
                    User.cred,"{}");

            JSONArray arrayex = null;
            JSONObject jsonObjex = null;
            System.out.println(refs.get(i)+"pp");


            arrayex = null;
            jsonObjex = null;
            try {
                jsonObjex = new JSONObject(ex);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(jsonObjex.getString("Ref_Key")+jsonObjex.getString("Description"));
                preferences.edit().putString(jsonObjex.getString("Ref_Key"), jsonObjex.getString(code)).commit();
                if (catalog.equals("Catalog_Контрагенты")){
                    Pochta.pochtakontr.edit().putString(jsonObjex.getString("Ref_Key"), jsonObjex.getString("Почта")).commit();
                    KontragentNum.kontrnumpreferences.edit().putString(jsonObjex.getString("Ref_Key"), jsonObjex.getString("ТелефонКонтактногоЛица")).commit();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void fupdate() throws NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {




        String res=process("http://185.209.23.53/InfoBase/odata/standard.odata/InformationRegister_%D0%98%D0%B7%D0%BC%D0%B5%D0%BD%D0%B5%D0%BD%D0%B8%D0%B5%D0%94%D0%B0%D0%BD%D0%BD%D1%8B%D1%85?$format=json&$orderby=Period%20desc&$top=1&$select=Period",
                "GET", User.cred,"{}");
        System.out.println(res);
        JSONArray array = null;
        JSONObject jsonObj=null;
        try {
            jsonObj = new JSONObject(res);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            array = jsonObj.getJSONArray("value");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            System.out.println();
            UpdateInf.lastupdate.edit().putString("last",array.getJSONObject(0).getString("Period")).commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String process(String url, String way, String cred, String data) throws NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Server server;
        String body = url + "," + way + "," + cred + "*---*" + data;
        System.out.println(body);
        String string = AES.aesEncryptString(body, "1234567890123456");
        body = "data=" + string;
        System.out.println(body);
        server = new Server("http://185.209.23.53/odata/demoaes.php", null, body);
        return server.post().toString();
    }

}
