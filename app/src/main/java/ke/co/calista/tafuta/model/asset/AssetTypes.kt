package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class AssetTypes {
    @SerializedName("id")
    @Expose
     var id: String? = null
    @SerializedName("code")
    @Expose
     var code: String? = null
    @SerializedName("names")
    @Expose
     var names: String? = null
    @SerializedName("timeStamp")
    @Expose
     var timeStamp: String? = null
    @SerializedName("createdById")
    @Expose
     var createdById: String? = null
    @SerializedName("statusCode")
    @Expose
     var statusCode: String? = null
    @SerializedName("statusName")
    @Expose
     var statusName: String? = null
    @SerializedName("assetDepartments")
    @Expose
    var assetDepartments: ArrayList<AssetDepartment>? = null
}
