package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("names")
    @Expose
    var names: String? = null
    @SerializedName("mobile")
    @Expose
    var mobile: String? = null
    @SerializedName("email")
    @Expose
    var email: String? = null
    @SerializedName("typeId")
    @Expose
    var typeId: String? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null
    @SerializedName("departmentId")
    @Expose
    var departmentId: String? = null
    @SerializedName("departmentModel")
    @Expose
    var departmentModel: String? = null
    @SerializedName("ownerId")
    @Expose
    var ownerId: String? = null
    @SerializedName("ownerModel")
    @Expose
    var ownerModel: Any? = null
    @SerializedName("isLoggedIn")
    @Expose
    var isLoggedIn: String? = null
    @SerializedName("photo")
    @Expose
    var photo: Any? = null
    @SerializedName("timeStamp")
    @Expose
    var timeStamp: String? = null
    @SerializedName("createdBy")
    @Expose
    var createdBy: Any? = null
    @SerializedName("statusCode")
    @Expose
    var statusCode: String? = null
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null
}