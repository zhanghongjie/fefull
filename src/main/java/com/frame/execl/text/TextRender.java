package com.frame.execl.text;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;
/**
 * 文本render
 * @author zhanghj
 *
 */
public interface TextRender {

	void render(Reader reader, Map<String, Object> context, Writer writer);

	String render(String templateString, Map<String, Object> context);

}
