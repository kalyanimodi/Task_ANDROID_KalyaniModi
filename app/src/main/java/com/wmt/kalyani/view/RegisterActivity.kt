package com.wmt.kalyani.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.wmt.kalyani.R
import com.wmt.kalyani.api.ApiRequest
import com.wmt.kalyani.api.ResponceCallback
import com.wmt.kalyani.model.RegisterModel
import com.wmt.kalyani.model.RegisterResModel
import com.wmt.kalyani.utils.ConstantCode
import kotlinx.android.synthetic.main.activity_main.*


class RegisterActivity : AppCompatActivity() {

    var emailPattern: String? = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bv_register.setOnClickListener(View.OnClickListener {
            GetRegistration()
        })
    }

    private fun GetRegistration() {
        val username = edt_username.text.toString()
        val email = edt_email.text.toString()
        val pass = edt_pass.text.toString()
        val conf_pass = edt_confpass.text.toString()

        if (username.isNullOrEmpty()) {
            edt_username.setError("Username Required !")
            edt_username.requestFocus()
            return
        }
        if (email.isNullOrEmpty() || !email!!.trim { it <= ' ' }
                .matches(emailPattern!!.toRegex())) {
            edt_email.setError("Invalid Email ID Required !")
            edt_email.requestFocus()
            return
        }
        if (pass.isNullOrEmpty() || pass.length <= 5) {
            edt_pass.setError("6 digit Password required !")
            edt_pass.requestFocus()
            return
        }
        if (conf_pass.isNullOrEmpty() || conf_pass.length <= 5) {
            edt_confpass.setError("6 digit Conform Password Required !")
            edt_confpass.requestFocus()
            return
        }
        if (pass != conf_pass) {
            edt_confpass.setError("Confirm Password Incurrect")
            edt_confpass.requestFocus()
            return
        } else {
            CallRegisterAPI(username, email, pass)
        }

    }

    private fun CallRegisterAPI(
        username: String,
        email: String,
        pass: String
    ) {
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Please Wait")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
        val model = RegisterModel()
        model.username = username
        model.email = email
        model.password = pass
        var apiRequest = ApiRequest()
        apiRequest.registeruser(model, callback)
    }

    val callback: ResponceCallback = object : ResponceCallback {
        override fun ResponceSuccessCallback(o: Any?) {
            try {
                progressDialog!!.dismiss()
                if (o is RegisterResModel) {

                    val status = o.meta!!.status
                    val message = o.meta!!.message
                    if (status != null) {
                        Log.e("status=", status)
                    }
                    if (status.equals("ok")) {
                        //---------SAVE VALUES IN SHAREDPREFERENCE------------
                        val gson = Gson()
                        val users = gson.toJson(o.data!!.user)

                        val token = o.data!!.token!!.token
                        val token_type = o.data!!.token!!.type

                        ConstantCode.savesharedpreference(
                            this@RegisterActivity,
                            "users_object",
                            users
                        )
                        ConstantCode.savesharedpreference(this@RegisterActivity, "token", o.data!!.token!!.token)
                        ConstantCode.savesharedpreference(
                            this@RegisterActivity,
                            "token_type",
                            token_type
                        )
                        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
                        val intent2 = Intent(this@RegisterActivity, HomeActivity::class.java)
                        intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent2)
                    } else {
                        ConstantCode.ShowpopupDialoge(this@RegisterActivity, message)
                    }
                } else {
                    ConstantCode.ShowpopupDialoge(this@RegisterActivity,"unique validation failed on username")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        override fun ResponceFailCallback(o: Any?) {
            if (progressDialog != null) {
                if (progressDialog!!.isShowing) {
                    progressDialog!!.dismiss()
                }
            }
            ConstantCode.ShowpopupDialoge(
                this@RegisterActivity,
                "Something Went Wrong.Please try again !"
            )
        }

    }
}