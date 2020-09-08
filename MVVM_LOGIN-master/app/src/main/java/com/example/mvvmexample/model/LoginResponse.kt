package com.example.mvvmexample.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("errorCode")
    var errorCode: String,
    @SerializedName("errorMessage")
    var errorMessage: String,
    @SerializedName("user")
    var user: User
)