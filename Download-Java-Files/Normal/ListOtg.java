package com.example.usk.glotus_final.ManagerApp.ManagerListFiles;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.widget.ListView;
import com.example.usk.glotus_final.R;
import com.example.usk.glotus_final.SuperviserApp.SuperviserListFiles.OrderListAdapter;
import com.example.usk.glotus_final.System.Catalog.Adress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListOtg extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otggg);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
          ArrayList<Otg> arrayList= new ArrayList<>();


        System.out.println(OrderListAdapter.item.getZakaz());
        JSONObject jsonObject = null;
        JSONArray jsonArray=null;

        try {
            jsonArray= new JSONArray(OrderListAdapter.item.getZakaz());

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i=0;i<jsonArray.length();i++){
            System.out.println(i);
            String otkuda="";
            String kuda="";
            try{
                otkuda=Adress.adresspreferences.getAll().get(jsonArray.getJSONObject(i).getString("Откуда_Key")).toString();
            }
            catch (Exception e){

            }
            try{
                kuda=Adress.adresspreferences.getAll().get(jsonArray.getJSONObject(i).getString("Куда_Key")).toString();
            }
            catch (Exception e){

            }

            try {
                System.out.println(jsonArray.getJSONObject(i).getString("Ref_Key"));

                Otg otg=new Otg(jsonArray.getJSONObject(i).getString("Ref_Key"),
                        jsonArray.getJSONObject(i).getString("Заказ_Key"),
                        jsonArray.getJSONObject(i).getString("ЗаказКоличествоМестФакт"),
                        jsonArray.getJSONObject(i).getString("ЗаказОбъемФакт"),
                        jsonArray.getJSONObject(i).getString("ЗаказВесФакт"),
                        jsonArray.getJSONObject(i).getString("Выгружен").split("T")[0],
                        jsonArray.getJSONObject(i).getString("ДатаКонтроля").split("T")[0],
                        otkuda,
                        kuda,
                        jsonArray.getJSONObject(i).getString("Коробок"),
                        jsonArray.getJSONObject(i).getString("Паллет"),
                        jsonArray.getJSONObject(i).getString("Примечание"));
                arrayList.add(otg);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        ListOtgAdapter listOtgAdapter=new ListOtgAdapter(this, R.layout.element_otgruzka,arrayList);
        listView=findViewById(R.id.list);
        listView.setAdapter(listOtgAdapter);


    }
}
