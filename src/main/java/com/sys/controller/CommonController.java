package com.sys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.core.Controller;

/**
 * CommonController
 */
public class CommonController extends Controller {
    public static final Logger log = LoggerFactory.getLogger(CommonController.class);
    public void index() {
		render("/manage/index.html");
    }
}
