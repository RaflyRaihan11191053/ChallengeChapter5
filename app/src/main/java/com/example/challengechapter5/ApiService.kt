package com.example.challengechapter5

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("products")
    fun getAllItems(): Call<List<GetAllItemSell>>

    @GET("products/{id}")
    fun getDetailItems(@Path ("id") id: Int): Call<GetAllItemSell>

//    @POST("products")
//    fun postItem(@Body request: PostAnItemRequest): Call<PostAnItemResponse>

}
