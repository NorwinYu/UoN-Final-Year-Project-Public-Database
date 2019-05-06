package com.trufla.androidtruforms.utils;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Map;

import static com.trufla.androidtruforms.utils.TruUtils.removeLastColon;

public class EnumDataFormatter {
    public static ArrayList<Pair<Object, String>> getPairList(String string, String selector, ArrayList<String> names) {
        ArrayList<Pair<Object, String>> list = new ArrayList<>();
        JsonElement jsonElement;
        JsonArray jsonArray;
        try {
            jsonElement = new JsonParser().parse(string);
            jsonArray = jsonElement.isJsonArray() ? jsonElement.getAsJsonArray() : null;
            if (jsonArray == null && jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("data")) {
                jsonArray = jsonElement.getAsJsonObject().get("data").getAsJsonArray();
            } else {
                jsonArray = findArrayField(jsonElement);
            }
            for (JsonElement element : jsonArray) {
                list.add(getPairFromObject(element.getAsJsonObject(), selector, names));
            }
            //todo if selector has a . in it these mean it's inside the nested object

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;

    }

    private static JsonArray findArrayField(JsonElement jsonElement) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Map.Entry<String, JsonElement> stringJsonElementEntry : jsonObject.entrySet()) {
            if (stringJsonElementEntry.getValue().isJsonArray())
                return stringJsonElementEntry.getValue().getAsJsonArray();
        }
        return null;
    }

    private static Pair<Object, String> getPairFromObject(JsonObject asJsonObject, String selector, ArrayList<String> names) {
        Object key = new Gson().fromJson(asJsonObject.getAsJsonPrimitive(selector), Object.class);
        StringBuilder finalName = new StringBuilder();
        for (String name : names) {
            try {
                if (name.contains(".")) {
                    String[] parts = name.split("\\.");
                    finalName.append(getNameFromNestedChild(asJsonObject, parts[0], parts[1]));
                }
                else
                    finalName.append(getNameFromCurrentObject(asJsonObject, name));
                finalName.append(',');
            } catch (Exception e) {
                finalName.append("N/A");
            }
        }
        finalName = new StringBuilder(removeLastColon(finalName.toString()));
        return new Pair<>(key, finalName.toString());
    }

    private static String getNameFromNestedChild(JsonObject asJsonObject, String childObject,String realName) {
        return getNameFromCurrentObject(asJsonObject.get(childObject).getAsJsonObject(),realName);
    }

    @NonNull
    private static String getNameFromCurrentObject(JsonObject asJsonObject, String n) {
        return String.valueOf(new Gson().fromJson(asJsonObject.getAsJsonPrimitive(n), Object.class));
    }


}
