package book.Java系统性能优化源代码.skill.reflect;

/**
 * 一个简单实现例子
 * @author java系统优化
 */
public class UserDirectAccessTool implements ReflectTool{
  @Override
  public Object getValue(Object target, String attr) {
    return ((User)target).getName();
  }
}
