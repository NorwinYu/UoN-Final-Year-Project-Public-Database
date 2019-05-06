package by.grodno.krivosheev.json;

import java.util.Map;
import java.util.Stack;

public class JsonParser {
    public static JsonObject getJsonObject(String source) {
        JsonObject jsonObject = new JsonObject();
        Stack<String> stackKey = new Stack<>();
        char preChar = ' ';
        Stack<Boolean> modeArray = new Stack<>();
        String value = "";
        int i = 0;
        try {
            while (i < source.length()) {
                if (preChar == ':')
                    value += source.charAt(i) == '{' || source.charAt(i) == '}' || source.charAt(i) == '[' || source.charAt(i) == ']' ? "" : source.charAt(i);
                else value = "";
                switch (source.charAt(i)) {
                    case '{':
                        if (preChar == '{') throw new Exception();
                        preChar = '{';
                        break;
                    case '[':
                        modeArray.push(true);
                        jsonObject.setKeyAndValue(stackKey.peek(), "[ {");
                        break;
                    case ',':
                        if (preChar == ':') {
                            String s = value;
                            preChar = ',';
                            if (!stackKey.empty()) {
                                String key = stackKey.pop();
                                jsonObject.setKeyAndValue(key, s);
                            }
                            value = "";
                            break;
                        }
                        if (preChar == '}') {
                            preChar = ',';
                            if (!stackKey.empty()) {
                                String s = stackKey.pop();
                                if (jsonObject.getObject("/" + s) != null && jsonObject.getObject("/" + s) == "} ]") {
                                    jsonObject.setKeyAndValue("/" + s, "} ],");
                                } else jsonObject.setKeyAndValue("/" + s, "},");
                                if (!modeArray.empty() && source.charAt(i - 1) == '}') {
                                    stackKey.push(i + s);
                                    jsonObject.setKeyAndValue(stackKey.peek(), "{n");
                                }
                            }
                            value = "";
                        }
                        break;
                    case ']':
                        modeArray.pop();
                        if (!stackKey.isEmpty()) jsonObject.setKeyAndValue("/" + stackKey.peek(), "} ]");
                        break;
                    case '}':
                        if (preChar == ':') {
                            String s = value;
                            preChar = '}';
                            String key = stackKey.pop();
                            jsonObject.setKeyAndValue(key, s);
                            break;
                        }
                        if (!stackKey.empty()) {
                            String key = stackKey.pop();
                            if (jsonObject.getObject("/" + key) != null && jsonObject.getObject("/" + key) == "} ]") {
                                preChar = '}';
                                break;
                            }
                            jsonObject.setKeyAndValue("/" + key, (preChar == ',' ? "}," : "}"));
                        }
                        preChar = '}';
                        break;
                    case ':':
                        preChar = ':';
                        break;
                    case '"':
                        int j = 1;
                        StringBuilder s = new StringBuilder();
                        while (source.charAt(i + j) != '"') {
                            s.append(source.charAt(j + i));
                            j++;
                        }
                        if (preChar == ':') {
                            value += s + "\"";
                        }
                        if (preChar == '{' || preChar == ',') {
                            stackKey.push(i + "\"" + s);
                            String key = stackKey.peek();
                            jsonObject.setKeyAndValue(key, "{");
                        }
                        i += j;
                        break;
                    default:
                        break;
                }
                i++;
            }
        } catch (Exception e) {
            System.err.println("Ошибка: некоректный формат!");
            return null;
        }
        return jsonObject;
    }

    public static String toString(JsonObject jsonObject) {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{\n\t");
        for (Map.Entry<String, Object> item : jsonObject.entrySet()) {
            if (item.getValue().toString().equals("}") || item.getValue().toString().equals("},")) {
                jsonString.append("\t");
                jsonString.append(item.getValue().toString());
            } else if (item.getValue().toString().equals("{n")) {
                jsonString.append("{");
            } else if (item.getValue().toString().equals("} ],") || item.getValue().toString().equals("} ]")) {
                jsonString.append(item.getValue().toString());
            } else {
                if (!item.getValue().toString().equals("")) {
                    String key = item.getKey().substring(item.getKey().indexOf('"'));
                    jsonString.append(key + "\":" + item.getValue().toString());
                }
            }
        }
        jsonString.append("\n}");
        return jsonString.toString();
    }
}
