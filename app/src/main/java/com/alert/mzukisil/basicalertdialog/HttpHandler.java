package com.alert.mzukisil.basicalertdialog;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Mzukisi on 2017/04/20.
 */

public class HttpHandler {

    private static  final  String TAG = HttpHandler.class.getSimpleName();
    public HttpHandler(){

    }
    public String serviceCall(String jsonUrl){

        String response=null;
        try {
            URL url= new  URL(jsonUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            //read the response
            InputStream in = new BufferedInputStream(con.getInputStream());
            response = convertStreamToString(in);
        }catch (MalformedURLException e){
            Log.e(TAG,"MalfomedURLException");
        }catch (ProtocolException e){
          Log.e(TAG,"Protocol"+e.getMessage());
        }catch (IOException e){
            Log.e(TAG,"IOEx"+e.getMessage());
        }catch (Exception e){
            Log.e(TAG,"Exception"+e.getMessage());
        }

        return response ;
    }
private String convertStreamToString(InputStream is){
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder stringBuilder= new StringBuilder();

    String readLine;
    try {
        while ((readLine= reader.readLine())!=null){
            stringBuilder.append(readLine).append('\n');
        }
    }catch (IOException e){
        e.printStackTrace();
    }finally {
        try {
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    return stringBuilder.toString();
}
}
