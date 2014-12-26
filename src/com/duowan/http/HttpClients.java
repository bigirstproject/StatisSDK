package com.duowan.http;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import com.duowan.statis.interfaces.RequestPackage;
import com.duowan.statis.interfaces.ResponsePackage;

public class HttpClients {

	private final String TAG = "HttpClients";

	private HttpClient mHttpClient;

	// 是否重试，默认不重试
	private boolean mRetry;

	// 连接和读数据超时时间
	private int mConnTimeOut = 8000, mReadTimeOut = 8000;

	// 重试次数
	private int mTryNum;

	// 最大重试次数
	private int mMaxTryNum = 1;

	private HttpClients() {
		initConfigParams();
		mHttpClient = createHttpClient();
	}

	/**
	 * 创建KGHttpClient对象
	 * 
	 * @return
	 */
	public static HttpClients getInstance() {
		return new HttpClients();
	}

	/**
	 * 设置是否重试
	 * 
	 * @param retry
	 *            　true　重试，false不重试
	 */
	public void setRetry(boolean retry) {
		mRetry = retry;
	}

	/**
	 * 发送请求
	 * 
	 * @param requestPackage
	 * @param responsePackage
	 * @throws Exception
	 */
	public void request(RequestPackage requestPackage,
			ResponsePackage<Object> responsePackage, String encode)
			throws Exception {
		try {
			mTryNum++;
			start(requestPackage, responsePackage, encode);
		} catch (Exception e) {
			((org.apache.http.client.HttpClient) mHttpClient)
					.getConnectionManager().shutdown();
			if (mTryNum < mMaxTryNum && mRetry) {
				mHttpClient = createHttpClient();
				request(requestPackage, responsePackage, encode);
			} else {
				throw e;
			}
		} finally {
			stop();
		}
	}

	/**
	 * 发送请求，带回调
	 * 
	 * @param requestPackage
	 * @param watcher
	 * @throws Exception
	 */
	public void requestWithWatch(RequestPackage requestPackage,
			ProgressWatcher watcher, String encode) throws Exception {
		try {
			mTryNum++;
			start(requestPackage, watcher, encode);
		} catch (Exception e) {
			((org.apache.http.client.HttpClient) mHttpClient)
					.getConnectionManager().shutdown();
			if (mTryNum < mMaxTryNum && mRetry) {
				mHttpClient = createHttpClient();
				requestWithWatch(requestPackage, watcher, encode);
			} else {
				throw e;
			}
		} finally {
			stop();
		}
	}

	/**
	 * 中断连接
	 */
	public void stop() {
		mHttpClient.getConnectionManager().shutdown();
		mTryNum = 0;
	}

	// 开始连网
	private void start(RequestPackage requestPackage,
			ResponsePackage<Object> responsePackage, String encode)
			throws Exception {
		HttpResponse response = connect(requestPackage, encode);
		final int status = response.getStatusLine().getStatusCode();
		if (status == HttpStatus.SC_OK
				|| status == HttpStatus.SC_PARTIAL_CONTENT) {
			if (responsePackage != null) {
				readData(response.getEntity(), responsePackage);
			}
		}
	}

	// 开始连网,带回调
	private void start(RequestPackage requestPackage, ProgressWatcher watcher,
			String encode) throws Exception {
		HttpResponse response = connect(requestPackage, encode);
		final int status = response.getStatusLine().getStatusCode();
		if (status == HttpStatus.SC_OK
				|| status == HttpStatus.SC_PARTIAL_CONTENT) {
			if (watcher != null) {
				readData(response.getEntity(), watcher);
			}
		}
	}

	// 连接
	private HttpResponse connect(RequestPackage requestPackage, String encode)
			throws Exception {
		URI uri = null;
		HttpUriRequest uriReq = null;
		if (HttpGet.METHOD_NAME.equalsIgnoreCase(requestPackage
				.getRequestType())) {
			String url = requestPackage.getUrl()
					+ requestPackage.getGetRequestParams();
			if (encode.equals("") || encode.equals(null)) {
			} else {
				url = requestPackage.getUrl()
						+ URLEncoder.encode(
								requestPackage.getGetRequestParams(), encode);
			}
			uri = new URI(url);
			uriReq = new HttpGet(uri);
		} else {

			uri = new URI(requestPackage.getUrl());
			uriReq = new HttpPost(uri);
			((HttpPost) uriReq)
					.setEntity(requestPackage.getPostRequestEntity());
		}
		uriReq.addHeader(HTTP.USER_AGENT, "Mozilla/5.0");
		uriReq.addHeader("Accept-Encoding", "gzip, deflate");
		HttpResponse response = mHttpClient.execute(uriReq);
		return response;
	}

	// 读取数据
	private void readData(HttpEntity entity,
			ResponsePackage<Object> responsePackage) throws Exception {
		byte[] data = null;
		if (isGzipStream(entity)) {
			data = EntityUtils.toByteArray(new GzipDecompressingEntity(entity));
		} else {
			data = EntityUtils.toByteArray(entity);
		}
		responsePackage.setContext(data);
	}

	// 读取数据，带回调
	private void readData(HttpEntity httpEntity, ProgressWatcher watcher)
			throws Exception {
		byte[] buffer = new byte[10 * 1024];
		InputStream instream = null;
		if (isGzipStream(httpEntity)) {
			instream = new GZIPInputStream(httpEntity.getContent());
		} else {
			instream = httpEntity.getContent();
		}
		BufferedInputStream bis = new BufferedInputStream(instream);
		ByteArrayBuffer bab = new ByteArrayBuffer(10 * 1024);
		int read = 0;
		int haveRead = 0;
		int totalSize = (int) httpEntity.getContentLength();
		try {
			while ((read = bis.read(buffer)) != -1) {
				haveRead += read;
				bab.append(buffer, 0, read);
				byte[] data = bab.toByteArray();
				watcher.onLoading(data, haveRead, totalSize);
				bab.clear();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			bis.close();
			instream.close();
		}
		watcher.onFinish();
	}

	// 是否Gzip压缩流
	private boolean isGzipStream(HttpEntity entity)
			throws IllegalStateException, IOException {
		if (entity != null) {
			Header header = entity.getContentEncoding();
			return header != null && header.getValue() != null
					&& header.getValue().toLowerCase().indexOf("gzip") != -1;
		}
		return false;
	}

	// 初始化参数
	private void initConfigParams() {
		mConnTimeOut = 8000;
		mReadTimeOut = 8000;
		mMaxTryNum = 1;
	}

	// 创建一个httpClient对象
	private HttpClient createHttpClient() {
		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		httpParams.setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
		HttpConnectionParams.setConnectionTimeout(httpParams, mConnTimeOut);
		HttpConnectionParams.setSoTimeout(httpParams, mReadTimeOut);
		return client;
	}

	/**
	 * 下载进度接口
	 */
	public interface ProgressWatcher {

		public void onLoading(byte[] data, int read, int totalSize);

		public void onFinish();

		public void onError();
	}

}
