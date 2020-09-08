package com.example.mvvmexample.model


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId")
    var userId: String,
    @SerializedName("userName")
    var userName: String
)