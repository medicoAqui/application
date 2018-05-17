package com.example.gabriela.medicoaqui;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebClient {

    public String post(String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String url = "http://medicoishere.herokuapp.com/cliente/add";

        Request.Builder builder = new Request.Builder();

        builder.url(url);

        MediaType mediaType =
                MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(mediaType, json);
        builder.post(body);

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        String jsonDeResposta = response.body().toString();

        return jsonDeResposta;
    }

    public String get() throws IOException {

        String url = "http://medicoishere.herokuapp.com/cliente/clientes";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();

        String jsonDeResposta = response.body().string();

        return jsonDeResposta;
    }

}
