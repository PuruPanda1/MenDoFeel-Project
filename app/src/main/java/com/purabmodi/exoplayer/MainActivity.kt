package com.purabmodi.exoplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.Player
import com.purabmodi.exoplayer.adapter.ExoPlayerAdapter
import com.purabmodi.exoplayer.apiTools.videoRepository
import com.purabmodi.exoplayer.databinding.ActivityMainBinding
import com.purabmodi.exoplayer.viewModels.VideoViewModel
import com.purabmodi.exoplayer.viewModels.VideosViewModelFactory

class MainActivity : AppCompatActivity(), Player.Listener {
    private lateinit var videoViewModel: VideoViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val repository = videoRepository()
        val viewModelFactory = VideosViewModelFactory(repository)
        videoViewModel = ViewModelProvider(this, viewModelFactory)[VideoViewModel::class.java]

        val options: HashMap<String, String> = HashMap()
        options["key"] = "24898492-8f784013c6375fa9763943a7e"
        options["q"] = "yellow+flowers"
        videoViewModel.getVideos(options)

        val adapter = ExoPlayerAdapter()
        binding.viewPager.adapter = adapter


        videoViewModel.videoList.observe(this) { response ->
            if (response.isSuccessful && response.body() != null) {
                adapter.submitList(response.body()!!.hits)
            }
        }
    }

//    private fun setupPlayer() {
//        val player = ExoPlayer.Builder(this).build()
//        binding.exoPlayerView.player = player
//        binding.exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
//        player.addListener(this)
//        val uri =
//            "https://cdn.pixabay.com/vimeo/328940142/Buttercups%20-%2022634.mp4?width=1956&hash=bb8d9d08ac8d9b82901668382fa6a63568b374b1"
//        val mediaItem = MediaItem.fromUri(uri)
//        player.setMediaItem(mediaItem)
//        player.prepare()
//        player.play()
//    }
}