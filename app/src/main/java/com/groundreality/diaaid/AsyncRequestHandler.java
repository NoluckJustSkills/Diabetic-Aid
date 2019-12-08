package com.groundreality.diaaid;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;

import org.json.JSONObject;

import java.security.KeyStore;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;



public class AsyncRequestHandler {

    private static AsyncRequestHandler instance;

    private AsyncHttpClient client;

    private AsyncRequestHandler(){
        client = new AsyncHttpClient();
    }

    public static AsyncRequestHandler getInstance(){
        if(instance == null){
            instance = new AsyncRequestHandler();
        }
        return instance;
    }

    // You can add more parameters if you need here.
    public void makeRequest(JSONObject params, String apilink, Context context, final RequestListener listener) {

        StringEntity entity = null;
        MySSLSocketFactory sf=null;
        try {
            entity = new StringEntity(params.toString());

            entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            sf = new MySSLSocketFactory(trustStore);
        } catch (Exception e) {
            e.printStackTrace();
        }

        sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        client.setSSLSocketFactory(sf);
        client.setTimeout(25000);
        client.post(context,apilink, entity, "application/json", new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                //Some debugging code here
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                listener.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                //Some debugging code here, show retry dialog, feedback etc.
                listener.onFailure(statusCode, headers, response);

            }

            @Override
            public void onRetry(int retryNo) {
            }
        });
    }
}
