package book.Java系统性能优化源代码.skill.template;

import java.io.IOException;

/**
 * 输出静态文本
 * @author java系统优化
 */
public class StaticTextToken implements  Token {
  /**
   * 可以进一步优化，提取出chars，直接调用append(char[]),不过可能失去了jdk对String重复的优化
   * @see com.ibeetl.code.ch05.after6.LotsOfStrings
   */
  private String  text;


  public StaticTextToken(String text){
    this.text = text;
  }
  @Override
  public final void render(Context ctx) throws IOException {
    ctx.getWriter().append(text);
  }
}
