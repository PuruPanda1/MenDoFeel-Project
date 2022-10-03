package com.purabmodi.exoplayer.apiTools

import com.purabmodi.exoplayer.apiTools.dataclass.video
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SimpleApi {
    @GET("/api/videos/")
    suspend fun getVideos(@QueryMap options: Map<String, String>): Response<video>
}