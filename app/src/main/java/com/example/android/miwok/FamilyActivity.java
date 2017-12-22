package com.example.android.miwok;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
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
        words.add(new Word("Father", "Pita ji", R.drawable.family_father, R.raw.father));
        words.add(new Word("Mother", "Mata ji", R.drawable.family_mother, R.raw.fmother));
        words.add(new Word("Son", "Beta", R.drawable.family_son, R.raw.fson));
        words.add(new Word("Daughter", "Beti", R.drawable.family_daughter, R.raw.fdaughter));
        words.add(new Word("Older Brother", "Bada Bhai", R.drawable.family_older_brother, R.raw.felderbro));
        words.add(new Word("Younger Brother", "Chota Bhai", R.drawable.family_younger_brother, R.raw.fyoungerbri));
        words.add(new Word("Older Sister", "Badi Behen", R.drawable.family_older_sister, R.raw.feldersis));
        words.add(new Word("Younger Sister", "Choti Behen", R.drawable.family_younger_sister, R.raw.fyoungersis));
        words.add(new Word("Grandmother", "Dadi Ji", R.drawable.family_grandmother, R.raw.fdadiji));
        words.add(new Word("GrandFather", "Dada Ji", R.drawable.family_grandfather, R.raw.fdadaji));
        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_family);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                releaseMediaPlayer();
                int result= mAudioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Word currentWord = words.get(position);
                    int audioId = currentWord.getAudioId();
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, audioId);
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
