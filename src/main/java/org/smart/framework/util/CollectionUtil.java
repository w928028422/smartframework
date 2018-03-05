package org.smart.framework.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;


public final class CollectionUtil {

    public static boolean isNotEmpty(Collection<?> collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    public static boolean isEmpty(Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

}
