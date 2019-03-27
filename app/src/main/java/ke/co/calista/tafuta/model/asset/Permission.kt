package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//1  all    ----      2 specific
class Permission {
  

    @SerializedName("permission_view")
    @Expose
    var permissionView: Int? = null

    @SerializedName("permission_view_allowed")
    @Expose
    var permissionViewAllowed: List<String>? = null




    @SerializedName("permission_edit")
    @Expose
    var permissionEdit: Int? = null

    @SerializedName("permission_edit_allowed")
    @Expose
    var permissionEditAllowed: List<String>? = null





    @SerializedName("permission_move")
    @Expose
    var permissionMove: Int? = null

    @SerializedName("permission_move_allowed")
    @Expose
    var permissionMoveAllowed: List<String>? = null











}