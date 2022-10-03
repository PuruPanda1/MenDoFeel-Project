package com.purabmodi.exoplayer.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.purabmodi.exoplayer.apiTools.videoRepository

class VideosViewModelFactory(private val repository: videoRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return VideoViewModel(repository) as T
    }
}