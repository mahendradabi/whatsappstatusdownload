package com.mahi.whatsappstatus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WhtaspAdatper extends RecyclerView.Adapter<WhtaspAdatper.MyViewHolder> {
    ArrayList<File> files;
    Context mContex;

    public WhtaspAdatper(Context mContex, ArrayList<File> files) {
        this.mContex = mContex;
        this.files = files;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_media_row_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final  MyViewHolder holder, int i) {
        File currentFile = files.get(i);

        if (currentFile.getAbsolutePath().endsWith(".mp4")) {
            //    holder.cardViewImageMedia.setVisibility(View.GONE);
            //    holder.cardViewVideoMedia.setVisibility(View.VISIBLE);
            Uri video = Uri.parse(currentFile.getPath().toString());
            holder.videoViewVideoMedia.setVideoURI(video);
            holder.videoViewVideoMedia.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                    holder.videoViewVideoMedia.start();
                }
            });
        } else {
            Bitmap bitmap= BitmapFactory.decodeFile(currentFile.getPath().toString());
            holder.imageViewImageMedia.setImageBitmap(bitmap);
        }


    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewImageMedia;
        VideoView videoViewVideoMedia;
        CardView cardViewVideoMedia;
        CardView cardViewImageMedia;
        Button buttonVideoDownload;
        Button buttonImageDownload;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImageMedia = (ImageView) itemView.findViewById(R.id.imageViewImageMedia);
            videoViewVideoMedia = (VideoView) itemView.findViewById(R.id.videoViewVideoMedia);
            cardViewVideoMedia = (CardView) itemView.findViewById(R.id.cardViewVideoMedia);
            cardViewImageMedia = (CardView) itemView.findViewById(R.id.cardViewImageMedia);
            buttonImageDownload = (Button) itemView.findViewById(R.id.buttonImageDownload);
            buttonVideoDownload = (Button) itemView.findViewById(R.id.buttonVideoDownload);


        }
    }
}
