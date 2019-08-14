package effective_java;

import java.util.regex.Pattern;

// Reusing expensive object for improved performance
public class roman_numerals {

    // Performance can be greatly improved!
    public static boolean isRomanNumeral(String s) {
        return s.matches("^(?=.)M*(C[MD]|D?C{0,3})"
                + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }

    //　这个实现的问题在于它依赖于 String.matches 方法。
    // 虽然 String.matches 是检查字符串是否与正则表达式匹配的最简单方法，
    // 但它不适合在性能临界的情况下重复使用。
    // 问题是它在内部为正则表达式创建一个 Pattern 实例，并且只使用它一次，之后它就有资格进行垃圾收集。
    // 创建 Pattern 实例是昂贵的，因为它需要将正则表达式编译成有限状态机（finite state machine）。
    //　　为了提高性能，作为类初始化的一部分，将正则表达式显式编译为一个 Pattern 实例（不可变），缓存它，并在 isRomanNumeral 方法的每个调用中重复使用相同的实例：
    private static final Pattern ROMAN = Pattern.compile(
            "^(?=.)M*(C[MD]|D?C{0,3})"
                    + "(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");

    //确定一个字符串是否是一个有效的罗马数字
    public static boolean isRomanNumeralBetter(String s) {
        return ROMAN.matcher(s).matches();
    }

}
