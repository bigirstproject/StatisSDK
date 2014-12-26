package com.duowan.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.text.TextUtils;

public class UrlEncodeUtil {
	public static String encode(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		return URLEncoder.encode(str).replace("+", "%20");
	}

	public static String encode(String str, String enc) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		try {
			return URLEncoder.encode(str, enc).replace("+", "%20");
		} catch (UnsupportedEncodingException e) {
			return encode(str);
		}

	}
}
