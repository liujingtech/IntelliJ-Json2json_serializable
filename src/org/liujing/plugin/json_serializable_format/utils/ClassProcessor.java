// 
// Decompiled by Procyon v0.5.36
// 

package org.liujing.plugin.json_serializable_format.utils;

import java.util.Iterator;
import org.liujing.plugin.json_serializable_format.model.ParamsModel;
import java.util.Collections;
import org.liujing.plugin.json_serializable_format.model.ClassModel;
import java.util.List;

public class ClassProcessor
{
    public static String buildClass(final List<ClassModel> classModels, final String nameWithoutExtension) {
        Collections.reverse(classModels);
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("import 'package:json_annotation/json_annotation.dart';");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("part '" + nameWithoutExtension + ".g.dart';");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        for (int i = 0; i < classModels.size(); ++i) {
            buildClassParam(stringBuilder, classModels.get(i));
        }
        return stringBuilder.toString();
    }
    
    private static void buildClassParam(final StringBuilder stringBuilder, final ClassModel classModel) {
        stringBuilder.append("@JsonSerializable()");
        stringBuilder.append("\n");
        stringBuilder.append(classTitle(classModel));
        for (final ParamsModel paramsModel : classModel.getParamsModels()) {
            stringBuilder.append(buildParams(paramsModel));
        }
        stringBuilder.append("\n");
        stringBuilder.append(String.format("  %1$s({", classModel.getClassName()));
        for (final ParamsModel paramsModel : classModel.getParamsModels()) {
            stringBuilder.append(String.format("this.%1$s, ", paramsModel.getOriginName()));
        }
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("});\n");
        stringBuilder.append("\n");
        stringBuilder.append("  factory " + classModel.getClassName() + ".fromJson(Map<String, dynamic> json) => _$" + classModel.getClassName() + "FromJson(json);");
        stringBuilder.append("\n");
        stringBuilder.append("\n");
        stringBuilder.append("  Map<String, dynamic> toJson() => _$" + classModel.getClassName() + "ToJson(this);");
        stringBuilder.append("\n");
        stringBuilder.append("}\n");
        stringBuilder.append("\n");
    }
    
    private static String buildParams(final ParamsModel paramsModel) {
        return String.format("  %1$s %2$s;\n", paramsModel.getTypeName(), paramsModel.getOriginName());
    }
    
    private static String classTitle(final ClassModel classModel) {
        return String.format("class %1$s {\n", classModel.getClassName());
    }
}
