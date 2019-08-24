package me.mahakagg.simplevideoviewhw;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// adapter and ViewHolder for the recyclerView
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private ArrayList<VideoInfo> videoInfoArrayList;

    // constructor that passes in context and VideoInfoArrayList
    VideoAdapter(Context context, ArrayList<VideoInfo> videoInfoArrayList) {
        this.context = context;
        this.videoInfoArrayList = videoInfoArrayList;
    }

    // mandatory method to create viewHolder objects
    @NonNull
    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }

    // mandatory method to bind data to viewHolder
    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.VideoViewHolder holder, int position) {
        // get current video and bind it to holder
        VideoInfo currentInfo = videoInfoArrayList.get(position);
        holder.bindTo(currentInfo);
    }

    // size of arrayList
    @Override
    public int getItemCount() {
        return videoInfoArrayList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // member variables
        private VideoView mVideoView;
        private TextView titleTextView;
        private boolean isVideoPrepared = false;

        VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            // initialize the views
            mVideoView = itemView.findViewById(R.id.videoView);
            titleTextView = itemView.findViewById(R.id.videoTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (isVideoPrepared) {
//                VideoInfo info = videoInfoArrayList.get(getAdapterPosition());
                mVideoView.start();
            }
            else{
                Toast.makeText(context, "Video is loading", Toast.LENGTH_LONG).show();
            }
        }

        private void bindTo(VideoInfo videoInfo){
            titleTextView.setText(videoInfo.getTitle());
            // setup media controller
            MediaController mediaController = new MediaController(context);
            mediaController.setMediaPlayer(mVideoView);
            mVideoView.setMediaController(mediaController);

            // url setup
            Uri videoUri = Uri.parse(videoInfo.getUrl());
            mVideoView.setVideoURI(videoUri);
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    isVideoPrepared = true;
                }
            });

        }
    }
}
