package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class NewAsset(
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("code")
    @Expose
    var code: String? = null,

    @SerializedName("typeId")
    @Expose
    var typeId: String? = null,
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null,


    @SerializedName("departmentId")
    @Expose
    var departmentId: String? = null,
    @SerializedName("departmentName")
    @Expose
    var departmentName: String? = null,

    @SerializedName("categoryId")
    @Expose
    var categoryId: String? = null,
    @SerializedName("categoryName")
    @Expose
    var categoryName: String? = null,
    @SerializedName("subCategoryId")
    @Expose
    var subCategoryId: String? = null,
    @SerializedName("subCategoryName")
    @Expose
    var subCategoryName: String? = null,
    @SerializedName("sNo")
    @Expose
    var sNo: String? = null,
    @SerializedName("scanCode")
    @Expose
    var scanCode: String? = null,
    @SerializedName("names")
    @Expose
    var names: String? = null,
    @SerializedName("stateCondition")
    @Expose
    var stateCondition: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("imageUrls")
    @Expose
    var imageUrls: String? = null,
    @SerializedName("depreciationRate")
    @Expose
    var depreciationRate: String? = null,
    @SerializedName("relatedData")
    @Expose
    var relatedData: ArrayList<RelatedData>? = null,

    @SerializedName("metaData")
    @Expose
    var metaData: String? = null,
    @SerializedName("createdById")
    @Expose
    var createdById: String? = null,

    @SerializedName("timeStamp")
    @Expose
    var timeStamp: String? = null,
    @SerializedName("assignment")
    @Expose
    var assignment: Int? = null,
    @SerializedName("statusCode")
    @Expose
    var statusCode: String? = null,
    @SerializedName("statusName")
    @Expose
    var statusName: String? = null,
    @SerializedName("createByNames")
    @Expose
    var createByNames: String? = null,

    @SerializedName("assetLog")
    @Expose
    var assetLog: ArrayList<AssetLog>? = null


) : Serializable