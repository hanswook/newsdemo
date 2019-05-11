package com.hans.newslook.base_core;

import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * Created by hanxu on 2018/8/7.
 */

public final class EmptyUtils {
    private EmptyUtils() {
    }

    public static boolean isEmpty(Object reference) {
        return reference == null;
    }

    public static boolean isEmpty(Map reference) {
        return reference == null || reference.isEmpty();
    }

    public static boolean isEmpty(Collection reference) {
        return reference == null || reference.size() == 0;
    }

    public static boolean isEmpty(String reference) {
        return reference == null || "".equals(reference);
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        } else {
            return reference;
        }
    }

    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        } else {
            return reference;
        }
    }
}
