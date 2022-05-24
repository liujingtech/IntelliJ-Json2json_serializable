// 
// Decompiled by Procyon v0.5.36
// 

package org.liujing.plugin.json_serializable_format.utils;

import com.google.common.base.CaseFormat;

public class StringUtils
{
    public static String toUpperCamel(String s) {
        if (s.contains("-")) {
            s = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, s);
        }
        if (s.contains("_")) {
            s = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, s);
        }
        return s;
    }
    
    public static String toLowerCamel(String s) {
        s = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s);
        if (s.contains("-")) {
            s = CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, s);
        }
        if (s.contains("_")) {
            s = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s);
        }
        return s;
    }
    
    public static String toUpperCaseFirstOne(final String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
