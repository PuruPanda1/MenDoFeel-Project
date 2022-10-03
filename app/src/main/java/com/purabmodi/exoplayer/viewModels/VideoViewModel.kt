package com.purabmodi.exoplayer.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.purabmodi.exoplayer.apiTools.dataclass.video
import com.purabmodi.exoplayer.apiTools.videoRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class VideoViewModel(private val repository: videoRepository):ViewModel() {
    val videoList:MutableLiveData<Response<video>> = MutableLiveData()

    fun getVideos(options:HashMap<String,String>){
        viewModelScope.launch {
            val response = repository.getVideos(options)
            videoList.value = response
        }
    }
}