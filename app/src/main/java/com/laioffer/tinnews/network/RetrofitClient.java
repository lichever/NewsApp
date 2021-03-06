package com.laioffer.tinnews.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    // TODO: Assign your API_KEY here
    private static final String API_KEY = "9470934f7a5c42d3abbb0fa45a6ed6a8";
    private static final String BASE_URL = "https://newsapi.org/v2/";


    /**
    *  OkHttpClient 是 Retrofit 底层
    * */
    public static Retrofit newInstance() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())// mapping
                .client(okHttpClient)

                .build();
    }

    private static class HeaderInterceptor implements Interceptor {//中途 add header

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original
                    .newBuilder()
                    .header("X-Api-Key", API_KEY)
                    .build();
            return chain.proceed(request);
        }
    }
}
