package com.frame.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public final class InternetProtocol {

	private static Logger log = Logger.getLogger(InternetProtocol.class);

	private InternetProtocol() {
	}

	/**
	 * 获取客户端IP地址.<br>
	 * 支持多级反向代理
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return 客户端真实IP地址
	 */
	public static String getRemoteAddr(final HttpServletRequest request) {
		try {
			String remoteAddr = request.getHeader("X-Forwarded-For");
			if (isEffective(remoteAddr) && (remoteAddr.indexOf(",") > -1)) {
				String[] array = remoteAddr.split(",");
				for (String element : array) {
					if (isEffective(element)) {
						remoteAddr = element;
						break;
					}
				}
			}
			if (!isEffective(remoteAddr)) {
				remoteAddr = request.getHeader("X-Real-IP");
			}
			if (!isEffective(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
			return remoteAddr.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : remoteAddr;
		} catch (Exception e) {
			log.error("get romote ip error,error message:" + e.getMessage());
			return "127.0.0.1";
		}
	}

	/**
	 * 远程地址是否有效.
	 *
	 * @param remoteAddr
	 *            远程地址
	 * @return true代表远程地址有效，false代表远程地址无效
	 */
	private static boolean isEffective(final String remoteAddr) {
		boolean isEffective = false;
		if ((null != remoteAddr) && (!"".equals(remoteAddr.trim()))
				&& (!"unknown".equalsIgnoreCase(remoteAddr.trim()))) {
			isEffective = true;
		}
		return isEffective;
	}
}
