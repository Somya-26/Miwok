package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
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
        words.add(new Word("Where are you going?", "Tum kahan ja rahe ho?", R.raw.phraseone));
        words.add(new Word("What is your name?", "Tumhara naam kya hai?", R.raw.phrasetwo));
        words.add(new Word("My name is..", "Mera naam hai..", R.raw.phrasethree));
        words.add(new Word("How are you feeling?", "Tum kaisa mehsoos kr rhe ho?", R.raw.phrasefour));
        words.add(new Word("I'm feeling good.", "Mein acha mehsoos kr rha hoon.", R.raw.phrasefive));
        words.add(new Word("Are you coming?", "Kya tum aa rhe ho?", R.raw.phrasesix));
        words.add(new Word("Yes, I'm coming.", "Han , mein aa rha hoon.", R.raw.phraseseven));
        words.add(new Word("I'm coming.", "Mein aa rha hoon.", R.raw.phraseeight));
        words.add(new Word("Let's go.", "Chlo , chlte hain.", R.raw.phrasenine));
        words.add(new Word("Come here.", "Idhar aao.", R.raw.phraseten));
        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Word currentWord = words.get(position);
                    int audioId = currentWord.getAudioId();
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, audioId);
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
