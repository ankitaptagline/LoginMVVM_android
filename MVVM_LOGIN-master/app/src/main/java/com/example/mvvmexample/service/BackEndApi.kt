package com.example.mvvmexample.service

import com.example.mvvmexample.model.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface BackEndApi {

    @FormUrlEncoded
    @POST("login")
    fun LOGIN(@HeaderMap headerMap: Map<String, String>?, @FieldMap params: Map<String, String>?): Call<LoginResponse>



}