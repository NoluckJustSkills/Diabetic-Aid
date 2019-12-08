package com.groundreality.diaaid;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Vijesh on 16-09-2016.
 */
public interface RequestListener {
    void onSuccess(int statusCode, Header[] headers, JSONObject response);
    void onFailure(int statusCode, Header[] headers, JSONObject response);

}