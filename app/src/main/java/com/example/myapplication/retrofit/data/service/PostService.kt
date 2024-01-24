package com.example.myapplication.retrofit.data.service

import com.example.myapplication.retrofit.data.response.Post
import com.example.myapplication.retrofit.data.response.PostListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PostService {

    @GET("/posts")
    suspend fun findAll(
        @Query("page") page : Int,
        @Query("size") size : Int
    ) : Response<PostListResponse>

    // Query Parameter 가 많을 때 @QueryMap 을 사용하는 것을 권장
    @GET("/posts")
    suspend fun findAll(
        @QueryMap map : Map<String, Int> // map 의 value 타입이 여러개라면 Any 타비
    ) : Response<PostListResponse>

    @GET("/posts/{id}")
    suspend fun findById(
        @Path("id") id : Int
    ) : Response<Post>

    @POST("/posts")
    suspend fun insert(
        @Body body : Post
    ) : Response<Post>

    @DELETE("/posts/{id}")
    suspend fun delete(
        @Path("id") id : Int
    ) : Response<Void>

    @PUT("/posts/{id}")
    suspend fun update(
        @Path("id") id : Int,
        @Body body : Post
    ) : Response<Post>
}