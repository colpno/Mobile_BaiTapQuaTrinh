package com.example.easytutofilemanager;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class PlaymusicActivity extends AppCompatActivity {

    private ImageView albumArtImageView;
    private ImageButton previousButton, playButton, nextButton;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler;
    private int currentSongIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        albumArtImageView = findViewById(R.id.albumArtImageView);
        previousButton = findViewById(R.id.previousButton);
        playButton = findViewById(R.id.playButton);
        nextButton = findViewById(R.id.nextButton);
        seekBar = findViewById(R.id.seekBar);

        handler = new Handler();
        Intent intent = getIntent();


        String filePath = intent.getStringExtra("FILE_PATH");
        File musicFolder = new File(filePath);

        // Lọc các tệp có đuôi mở rộng là ".mp3"
        File[] files = musicFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".mp3");
            }
        });
        Log.d("Testnha","Test lay lít file music  xem sao"+musicFolder);

        if (files != null && files.length > 0) {
            // Lấy tệp âm thanh đầu tiên từ thư mục
            File firstMusicFile = files[0];
            Log.d("Testnha","Test lay file dau tien "+firstMusicFile.toString());
            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(firstMusicFile.getPath());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playButton.setImageResource(R.drawable.play);
                    } else {
                        mediaPlayer.start();
                        playButton.setImageResource(R.drawable.pause);
                        updateSeekBar();
                    }
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPreviousSong();

            }
            private void playPreviousSong() {
                if (mediaPlayer != null) {
                    if (currentSongIndex > 0) {
                        // Nếu không phải là bài hát đầu tiên
                        currentSongIndex--;
                    } else {
                        // Nếu đang ở bài hát đầu tiên, chuyển về bài hát cuối cùng
                        currentSongIndex = files.length - 1;
                    }

                    File previousMusicFile = files[currentSongIndex];
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(previousMusicFile.getPath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        playButton.setImageResource(R.drawable.pause); // Đặt icon pause nếu bắt đầu phát
                        updateSeekBar();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý chuyển bài tiếp theo ở đây
                playNextSong();

            }
            private void playNextSong() {
                if (mediaPlayer != null) {
                    if (currentSongIndex < files.length - 1) {
                        // Nếu chưa phải là bài hát cuối cùng
                        currentSongIndex++;
                    } else {
                        // Nếu đang ở bài hát cuối cùng, chuyển về bài hát đầu tiên
                        currentSongIndex = 0;
                    }

                    File nextMusicFile = files[currentSongIndex];
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(nextMusicFile.getPath());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        playButton.setImageResource(R.drawable.pause); // Đặt icon pause nếu bắt đầu phát
                        updateSeekBar();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Không cần xử lý
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Không cần xử lý
            }
        });
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    updateSeekBar();
                }
            }, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            handler.removeCallbacksAndMessages(null);
        }
    }
}
