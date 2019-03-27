package ke.co.calista.tafuta.model.asset

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Assignment {
    @SerializedName("assignment_code")
    @Expose
    var AssignementCode: String? = null


    @SerializedName("assignee_id")
    @Expose
    var AssigneeId: Int? = null


    @SerializedName("assignee_name")
    @Expose
    var AssigneeName: String? = null



    @SerializedName("assignee_created_by")
    @Expose
    var AssigneeCreatedBy: Int? = null

    @SerializedName("assignee_created_at")
    @Expose
    var AssigneeCreatedAt: String? = null

    @SerializedName("assignee_updated_at")
    @Expose
    var AssigneeUpdatedAt: String? = null


}
