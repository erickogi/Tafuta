package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UsersResponse {


    @SerializedName("error")
    @Expose
    var error: Boolean? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
    @SerializedName("data")
    @Expose
    var data: List<User>? = null
    @SerializedName("numPages")
    @Expose
    var numPages: Int? = null
    @SerializedName("currentPage")
    @Expose
    var currentPage: Int? = null
}