package org.smart.framework.util;

import org.apache.commons.lang3.StringUtils;

public final class CastUtil {

    public static long castLong(Object object) {
        long longValue = 0;
        if(object != null){
            String strValue = castString(object);
            if(StringUtils.isNotEmpty(strValue)){
                try {
                    longValue = Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    longValue = 0;
                }
            }
        }
        return longValue;
    }

    public static String castString(Object object) {
        return object == null ? "" : String.valueOf(object);
    }
}
