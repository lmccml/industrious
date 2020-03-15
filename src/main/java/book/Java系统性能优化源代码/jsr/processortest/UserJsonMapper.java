package book.Java系统性能优化源代码.jsr.processortest;

import com.ibeetl.code.ch10.processor.annotation.JsonWriter;

@JsonWriter
public interface UserJsonMapper {
	public String write(User user);
}
