package com.kogicodes.sokoni.network


import ke.co.calista.tafuta.model.asset.*
import ke.co.calista.tafuta.model.oauth.LoginResponse
import retrofit2.Call
import retrofit2.http.*


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


    @GET("dashboard/info.php")
    fun dashBoard(): Call<DashboardResponse>


    @POST("assets/add.php")
    fun sendAsset(@Body asset: NewAsset): Call<SendAssetResponse>


    @FormUrlEncoded
    @POST("assets/scan.php")
    fun scanAsset(@Field("scanCode") id: String?): Call<AssetResponse>


    @FormUrlEncoded
    @POST("assets/assign.php")
    fun assignAsset(
        @Field("assetId") assetId: String?, @Field("actionId") actionId: String?, @Field("initId") initId: String?, @Field(
            "targetId"
        ) targetId: String?, @Field("description") description: String?
    ): Call<AssignResponse>


    @FormUrlEncoded
    @POST("users/fetch.php")
    fun users(@Field("perPage") perPage: String?, @Field("pageNo") pageNo: String?): Call<UsersResponse>


}
