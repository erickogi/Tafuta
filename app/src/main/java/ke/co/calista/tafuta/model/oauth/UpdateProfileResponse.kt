package com.kogicodes.sokoni.models.v1.oauth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class UpdateProfileResponse {
    @SerializedName("error")
    @Expose
    var error: Boolean? = null
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("success")
    @Expose
    var success: Boolean? = null
    @SerializedName("profile")
    @Expose
    var profile: Profile? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
}