package com.frame.utils;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	public MD5() {
	}

	/**
	 * MD5
	 *
	 * @param str
	 *            String
	 * @return String
	 * @throws NoSuchAlgorithmException
	 */
	public static synchronized String encrypt(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest alga = MessageDigest.getInstance("md5");
			alga.update(str.getBytes());
			byte[] digesta = alga.digest();

			return byte2hex(digesta);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *
	 * @param b byte[]
	 * @return String
	 */
	public static String byte2hex(byte[] b) {
		String sHexStr = "";
		String sTmp = "";
		for (int n = 0; n < b.length; n++) {
			sTmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (sTmp.length() == 1) {
				sHexStr = sHexStr + "0" + sTmp;
			} else {
				sHexStr = sHexStr + sTmp;
			}
			if (n < b.length - 1) {
				sHexStr = sHexStr + "";
			}
		}
		return sHexStr.toLowerCase();
	}

	public static void main(String[] args) {
		System.out.print(encrypt("7239311"));
	}
}
