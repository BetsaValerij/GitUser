package com.sample.gituser.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sample.gituser.R;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GitNetworkService {
    private static GitNetworkService mInstance;
    private Retrofit mRetrofit;
    private String SERVER_BASE_URL;

    private GitNetworkService(Context context){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setLenient()
                .create();
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_0)
                .allEnabledCipherSuites()
                .build();
        OkHttpClient client = new OkHttpClient();
        try {
            TLSSocketFactory tlsSocketFactory=new TLSSocketFactory();
            if (tlsSocketFactory.getTrustManager()!=null) {
                client = new OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                        .build();
            }
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        SERVER_BASE_URL = context.getString(R.string.SERVER_URL);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(SERVER_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    public GitApi getGitApi(){
        return mRetrofit.create(GitApi.class);
    }

    public static GitNetworkService getInstance(Context context){
        if (mInstance == null){
            mInstance = new GitNetworkService(context);
        }
        return mInstance;

    }
}
