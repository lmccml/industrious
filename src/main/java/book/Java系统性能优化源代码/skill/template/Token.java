package book.Java系统性能优化源代码.skill.template;

import java.io.IOException;
/**
 * 语法解析的抽象
 * @author java系统优化
 * @see StaticTextToken
 * @See VarToken
 */
public interface Token {
  public void render(Context ctx) throws IOException;
}
