package com.wmt.kalyani.api

import android.content.Context
import com.wmt.kalyani.model.RegisterModel
import com.wmt.kalyani.model.RegisterResModel
import com.wmt.kalyani.model.UserData
import com.wmt.kalyani.model.UserResModel
import com.wmt.kalyani.utils.ConstantCode
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiRequest {
    var context: Context? = null
    var retrofit: Retrofit? = null
    var apiInterface: APIInterface? = null
    var token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjExMzQsImlhdCI6MTYwODE4OTM5N30.8--AfuWuetcosP2hDyhv4UqwvC9PBSBMOpS6rXblNU4"

    constructor() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        retrofit = Retrofit.Builder()
            .baseUrl(ConstantCode.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        apiInterface = retrofit!!.create(APIInterface::class.java)
    }

    fun registeruser(model: RegisterModel, callback: ResponceCallback) {
        val responceCallback: Call<RegisterResModel> =
            apiInterface!!.registeruser(
                model.username!!,
                model.email!!,
                model.password!!
            )
        responceCallback.enqueue(object : Callback<RegisterResModel> {
            override fun onResponse(
                call: Call<RegisterResModel>,
                response: Response<RegisterResModel>
            ) {
                callback.ResponceSuccessCallback(response.body())
            }

            override fun onFailure(call: Call<RegisterResModel>, t: Throwable) {
                callback.ResponceFailCallback(t.message)
            }
        })
    }
    fun getusers(model: UserData, callback: ResponceCallback) {
        val responceCallback: Call<UserResModel> =
            apiInterface!!.getusers(model.page,model.type+" "+model.token)
        responceCallback.enqueue(object : Callback<UserResModel> {
            override fun onResponse(
                call: Call<UserResModel>,
                response: Response<UserResModel>
            ) {
                callback.ResponceSuccessCallback(response.body())
            }

            override fun onFailure(call: Call<UserResModel>, t: Throwable) {
                callback.ResponceFailCallback(t.message)
            }
        })

    }

}