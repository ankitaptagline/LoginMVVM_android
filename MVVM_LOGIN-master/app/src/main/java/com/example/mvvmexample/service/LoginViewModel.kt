package com.example.mvvmexample.service

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmexample.R
import com.example.mvvmexample.model.LoginResponse
import com.example.mvvmexample.utils.Glob
import com.example.mvvmexample.utils.ProgressDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel :ViewModel(){


    private  var loginResponse: MutableLiveData<LoginResponse>  = MutableLiveData()

    fun setLoginUser(
        headermap: HashMap<String, String>,
        querymap: HashMap<String, String>,
        context: Context
    ): MutableLiveData<LoginResponse> {

        loginResponse =   MutableLiveData<LoginResponse>()

        loadHeroes(headermap,querymap,context)

        return loginResponse
    }


    private fun loadHeroes(
        headermap: HashMap<String, String>,
        querymap: HashMap<String, String>,
        context: Context
    ) {
        if (Glob().isNetworkAvailable(context)) {

            val progressDialog = ProgressDialog(context)
            progressDialog.setCancelable(false)
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://private-222d3-homework5.apiary-mock.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api: BackEndApi = retrofit.create<BackEndApi>(BackEndApi::class.java)
            val call: Call<LoginResponse> = api.LOGIN(headermap, querymap)
            call.enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    loginResponse.setValue(response.body())
                    progressDialog.dismiss()
                }

                override fun onFailure(
                    call: Call<LoginResponse?>,
                    t: Throwable
                ) {
                    Log.d("response_error", t.toString())
                    progressDialog.dismiss()
                }
            })
        }else{

            val builder: AlertDialog.Builder
            builder = AlertDialog.Builder(context)
            builder.setMessage(context.resources.getString(R.string.network_msg)).setTitle(context.resources.getString(R.string.networkdialod))
                .setCancelable(false).setPositiveButton(context.resources.getString(R.string.ok),
                    DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
            val alert = builder.create()
            if (!alert.isShowing) {
                alert.show()
            }
        }
    }


}