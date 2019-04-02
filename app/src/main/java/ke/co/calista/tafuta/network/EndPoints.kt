package com.kogicodes.sokoni.network



import ke.co.calista.tafuta.model.asset.AssetsResponse
import ke.co.calista.tafuta.model.oauth.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * @author kogi
 */
interface EndPoints {
    @FormUrlEncoded
    @POST("auth/signin.php")
    fun signIn(@Field("email") email: String?, @Field("password") password: String?): Call<LoginResponse>

    @FormUrlEncoded
    @POST("auth/signup.php")
    fun signUp(@Field("email") email: String?, @Field("password") password: String?, @Field("firstName") firstName: String?, @Field("lastName") lastName: String?, @Field("mobile") mobile: String?): Call<LoginResponse>


    @FormUrlEncoded
    @POST("assets/fetch.php")
    fun assets(@Field("id") id: String?, @Field("perPage") perPage: String?, @Field("pageNo") pageNo: String?): Call<AssetsResponse>


    //http://skylar.co.ke/assetmanagement/api/dashboard/info.php
    @GET("dashboard/info.php")
    // fun dashBoard(): Call<BaseData>






}
