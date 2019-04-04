package ke.co.calista.tafuta.model.oauth

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import ke.co.calista.tafuta.model.asset.AssetTypes
import org.jetbrains.annotations.NotNull

@Entity(
    indices = [(Index("id"))],
    primaryKeys = ["id"]
)


class LoginData {
    @field:SerializedName("id")
    @Expose

    @NotNull
    var id: String? = null
    @field:SerializedName("code")
    @Expose
    var code: String? = null
    @field:SerializedName("names")
    @Expose
    var names: String? = null
    @field:SerializedName("mobile")
    @Expose
    var mobile: String? = null
    @field:SerializedName("email")
    @Expose
    var email: String? = null
    @field:SerializedName("password")
    @Expose
    var password: String? = null
    @field:SerializedName("typeId")
    @Expose
    var typeId: String? = null
    @field:SerializedName("typeName")
    @Expose
    var typeName: String? = null
    @field:SerializedName("departmentId")
    @Expose
    var departmentId: String? = null
    @field:SerializedName("departmentModel")
    @Expose
    var departmentModel: String? = null
    @field:SerializedName("ownerId")
    @Expose
    var ownerId: String? = null


    @field:SerializedName("photo")
    @Expose
    var photo: String? = null
    @field:SerializedName("timeStamp")
    @Expose
    var timeStamp: String? = null
    @field:SerializedName("createdBy")
    @Expose
    var createdBy: String? = null
    @field:SerializedName("statusCode")
    @Expose
    var statusCode: String? = null
    @field:SerializedName("statusName")
    @Expose
    var statusName: String? = null
    @field:SerializedName("assetTypes")
    @Expose
    var filters: ArrayList<AssetTypes>? = null
}

