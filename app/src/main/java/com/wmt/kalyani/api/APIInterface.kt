package com.wmt.kalyani.api

import com.wmt.kalyani.model.RegisterResModel
import com.wmt.kalyani.model.UserResModel
import retrofit2.Call
import retrofit2.http.*

interface APIInterface {

    @FormUrlEncoded
    @POST("register")
    fun registeruser(
        @Field("username") username: String?,
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<RegisterResModel>

    @Headers(*["Content-Type: application/json;charset=UTF-8"])
    @GET("list")
    fun getusers(
        @Query("page")page:Int?,
        @Header("Authorization") auth: String?
    ): Call<UserResModel>

}