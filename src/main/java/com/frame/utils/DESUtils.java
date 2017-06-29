package com.frame.utils;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DESUtils {
	private static final String PASSWORD_CRYPT_KEY = "!wrw#@jy^a";
	private final static String DES = "DES";
	private final static String MODE="DES/ECB/PKCS5Padding"; 
	private final static String encode = "utf-8";
	
	public static byte[] encrypt(byte[] src, byte[] key){
		try {
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(MODE);
	        byte biv[] = new byte[8];
	        IvParameterSpec iv = new IvParameterSpec(biv);			
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);			
			return cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decrypt(byte[] src, byte[] key) {		
		try {			
			SecureRandom sr = new SecureRandom();
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(MODE);
	        byte biv[] = new byte[8];
	        IvParameterSpec iv = new IvParameterSpec(biv);
			cipher.init(Cipher.DECRYPT_MODE, securekey,sr);
			return cipher.doFinal(src);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final static String decrypt(String data) {
		try {
			return new String(decrypt(hex2byte(data.getBytes(encode)), PASSWORD_CRYPT_KEY.getBytes(encode)),encode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public final static String encrypt(String password) {
		try {
			return byte2hex(encrypt(password.getBytes(encode), PASSWORD_CRYPT_KEY.getBytes(encode)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;

		}
		return hs.toUpperCase();
	}	
 

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0) throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}
	
	
	public static void main(String[] args) throws Exception{
		System.out.println(DESUtils.encrypt("-qL4KqIx2luFkpANvcCcvuCOEQy2gO2s3sySSBke"));
	}

}