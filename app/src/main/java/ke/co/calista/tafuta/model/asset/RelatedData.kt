package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class RelatedData : Serializable {
    @SerializedName("label")
    @Expose
    var label: String? = null
    @SerializedName("value")
    @Expose
    var value: String? = null
    @SerializedName("inputType")
    @Expose
    var inputType: Int? = null

    constructor(label: String?, value: String?, inputType: Int?) {
        this.label = label
        this.value = value
        this.inputType = inputType
    }
}