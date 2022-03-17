package com.example.thecatapi.retrofit.service

import com.example.thecatapi.model.CatElement
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @Headers(
        "x-api-key: 4afa11bc-704c-4f24-813d-7bb0413904a6"
    )

    @GET("images/search")
    fun listPost(
        @Query("limit") per_page: Int = 10,
        @Query("page") page: Int
    ): Call<ArrayList<CatElement>>

    @Headers(
        "x-api-key: 4afa11bc-704c-4f24-813d-7bb0413904a6"
    )
    @Multipart
    @POST("images/upload")
    fun uploadSpecificImage(
        @Part file: MultipartBody.Part,
        @Part("sub_id") sub_id: String?
    ): Call<ResponseBody>
}