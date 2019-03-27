package com.kogicodes.sokoni.models.v1.oauth

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
        indices = [(Index("id"))],
        primaryKeys = ["id"]
)
class Profile {
    @field:SerializedName("id")
    var id: Int? = 0
    @field:SerializedName("code")

    var code: String? = null
    @field:SerializedName("first_name")

    var firstName: String? = "name"
    @field:SerializedName("last_name")

    var lastName: String? = null
    @field:SerializedName("email")

    var email: String? = "0"
    @field:SerializedName("mobile")

    var mobile: String? = null
    @field:SerializedName("firebase_token")

    var firebaseToken: String? = null
    @field:SerializedName("password")

    var password: String? = null
    @field:SerializedName("token")

    var token: String? = null
    @field:SerializedName("created_at")

    var createdAt: String? = null
    @field:SerializedName("updated_at")

    var updatedAt: String? = null


    @Ignore constructor(firstName: String?, lastName: String?, email: String?, mobile: String?, password: String?) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.mobile = mobile
        this.password = password
    }
    @Ignore constructor(firstName: String?, email: String?,  password: String?) {
        this.firstName = firstName
        this.lastName ="null"
        this.email = email
        this.mobile = email
        this.password = password
    }


    @Ignore constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }


    constructor()

    @Ignore constructor(email: String?) {
        this.email = email
    }



}