package com.sys.controller;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.sys.interceptor.AuthorityCharacterITR;
import com.sys.interceptor.AuthorityMenuITR;

@Before({AuthorityCharacterITR.class,AuthorityMenuITR.class})
public class QrCodeController extends Controller {
	/**
	 */
	public void index() {
		renderFreeMarker("/sys/qrCode.html");
	}
}
