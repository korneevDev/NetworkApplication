package ru.mik0war.netapp.data.cloud

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkAPI {

    @GET("items")
    suspend fun getItems(): List<ServerDTO>

    @GET("items/{id}")
    suspend fun getItem(@Path("id") id: Int): ServerDTO

    @POST("items")
    suspend fun createItem(@Body item: ServerDTO): ServerDTO

}