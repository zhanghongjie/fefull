package com.frame.execl.text;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.log.NullLogChute;

import com.frame.execl.ExportSettingContants;


public class VelocityTextRender implements TextRender {

	public void render(Reader reader, Map<String, Object> context, Writer writer) {
		try {
			//Velocity.init();
			RuntimeInstance  ri = new RuntimeInstance();
			if (!ri.isInitialized()) {
		            // 设置空的log，避免使用velocity默认的veloicyt.log
		           ri.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
		           ri.init();
			 }
			VelocityContext velocityContext = new VelocityContext();
			for(Map.Entry<String, Object> entry : context.entrySet()) {
				velocityContext.put(entry.getKey(), entry.getValue());
			}
			Velocity.evaluate(velocityContext, writer, VelocityTextRender.class.getName(), reader);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String render(String templateString, Map<String, Object> context) {
		StringWriter writer = new StringWriter();
		render(new StringReader(templateString), context, writer);
		return writer.toString();
	}

	public static void main(String[] args) {
		Object[] arguments = new Object[]{"收","100"};
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(ExportSettingContants.ARGS_STRING, arguments);

		TextRender textRender = new VelocityTextRender();
		String evaledText = textRender.render("应$args[0]款：$args[1]元", context);
		System.out.println(evaledText);
	}

}
