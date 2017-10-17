package xyz.dushyant31.laundroapp.laundrodetails;

import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class ControllerDetails {



    public static final String TAG = "TAG";

    public static final String WAURL2="https://script.google.com/macros/s/AKfycbzQQtEmRHSBeOeuhWT5OfMiJ9BVzD6ymncTa1u5sMY-w4nyX-9G/exec?";

    // EG : https://script.google.com/macros/s/AKfycbwXXXXXXXXXXXXXXXXX/exec?
//Make Sure '?' Mark is present at the end of URL
    private static Response response;



    public static JSONObject insertData(String id, String shirt, String pant) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(WAURL2+"action=insert&id="+id+"&shirt="+shirt+"&pant="+pant)
                    .build();
            response = client.newCall(request).execute();
            //    Log.e(TAG,"response from gs"+response.body().string());
            return new JSONObject(response.body().string());


        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
        }
        return null;
    }

//    public static JSONObject updateData(String id, String shirt, String pant) {
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(WAURL2+"action=update&id="+id+"&shirt="+shirt+"&pant="+pant)
//                    .build();
//            response = client.newCall(request).execute();
//            //    Log.e(TAG,"response from gs"+response.body().string());
//            return new JSONObject(response.body().string());
//
//
//        } catch (@NonNull IOException | JSONException e) {
//            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
//        }
//        return null;
//    }
//
//    public static JSONObject readData(String id) {
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(WAURL2+"action=read&id="+id)
//                    .build();
//            response = client.newCall(request).execute();
//            // Log.e(TAG,"response from gs"+response.body().string());
//            return new JSONObject(response.body().string());
//
//
//        } catch (@NonNull IOException | JSONException e) {
//            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
//        }
//        return null;
//    }
//
//    public static JSONObject deleteData(String id) {
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(WAURL2+"action=delete&id="+id)
//                    .build();
//            response = client.newCall(request).execute();
//            // Log.e(TAG,"response from gs"+response.body().string());
//            return new JSONObject(response.body().string());
//
//
//        } catch (@NonNull IOException | JSONException e) {
//            Log.e(TAG, "recieving null " + e.getLocalizedMessage());
//        }
//        return null;
//    }

}