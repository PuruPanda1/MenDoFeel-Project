package com.purabmodi.exoplayer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.purabmodi.exoplayer.R
import com.purabmodi.exoplayer.apiTools.dataclass.Hit

class ExoPlayerAdapter() : ListAdapter<Hit, ExoPlayerAdapter.viewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.exoplayer_layout, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onViewDetachedFromWindow(holder: viewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.stopVideo()
    }

    override fun onViewAttachedToWindow(holder: viewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.startVideo()
    }

    inner class viewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val videoPlayer: StyledPlayerView = view.findViewById(R.id.exoPlayerView)
        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val tagView: TextView = view.findViewById(R.id.tags)
        val videoViews: TextView = view.findViewById(R.id.views)
        val player = ExoPlayer.Builder(videoPlayer.context).build()

        fun bind(video: Hit) {
            videoPlayer.player = player
            tagView.text = "Tags: ${video.tags}"
            videoViews.text = "Views: ${video.views}"
            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    when (playbackState) {
                        Player.STATE_READY -> {
                            progressBar.isVisible = false
                        }
                        Player.STATE_BUFFERING -> {
                            progressBar.isVisible = true
                        }
                        Player.STATE_ENDED -> {
                            progressBar.isVisible = true
                            player.seekTo(0)
                        }
                        Player.STATE_IDLE -> {
                            progressBar.isVisible = false
                        }
                    }
                }
            })
            videoPlayer.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            val uri = video.videos.tiny.url
            val mediaItem = MediaItem.fromUri(uri)
            player.setMediaItem(mediaItem)
        }

        fun startVideo() {
            player.prepare()
            player.play()
        }

        fun stopVideo() {
            player.seekTo(0)
            player.stop()
            player.playbackState
        }

    }

    class Comparator : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean {
            return oldItem == newItem
        }

    }
}

