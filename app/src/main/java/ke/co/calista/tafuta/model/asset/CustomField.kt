package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CustomField {
    @SerializedName("code")
    @Expose
    var customFieldCode: String? = null


    @SerializedName("label")
    @Expose
    var customFieldLabel: String? = null
    @SerializedName("value")
    @Expose
    var customFieldValue: String? = null

    @SerializedName("created_by")
    @Expose
    var customFieldCreatedBy: Int? = null

    @SerializedName("created_at")
    @Expose
    var customFieldCreatedAt: String? = null

    @SerializedName("updated_at")
    @Expose
    var customFieldUpdatedAt: String? = null

}