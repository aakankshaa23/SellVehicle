package com.dextroxd.sellvehicle.network;

import com.dextroxd.sellvehicle.network.Login.model.LoginPost;
import com.dextroxd.sellvehicle.network.Login.model.LoginResponse;
import com.dextroxd.sellvehicle.network.Login.model.Response;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface
{
    @POST("user/register")
    Call<Response> saveUser(
            @Body Response response
    );

    @POST("user/login")
    Call<LoginResponse> loginUser(
            @Body LoginPost loginPost
    );

    @GET("property/get")
    Call<List<com.dextroxd.sellvehicle.network.PostProperty.model.Response>> getProperty();

}
