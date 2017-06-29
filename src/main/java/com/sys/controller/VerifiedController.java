package com.sys.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.frame.constants.Constants;
import com.frame.utils.VerifiedCodeGenerator;
import com.jfinal.core.Controller;

public class VerifiedController extends Controller {

	public void index() {
		OutputStream out = null;
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		response.setContentType("image/jpeg");
		try {
			out = response.getOutputStream();
			Random random = new Random();
			StringBuilder strb = new StringBuilder()
									.append(random.nextInt(10))
									.append(random.nextInt(10))
									.append(random.nextInt(10))
									.append(random.nextInt(10));
			request.getSession().setAttribute(Constants.VERITY_CODE_KEY, strb.toString());

			VerifiedCodeGenerator codeGenerator = new VerifiedCodeGenerator();
			codeGenerator.setImgWidth(60);
			codeGenerator.setImgHeight(22);
			//codeGenerator.output(out, "jpg", codeGenerator.createImage(strb.toString()));
			codeGenerator.output(out, "png", codeGenerator.createImage(strb.toString()));

			renderNull();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
