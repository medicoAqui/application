package com.example.gabriela.medicoaqui;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {

        @Headers("X-Mashape-Key: AuuyclCPjcmshv2iOPq190OpzLrMp1FJWwejsnJrdfwOUr4h44")

        @FormUrlEncoded
        @POST("convert")
        Call<Cadastro> converterUnidade(@Field("name") String name,
                                        @Field("email") String email,
                                        @Field("sexo") String sexo,
                                        @Field("cpf") String cpf,
                                        @Field("password") String password,
                                        @Field("telefone") String telefone);

        }

