package ke.co.calista.tafuta.model.oauth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class LoginResponse {

    @SerializedName("error")
    @Expose
    var error: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: LoginData? = null
}