package com.example.githubissuetracker.network;

import com.apollographql.apollo.ApolloClient;
import com.example.githubissuetracker.BuildConfig;


import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApolloConnector {
    private static final String BASE_URL = "https://api.github.com/graphql";
    public static final String access_token = BuildConfig.ACCES_TOKEN;

    public static ApolloClient setupClient() {
        OkHttpClient client = new OkHttpClient.Builder().addNetworkInterceptor(new AuthorizationInterceptor()).build();

        return ApolloClient.builder().serverUrl(BASE_URL).okHttpClient(client).build();
    }

    public static class AuthorizationInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(Chain chain) throws IOException {
            final Request request = chain.request()
                    .newBuilder()
                    //TODO: store token in shared preferences
                    .addHeader("Authorization", access_token)
                    .build();
            return chain.proceed(request);
        }
    }
}