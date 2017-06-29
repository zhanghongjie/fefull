package com.frame.utils;

import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * Java使用qq邮箱发送邮件
 * 
 */
public class JavaMail {

	// 发件人用户名、密码
	private String SEND_USER = "304873617@qq.com";
	//qq邮箱授权码
	private String SEND_PWD = "lbumiobzhjlpbjgi";
	/*
	 * 初始化方法
	 */
	public JavaMail() {
	}

	/**
	 * 发送邮件
	 * 
	 * @param headName
	 *            邮件头文件名
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 */
	public void doSendHtmlEmail(String headName, String sendHtml,
			String receiveUser) {
		if(!Fun.eqNull(receiveUser)){
			try {
				Properties props = new Properties();
				// 开启debug调试
				props.setProperty("mail.debug", "true");
				// 发送服务器需要身份验证
				props.setProperty("mail.smtp.auth", "true");
				// 设置邮件服务器主机名
				props.setProperty("mail.host", "smtp.qq.com");
				// 发送邮件协议名称
				props.setProperty("mail.transport.protocol", "smtp");
	
				MailSSLSocketFactory sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
				props.put("mail.smtp.ssl.enable", "true");
				props.put("mail.smtp.ssl.socketFactory", sf);
	
				Session session = Session.getInstance(props);
	
				Message msg = new MimeMessage(session);
				//标题
				msg.setSubject(headName);
				//内容
				msg.setText(sendHtml);
				//发送者邮箱
				msg.setFrom(new InternetAddress(SEND_USER));
				Transport transport = session.getTransport();
				transport.connect("smtp.qq.com", SEND_USER, SEND_PWD);
				//接收者邮箱
				transport.sendMessage(msg, new Address[] { new InternetAddress(receiveUser) });
				transport.close();
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
		
		}
	}

	public static void main(String[] args) {
		JavaMail se = new JavaMail();
		se.doSendHtmlEmail("测试", "邮件内容", "lwyx2000@163.com");
	}
}
