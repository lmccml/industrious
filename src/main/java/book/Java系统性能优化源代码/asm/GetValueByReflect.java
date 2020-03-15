package book.Java系统性能优化源代码.asm;

import java.lang.reflect.Field;

public class GetValueByReflect {
    private GetValueByReflect() {

    }

    public static Object value(Object bean, String property) {
        try {
            Field field = bean.getClass().getDeclaredField(property);
            field.setAccessible(true);
            return field.get(bean);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
                | IllegalAccessException e) {
            // Do nothing
        }
        return null;

    }
}
