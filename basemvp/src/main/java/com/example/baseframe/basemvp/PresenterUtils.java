package com.example.baseframe.basemvp;

import com.example.BaseFrame.common.ReflectUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author fengzhongcheng
 * @since 2021/4/19
 */
public class PresenterUtils {
    public static boolean isParameterizedType(Class cls) {
        if (cls == null) return false;
        Type type = cls.getGenericSuperclass();
        return type != null && ParameterizedType.class.isAssignableFrom(type.getClass());
    }

    public static <T> T getBasePresenter(Class cls) {
        if (isParameterizedType(cls)) {
            try {
                Type[] types = ((ParameterizedType) cls.getGenericSuperclass()).getActualTypeArguments();
                if (types != null && types.length > 0) {
                    Class<T> clazz = (Class<T>) ReflectUtils.getClass(types[0]);
                    if (clazz == null) {
                        return null;
                    }
                    if (BasePresenter.class.isAssignableFrom(clazz)) {
                        return clazz.newInstance();
                    } else {
                        throw new Exception("must be with a presenter");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else {
            if (cls == null || !BaseView.class.isAssignableFrom(cls)) {
                return null;
            }
            return getBasePresenter(cls.getSuperclass());
        }
        return null;
    }
}
