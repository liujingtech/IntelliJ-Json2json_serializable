// 
// Decompiled by Procyon v0.5.36
// 

package org.liujing.plugin.json_serializable_format.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.util.Iterator;
import org.liujing.plugin.json_serializable_format.model.ParamsModel;
import java.util.ArrayList;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.Set;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.liujing.plugin.json_serializable_format.model.ClassModel;
import java.util.List;

public class JsonTransformerUtils
{
    public static List<ClassModel> transformerJson(final String json, final String className) {
        if (json == null || !json.startsWith("{")) {
            return null;
        }
        final JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        if (jsonObject == null) {
            return null;
        }
        final Set<Map.Entry<String, JsonElement>> keys = (Set<Map.Entry<String, JsonElement>>)jsonObject.entrySet();
        return init(keys, className);
    }
    
    private static List<ClassModel> init(final Set<Map.Entry<String, JsonElement>> keys, final String className) {
        final List<ClassModel> classModels = new ArrayList<ClassModel>();
        return binaryClassList(classModels, keys, className);
    }
    
    private static List<ClassModel> binaryClassList(final List<ClassModel> classModels, final Set<Map.Entry<String, JsonElement>> keys, final String className) {
        final ClassModel classModel = new ClassModel();
        classModel.setClassName(className);
        final List<ParamsModel> paramsModels = new ArrayList<ParamsModel>();
        for (final Map.Entry<String, JsonElement> entry : keys) {
            final ParamsModel paramsModel = checkParam(entry, classModels);
            paramsModels.add(paramsModel);
        }
        classModel.setParamsModels(paramsModels);
        classModels.add(classModel);
        return classModels;
    }
    
    private static ParamsModel checkParam(final Map.Entry<String, JsonElement> entry, final List<ClassModel> classModels) {
        final ParamsModel paramsModel = new ParamsModel();
        paramsModel.setOriginName(entry.getKey());
        paramsModel.setCamelName(StringUtils.toLowerCamel(entry.getKey()));
        if (entry.getValue().isJsonNull()) {
            paramsModel.setType(9);
            paramsModel.setTypeName("dynamic");
            return paramsModel;
        }
        if (entry.getValue().isJsonPrimitive()) {
            final JsonPrimitive jsonPrimitive = entry.getValue().getAsJsonPrimitive();
            paramsModel.setType(getPrimitiveType(jsonPrimitive));
            paramsModel.setTypeName(getPrimitiveTypeName(jsonPrimitive, "num"));
        }
        else if (entry.getValue().isJsonArray()) {
            final JsonArray jsonElements = entry.getValue().getAsJsonArray();
            if (jsonElements.size() == 0) {
                paramsModel.setType(9);
                paramsModel.setTypeName("dynamic");
                return paramsModel;
            }
            if (jsonElements.get(0).isJsonNull()) {
                paramsModel.setType(8);
                paramsModel.setTypeName(String.format("List<%1$s>", "dynamic"));
            }
            else if (jsonElements.get(0).isJsonPrimitive()) {
                paramsModel.setType(6);
                paramsModel.setTypeName(getPrimitiveTypeName(jsonElements.get(0).getAsJsonPrimitive(), "List<%1$s>"));
            }
            else if (jsonElements.get(0).isJsonObject()) {
                final String className = Character.toUpperCase(entry.getKey().charAt(0)) + entry.getKey().substring(1) + "Bean";
                paramsModel.setType(8);
                paramsModel.setTypeName(String.format("List<%1$s>", className));
                binaryClassList(classModels, jsonElements.get(0).getAsJsonObject().entrySet(), className);
            }
        }
        else if (entry.getValue().isJsonObject()) {
            final Set<Map.Entry<String, JsonElement>> keys = (Set<Map.Entry<String, JsonElement>>)entry.getValue().getAsJsonObject().entrySet();
            if (keys == null || keys.size() == 0) {
                paramsModel.setType(9);
                paramsModel.setTypeName("dynamic");
            }
            else {
                final String className = Character.toUpperCase(entry.getKey().charAt(0)) + entry.getKey().substring(1) + "Bean";
                paramsModel.setType(7);
                paramsModel.setTypeName(className);
                binaryClassList(classModels, keys, className);
            }
        }
        return paramsModel;
    }
    
    private static String getPrimitiveTypeName(final JsonPrimitive jsonPrimitive, final String modelType) {
        if (jsonPrimitive.isBoolean()) {
            return "List<%1$s>".equals(modelType) ? String.format("List<%1$s>", "bool") : "bool";
        }
        if (jsonPrimitive.isNumber()) {
            return "List<%1$s>".equals(modelType) ? String.format("List<%1$s>", "num") : "num";
        }
        if (jsonPrimitive.isString()) {
            final String date = jsonPrimitive.getAsString().replace("Z", " UTC");
            final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            try {
                format.parse(date);
                return "List<%1$s>".equals(modelType) ? String.format("List<%1$s>", "int") : "int";
            }
            catch (ParseException e) {
                e.printStackTrace();
                return "List<%1$s>".equals(modelType) ? String.format("List<%1$s>", "String") : "String";
            }
        }
        return "List<%1$s>".equals(modelType) ? String.format("List<%1$s>", "") : "";
    }
    
    private static int getPrimitiveType(final JsonPrimitive jsonPrimitive) {
        if (jsonPrimitive.isNumber()) {
            return 4;
        }
        if (jsonPrimitive.isString()) {
            return 1;
        }
        if (jsonPrimitive.isBoolean()) {
            return 5;
        }
        return 0;
    }
}
