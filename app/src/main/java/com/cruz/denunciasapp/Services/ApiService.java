package com.cruz.denunciasapp.Services;

import com.cruz.denunciasapp.Models.Denuncia;
import com.cruz.denunciasapp.Models.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by FERNANDO on 16/11/2017.
 */

public interface ApiService {


    String API_BASE_URL = "https://muni-denuncias-fernando19.c9users.io";



    @FormUrlEncoded
    @POST("api/v1/login")
    Call<User> loginUser
            (@Field("username")String username,
             @Field("password") String password
            );

    @FormUrlEncoded
    @POST("api/v1/register")
    Call<ResponseMessage> createUsuario(@Field("username") String username,
                                        @Field("password") String password,
                                        @Field("email") String email);

    @GET("api/v1/denuncias")
    Call<List<Denuncia>> getDenuncias();

    @FormUrlEncoded
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenuncia(
                                         @Field("titulo") String titulo,
                                         @Field("comentario") String comentario,
                                         @Field("latitud") String latitud,
                                         @Field("longitud") String longitud,
                                         @Field("users_id") String users_id

                                         );

    @Multipart
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenunciaWithImage(

            @Part("titulo") RequestBody titulo,
            @Part("comentario") RequestBody comentario,
            @Part("latitud") RequestBody latitud,
            @Part("longitud") RequestBody longitud,
            @Part MultipartBody.Part imagen,
            @Part("users_id") RequestBody users_id

    );


    @DELETE("/api/v1/denuncias/{id}")
    Call<ResponseMessage> destroyDenuncia(@Path("id") int id);



}
