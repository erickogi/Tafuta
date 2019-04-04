package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class Stats {
    @SerializedName("allAssetsCount")
    @Expose
    var allAssetsCount: String? = null
    @SerializedName("activeAssetsCount")
    @Expose
    var activeAssetsCount: String? = null
    @SerializedName("unAssignedAssetsCount")
    @Expose
    var unAssignedAssetsCount: String? = null
}