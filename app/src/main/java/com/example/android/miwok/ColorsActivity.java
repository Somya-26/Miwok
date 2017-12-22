package com.example.android.miwok;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class ColorsActivity extends AppCompatActivity {
   private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener listener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener  onAudioFocusChangeListener =new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                mMediaPlayer.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
                releaseMediaPlayer();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager=(AudioManager)getSystemService(this.AUDIO_SERVICE);
       final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("Red","Laal",R.drawable.color_red,R.raw.cred));
        words.add(new Word("Green","Hara",R.drawable.color_green,R.raw.cgreen));
        words.add(new Word("Black","Kala",R.drawable.color_black,R.raw.cblack));
        words.add(new Word("White","Shwet",R.drawable.color_white,R.raw.cwhite));
        words.add(new Word("Dusty Yellow","Halka Peela",R.drawable.color_dusty_yellow,R.raw.cdyello));
        words.add(new Word("Yellow","Peela",R.drawable.color_mustard_yellow,R.raw.cyellow));
        words.add(new Word("Brown","Bhoora",R.drawable.color_brown,R.raw.cbrown));
        words.add(new Word("Gray","Gray",R.drawable.color_gray,R.raw.cgray));

        WordAdapter itemsAdapter = new WordAdapter(this, words,R.color.category_colors);

        ListView listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position,
                                     long id) {
                 releaseMediaPlayer();

                int result= mAudioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                 if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                     Word currentWord=words.get(position);
                     int audioId= currentWord.getAudioId();
                     mMediaPlayer=MediaPlayer.create(ColorsActivity.this,audioId);
                     mMediaPlayer.start();
                     mMediaPlayer.setOnCompletionListener(listener);
                 }
             }
         });

    }
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();
            mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

        }
    }
}
