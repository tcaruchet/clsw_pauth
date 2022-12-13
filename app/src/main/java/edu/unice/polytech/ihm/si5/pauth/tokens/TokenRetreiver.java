package edu.unice.polytech.ihm.si5.pauth.tokens;

import android.util.Log;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TokenRetreiver {
    //get token from API by performing GET request like this:
    //https://tokenapi.caruchet.dev/token/Generate/142/4.9876728/67.678999
    //where 142 is the heart rate, 4.9876728 is the latitude and 67.678999 is the longitude
    //return the token as a string
    public static String getToken(int hr, double latitude, double longitude) {
        AtomicReference<String> res = new AtomicReference<>("");


        CompletableFuture.runAsync(() -> {
            try  {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://tokenapi.caruchet.dev/token/Generate/" + hr + "/" + latitude + "/" + longitude)
                        .build(); // defaults to GET

                Response response = client.newCall(request).execute();

                res.set(response.body().string());
            } catch (Exception e) {
                Log.e("TokenRetreiver", "Error while getting token", e);
            }
        }).join();

        return res.get();
    }
}
