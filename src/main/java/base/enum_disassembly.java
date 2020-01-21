package base;

public enum enum_disassembly {
    SPRING,SUMMER,AUTUMN,WINTER;
}

/*反汇编代码
public final class enum_disassembly extends Enum {
    private enum_disassembly(String s, int i)
    {
        super(s, i);
    }
    public static enum_disassembly[] values()
    {
        enum_disassembly at[];
        int i;
        enum_disassembly at1[];
        System.arraycopy(at = ENUM$VALUES, 0, at1 = new enum_disassembly[i = at.length], 0, i);
        return at1;
    }

    在序列化的时候Java仅仅是将枚举对象的name属性输出到结果中，反序列化的时候则是通过java.lang.Enum的valueOf方法来根据名字查找枚举对象。
    同时，编译器是不允许任何对这种序列化机制的定制的，因此禁用了writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法。
    我们看一下这个valueOf方法：
    public static <T extends Enum<T>>T valueOf(Class<T> enumType,String name) {
            T result = enumType.enumConstantDirectory().get(name);
            if (result != null)
                return result;
            if (name == null)
                throw new NullPointerException("Name is null");
            throw new IllegalArgumentException(
                "No enum const " + enumType +"." + name);
        }

    public static enum_disassembly valueOf(String s)
    {
        return (enum_disassembly)Enum.valueOf(demo/ enum_disassembly, s);
    }

    public static final enum_disassembly SPRING;
    public static final enum_disassembly SUMMER;
    public static final enum_disassembly AUTUMN;
    public static final enum_disassembly WINTER;
    private static final enum_disassembly ENUM$VALUES[];
    static
    {
        SPRING = new enum_disassembly("SPRING", 0);
        SUMMER = new enum_disassembly("SUMMER", 1);
        AUTUMN = new enum_disassembly("AUTUMN", 2);
        WINTER = new enum_disassembly("WINTER", 3);
        ENUM$VALUES = (new enum_disassembly[] {
                SPRING, SUMMER, AUTUMN, WINTER
        });
    }
}
*/