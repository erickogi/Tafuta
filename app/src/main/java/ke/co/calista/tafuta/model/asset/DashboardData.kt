package ke.co.calista.tafuta.model.asset


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class DashboardData {
    @SerializedName("stats")
    @Expose
    var stats: Stats? = null
}