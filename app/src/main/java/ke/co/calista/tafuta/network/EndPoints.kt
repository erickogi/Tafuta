package com.kogicodes.sokoni.network



import com.kogicodes.sokoni.models.v1.oauth.Oauth
import com.kogicodes.sokoni.models.v1.oauth.Profile
import com.kogicodes.sokoni.models.v1.oauth.UpdateProfileResponse
import retrofit2.Call
import retrofit2.http.*


/**
 * @author kogi
 */
interface EndPoints {
    @FormUrlEncoded
    @POST("aouth/signin.php")
    fun signIn(@Field("email") email: String?, @Field("password") password: String?): Call<Oauth>

    @FormUrlEncoded
    @POST("aouth/signup.php")
    fun signUp(@Field("email") email: String?, @Field("password") password: String?, @Field("firstName") firstName: String?, @Field("lastName") lastName: String?, @Field("mobile") mobile: String?): Call<Oauth>

    @POST("aouth/update.php")
    fun updateProfile(@Body item: Profile): Call<UpdateProfileResponse>







}
