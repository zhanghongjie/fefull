package com.frame.sms.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.biz.commom.FefullEnum;
import com.biz.commom.sms.SmsReBaseBean;
import com.biz.model.SysSms;
import com.frame.exception.BusinessException;
import com.frame.sms.common.exception.SmsException;
import com.frame.sms.constant.SysSmsConstant;
import com.jfinal.plugin.activerecord.Record;

public class SendUtil {
	public static final Logger logger = LoggerFactory.getLogger(SendUtil.class);
	private static String proxyIp;
	private static int port;


	/**
	 * @param url 请求地址
	 * @param paramsMap 请求参数
	 * @return
	 */
	public static String simpleHttpPost4Proxy(String url,
			Map<String, String> paramsMap) {
		if(url==null || paramsMap==null){
			throw new BusinessException("SendUtil-->simpleHttpPost4Proxy-->输入参数为空！");
		}
		logger.info("请求开始 url=" + url + " \n req = " + paramsMap.entrySet().toArray());
		CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, SysSmsConstant.SMS_ENCODE));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity);
    			logger.info("请求完成  返回内容[" + responseText + "] ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText;
	}

	public static Proxy getProxy() throws UnknownHostException {
		if ((proxyIp != null) && (!(proxyIp.trim().equals("")))
				&& (!(proxyIp.trim().toLowerCase().equals("null")))) {
			logger.info("使用代理 http://" + proxyIp + ":" + port);
			InetSocketAddress socketAddress = new InetSocketAddress(
					InetAddress.getByName(proxyIp), port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, socketAddress);
			return proxy;
		}
		return null;
	}

	public static String getProxyIp() {
		return proxyIp;
	}

	public static void setProxyIp(String proxyIp) {
		SendUtil.proxyIp = proxyIp;
	}

	public static int getPort() {
		return port;
	}

	public static void setPort(int port) {
		SendUtil.port = port;
	}
	/**
	 * 返回通道请求参数
	 * @param conf
	 * @param smsType 短信类型
	 * @return
	 */
	public static Map<String,String> boxArg(List<Record> conf,String mSmsType){
		Map<String,String> reMap  = null;
		StringBuilder sb  = null;
		//通道请求地址
		String channelUrl = null;
		//默认短信类型为全类型
		String defSmsType = SysSmsConstant.SMS_ALL_TYPE;
		if(conf != null && conf.size()>0){
			sb = new StringBuilder();
			reMap = new HashMap<String, String>();
			Record tmp = conf.get(0);
			//若有细分短信类型，则按细分的短信类型查找相应的配置
			if(!tmp.get("sms_type").equals(defSmsType)){
				defSmsType = mSmsType;
			}
			//取短信通道公用设置
			//通道请求地址
			channelUrl = tmp.get("channel_url").toString();
			reMap.put(SysSmsConstant.CHANNEL_URL, channelUrl);
			//通道签名
			reMap.put(SysSmsConstant.CHANNEL_SING, tmp.get("channel_sign")==null? "" : tmp.get("channel_sign").toString());
			
			for (Record map : conf) {
				// fk_sys_sms_product为0为公共属性
				if(map != null && (map.get("sms_type").equals(defSmsType) || (null!=tmp.get("fk_sys_sms_product") && tmp.get("fk_sys_sms_product").toString().trim().equals("0")))){
					sb.append(map.get("arg_code").toString())
					.append("=")
					.append(map.get("arg_value").toString())
					.append("&");
				}
			}
			if(sb!=null && sb.length()>1){
				reMap.put(SysSmsConstant.CHANNEL_ARGS, sb.substring(0, sb.length()-1));
			}
		}
		return reMap;
	}

	/**
	 * 组装请求参数
	 * @param sms 
	 * @param mapArgs
	 * @param encode 短信内容URLEncoder编码
	 * @return
	 * @throws SmsException 
	 */
	public static Map<String,String> reReqArg(SysSms sms,Map<String,String> mapArgs,String encode,int isOnlyAm) throws SmsException{
		Map<String,String> reMap  = null;
		if(sms!=null && mapArgs!=null){
			reMap = new HashMap<String, String>();
			String tmp = mapArgs.get(SysSmsConstant.CHANNEL_ARGS);
			tmp = tmp.replace("{content}", ( mapArgs.get(SysSmsConstant.CHANNEL_SING)==null? "": mapArgs.get(SysSmsConstant.CHANNEL_SING)) + sms.getContent())
					.replace("{mobile}", sms.getToPhones());
			reMap.put("args", tmp);
			reMap.put("url", mapArgs.get(SysSmsConstant.CHANNEL_URL));
			sms.setIsOnlyAm(isOnlyAm);
			sms.setChannelArgs(tmp);
			sms.setChannelUrl(mapArgs.get(SysSmsConstant.CHANNEL_URL));
		}
		return reMap;
	}
	/**
	 * 返回短信发送结果
	 * @param rs
	 * @param sms
	 * @return
	 */
	public static void reResStatus(String rs,SysSms sms){ if(rs!=null && sms!=null){
			SmsReBaseBean smsReBaseBean = JSON.parseObject(rs,SmsReBaseBean.class);
			
			if(smsReBaseBean.getCode()==FefullEnum.SmsReType.OK.getValue()){
				sms.setResStatus(FefullEnum.SmsReType.OK.getValue()+"");
			}else{
				sms.setResStatus(FefullEnum.SmsReType.ERROR.getValue()+"");
			}
		}
	}
	public static class Result {
		private boolean rs = true;
		private String msg;
		private Object datas;

		public Result() {
		}

		public Result(boolean rs, String msg) {
			this.rs = rs;
			this.msg = msg;
		}

		public Result(boolean rs, String msg, Object datas) {
			this.rs = rs;
			this.msg = msg;
			this.datas = datas;
		}

		public boolean isRs() {
			return this.rs;
		}

		public void setRs(boolean rs) {
			this.rs = rs;
		}

		public String getMsg() {
			return this.msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

		public Object getDatas() {
			return this.datas;
		}

		public void setDatas(Object datas) {
			this.datas = datas;
		}

		public String toString() {
			StringBuilder strb = new StringBuilder("{\"rs\":");
			strb.append((this.rs) ? "true" : "false")
					.append(",\"msg\":\"")
					.append((this.msg == null) ? "" : this.msg)
					.append("\",\"datas\":")
					.append((this.datas == null) ? "{}" : JSON
							.toJSONString(this.datas)).append("}");
			return strb.toString();
		}
	}
}
