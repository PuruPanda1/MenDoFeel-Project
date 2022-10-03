package com.purabmodi.exoplayer.apiTools

import com.purabmodi.exoplayer.apiTools.dataclass.video
import retrofit2.Response

class videoRepository {
    suspend fun getVideos(options: HashMap<String,String>):Response<video>{
        return RetrofitInstance.api.getVideos(options)
    }
}