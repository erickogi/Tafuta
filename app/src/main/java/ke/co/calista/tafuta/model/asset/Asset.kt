package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Asset {
    @SerializedName("code")
    @Expose
    var assetCode: String? = null


    @SerializedName("tag")
    @Expose
    var assetTag: String? = null


    @SerializedName("name")
    @Expose
    var assetName: String? = null

    @SerializedName("location")
    @Expose
    var assetLocation: String? = null


    @SerializedName("current_assignemt")
    @Expose
    var assetCurrentAssignment: Assignment? = null

    @SerializedName("current_assignemt_code")
    @Expose
    var assetCurrentAssignmentCode: Int? = null



    @SerializedName("accusation_date")
    @Expose
    var assetAccusationDate: String? = null

    @SerializedName("serial_number")
    @Expose
    var assetSerialNumber: String? = null

    @SerializedName("condition")
    @Expose
    var assetCondition: String? = null

    @SerializedName("description")
    @Expose
    var assetDescription: String? = null


    @SerializedName("permissions")
    @Expose
    var permission: Permission? = null














    @SerializedName("created_by")
    @Expose
    var createdBy: Int? = null

    @SerializedName("created_at")
    @Expose
    var createdAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var updatedAt: String? = null

}