package com.example.mvvmexample.ui

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmexample.R
import com.example.mvvmexample.model.LoginResponse
import com.example.mvvmexample.service.LoginViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Init()
    }

    private fun Init() {
        btn_signin.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            btn_signin -> {
                if(isValidation()){
                    signInAPICall()
                }
            }
        }

    }

    private fun  signInAPICall() {

        val email = ed_username.text.toString().trim()
        val password = ed_password.text.toString().trim()

        val headermap = HashMap<String, String>()
        val querymap = HashMap<String, String>()
        headermap.put("Content-Type","application/json")
        headermap.put("IMSI","357175048449937")
        headermap.put("IMEI","510110406068589")
        querymap.put("username",email)
        querymap.put("password",password)

        val model: LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        model.setLoginUser(headermap,querymap,this).observe(this, object : Observer<LoginResponse?> {
            override fun onChanged(loginResponse: LoginResponse?) {
                Log.d("loginResponse",loginResponse.toString())

                if(loginResponse!=null){
                    if(loginResponse.errorCode == "01"){
                        opendialog(resources.getString(R.string.errordialog),loginResponse.errorMessage)
                    }else if(loginResponse.errorCode == "00"){
                        opendialog(resources.getString(R.string.successdialog),loginResponse.errorMessage)
                    }
                }


            }
        })

    }

    private fun opendialog(title: String, message: String) {

        val builder: AlertDialog.Builder
        builder = AlertDialog.Builder(this)
        builder.setMessage(message).setTitle(title)
            .setCancelable(false).setPositiveButton(resources.getString(R.string.ok),
                DialogInterface.OnClickListener { dialog, id -> dialog.dismiss() })
        val alert = builder.create()
        if (!alert.isShowing) {
            alert.show()
        }

    }

    private fun isValidation(): Boolean {
        if(ed_username.text.isNullOrEmpty()){
            ed_username.requestFocus()
            ed_username.setError(resources.getString(R.string.empty_username))
            return false
        }else if(ed_password.text.isNullOrEmpty()){
            ed_password.requestFocus()
            ed_password.setError(resources.getString(R.string.empty_password))
            return false
        }else{
            return true
        }

    }

}