package com.duowan.http;

import java.util.Hashtable;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.json.JSONObject;

import com.duowan.statis.constant.UrlWrapper;
import com.duowan.statis.interfaces.ResponsePackage;

public class StatisModel {

	public StatisResponse request(int merchantId, int appId, String userName) {
		StatisRequestPackage req = new StatisRequestPackage();
		StatisResponse respObj = new StatisResponse();
		ResponsePackage resp = new StatieResponsePackage();
		Hashtable<String, Object> params = new Hashtable<String, Object>();
		params.put("MerchantId", merchantId);
		params.put("AppId", appId);
		params.put("UserName", UrlEncodeUtil.encode(userName, "gbk"));
		// params.put("Sign", AppUtil.getMd5Str(merchantId, appId));
		req.setParams(params);
		HttpClients kc = HttpClients.getInstance();
		try {
			kc.request(req, resp, "");
			resp.getResponseData(respObj);
			return respObj;
		} catch (Exception e) {
		}
		return null;
	}

	static class StatisRequestPackage extends AbstractRequestPackage {

		@Override
		public HttpEntity getPostRequestEntity() {
			return null;
		}

		@Override
		public String getUrl() {
			return UrlWrapper.DISTRIBUTE_ACCOUNT;
		}

		@Override
		public String getRequestType() {
			return HttpGet.METHOD_NAME;
		}
	}

	static class StatieResponsePackage implements
			ResponsePackage<StatisResponse> {

		private byte[] mData;

		@Override
		public void setContext(byte[] data) {
			mData = data;
		}

		@Override
		public void getResponseData(StatisResponse statisResponse) {
			if (statisResponse != null && mData != null && mData.length > 0) {
				try {
					String jsonStr = new String(mData, "utf-8");
					JSONObject jsonObj = new JSONObject(jsonStr);
					JSONObject jsonResp = jsonObj.getJSONObject("response");
					String code = jsonResp.getString("code");
					String message = jsonResp.getString("message");
					String messageCn = jsonResp.getString("message_cn");
					String prompt = jsonResp.getString("prompt");
					statisResponse.setCode(code);
					statisResponse.setMessage(message);
					statisResponse.setMessageCn(messageCn);
					statisResponse.setPrompt(prompt);
					if ("000000".equals(code)) {
						JSONObject jsonResult = jsonObj.getJSONObject("result");
						String bindedPhone = jsonResult
								.getString("bindedphone");
						statisResponse.setBindedPhone(bindedPhone);
					}
				} catch (Exception e) {
				}
			}
		}
	}

	public static class StatisResponse extends ResponseMessage {

		private String mBindedPhone = null;

		public String getBindedPhone() {
			return mBindedPhone;
		}

		public void setBindedPhone(String bindedPhone) {
			this.mBindedPhone = bindedPhone;
		}

	}
}
