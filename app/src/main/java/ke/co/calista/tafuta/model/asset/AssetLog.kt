package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AssetLog {
    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("assetId")
    @Expose
    var assetId: String? = null
    @SerializedName("actionId")
    @Expose
    var actionId: String? = null

    @SerializedName("actionNames")
    @Expose
    var actionNames: String? = null

    @SerializedName("initId")
    @Expose
    var initId: String? = null
    @SerializedName("initNames")
    @Expose
    var initNames: String? = null


    @SerializedName("targetId")
    @Expose
    var targetId: String? = null
    @SerializedName("targetName")
    @Expose
    var targetNames: String? = null


    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("timeStamp")
    @Expose
    var timeStamp: String? = null
}