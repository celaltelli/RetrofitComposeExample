package com.tellioglu.retrofitcompose.model

import com.google.gson.annotations.SerializedName

class DeveloperModel {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("website")
    var website: String? = null

    @SerializedName("email")
    var email: String? = null
}