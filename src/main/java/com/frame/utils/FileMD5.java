package com.frame.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
/**
 * MD5�ļ�����
 * @author lwyx
 *
 */
public class FileMD5 {
	  public static String getMd5ByFile(File file) {
	      String value = null;
	      FileInputStream in = null;
	  try {
	      in = new FileInputStream(file);
	      MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
	      MessageDigest md5 = MessageDigest.getInstance("MD5");
	      md5.update(byteBuffer);
	      BigInteger bi = new BigInteger(1, md5.digest());
	      value = bi.toString(16);
	      if(value.length()==31){
	    	  value = "0"+value;
	      }
	  } catch (Exception e) {
	      e.printStackTrace();
	  } finally {
	          if(null != in) {
	              try {
	              in.close();
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	      }
	  }
	  return value;
	  }
	  public static void main(String[] args) {
	      String v = FileMD5.getMd5ByFile(new File("D:\\PLSQL Developer 8.0.3.1510.rar"));
	      System.out.println(v);
	  System.out.println(v.toUpperCase());
	  }
	}
