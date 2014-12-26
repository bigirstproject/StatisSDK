package com.duowan.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.duowan.statis.interfaces.RequestPackage;

abstract class AbstractRequestPackage implements RequestPackage {

	protected Hashtable<String, Object> mParams;

	@Override
	public String getGetRequestParams() {
		if (mParams != null && mParams.size() >= 0) {
			StringBuilder builder = new StringBuilder();
			builder.append("?");
			final Set<String> keys = mParams.keySet();
			for (String key : keys) {
				builder.append(key).append("=").append(mParams.get(key))
						.append("&");
			}
			builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		}
		return "";
	}

	public Hashtable<String, Object> getParams() {
		return mParams;
	}

	public void setParams(Hashtable<String, Object> mParams) {
		this.mParams = mParams;
	}

	@Override
	public HttpEntity getPostRequestEntity() {
		if (mParams != null && mParams.size() >= 0) {
			final Set<String> keys = mParams.keySet();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// for (String key : keys) {
			// params.add(new BasicNameValuePair(key, (String)
			// mParams.get(key)));
			// KGLog.d("kugou",key+"   "+ mParams.get(key).toString());
			// }
			for (int i = 0; i < mParams.size(); i++) {
				String key = keys.toArray()[i].toString();
				params.add(new BasicNameValuePair(key, mParams.get(key)
						.toString()));
			}
			try {
				return new UrlEncodedFormEntity(params, HTTP.UTF_8);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();

			}

		}
		return null;
	}
}
