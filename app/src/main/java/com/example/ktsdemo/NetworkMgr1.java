package com.example.ktsdemo;

import android.content.Context;
import com.merlin.network.CallBack;
import com.merlin.network.DownloadCallBack;
import com.merlin.network.HttpMethod;
import com.merlin.network.NetworkDelegate;
import com.merlin.network.NetworkOption;
import com.merlin.network.controller.DownloadProgressListener;
import com.merlin.network.controller.HttpListener;
import com.merlin.network.internal.FileRequest;
import com.merlin.network.internal.NormalRequest;
import com.merlin.network.internal.Request;
import com.merlin.network.internal.RestRequest;
import com.merlin.network.internal.RestTemplate;
import com.merlin.network.internal.UpLoadRequest;
import java.io.File;
import java.util.HashMap;

/**
 * User: Simon
 * Date: 2016/3/4
 * Desc: 网络接入管理类
 */
public class NetworkMgr1 implements NetworkOption {

    private static NetworkMgr1 networkMgr;

    private RestTemplate restTemplate;

    private Request tempRequest;

    private NetworkMgr1() {
    }


    public static synchronized NetworkMgr1 getInstance() {
        if (networkMgr == null) {
            networkMgr = new NetworkMgr1();
        }
        return networkMgr;
    }

    public void init(Context context, boolean debug) {
        NetworkDelegate.getInstance().setNetworkOption(this);
        restTemplate = RestTemplate.init(context, debug);
    }

    @Override
    public <T> NetworkOption get(Class<T> className, String url, HashMap<String, ?> params, final CallBack<T> callBack) {
        tempRequest = restTemplate.add(new RestRequest<>(className, HttpMethod.GET, params, url, new HttpListener<T>() {
            @Override
            public void onResponse(T response) {
                if (callBack != null)
                    callBack.onResponse(response);
            }

            @Override
            public void onErrorResponse(Exception error) {
                if (callBack != null) {
                    callBack.onFailure(error);
                }
            }
        }));

        return this;
    }




    @Override
    public <T> NetworkOption post(Class<T> className, String url, HashMap<String, ?> params, final CallBack<T> callBack) {
        tempRequest = restTemplate.add(new Rest1Request<>(className, HttpMethod.POST, params, url, new HttpListener<T>() {
            @Override
            public void onResponse(T response) {
                if (callBack != null)
                    callBack.onResponse(response);
            }

            @Override
            public void onErrorResponse(Exception error) {
                if (callBack != null) {
                    callBack.onFailure(error);
                }
            }
        }));
        return this;
    }



    @Override
    public NetworkOption downloadFile(String url, String dir, String fileName, final CallBack<File> callBack) {
        tempRequest = restTemplate.add(new FileRequest(dir, fileName, HttpMethod.GET, url, new DownloadProgressListener<File>() {
            @Override
            public void onProgressUpdate(long bytesRead, long contentLength, boolean done) {

                if(callBack instanceof  DownloadCallBack){
                    DownloadCallBack downloadCallBack = (DownloadCallBack) callBack;
                    downloadCallBack.onProgressUpdate(bytesRead, contentLength, done);
                }
            }

            @Override
            public void onResponse(File response) {
                if (callBack != null)
                    callBack.onResponse(response);
            }

            @Override
            public void onErrorResponse(Exception error) {
                if (callBack != null) {
                    callBack.onFailure(error);
                }
            }
        }));
        return this;
    }

    @Override
    public <T> NetworkOption formPost(Class<T> className, String url, HashMap<String, ?> params, final CallBack<T> callBack) {
        tempRequest = restTemplate.add(new NormalRequest<>(className, HttpMethod.POST, params, url, new HttpListener<T>() {
            @Override
            public void onResponse(T response) {
                if (callBack != null)
                    callBack.onResponse(response);
            }

            @Override
            public void onErrorResponse(Exception error) {
                if (callBack != null) {
                    callBack.onFailure(error);
                }
            }
        }));
        return this;
    }
    @Override
    public <T> NetworkOption uploadPost(Class<T> className, String url, HashMap<String, ?> params, final CallBack<T> callBack,String path) {
        tempRequest = restTemplate.add(new UpLoadRequest(className, HttpMethod.POST, params, url, new HttpListener<T>() {
            @Override
            public void onResponse(T response) {
                if (callBack != null)
                    callBack.onResponse(response);
            }

            @Override
            public void onErrorResponse(Exception error) {
                if (callBack != null) {
                    callBack.onFailure(error);
                }
            }
        },path));
        return this;
    }
    @Override
    public NetworkOption setTag(Object tag) {
        if(tempRequest != null){
            tempRequest.setTag(tag);
        }
        return this;
    }

    @Override
    public NetworkOption cancelAll() {
        restTemplate.cancelAll();
        return this;
    }

    @Override
    public NetworkOption cancel(Object tag) {
        restTemplate.cancel(tag);
        return this;
    }

}
