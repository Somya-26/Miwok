package com.example.android.miwok;

public class Word {
    private String mDefaultTranslation;
    private String mHindiTranslation;
     private  static final int notVisible=-1;
    private int mAudioId;;
    private int  mImageResourceId;
    public  Word(String defaultTranslation,String hindiTranslation,int audioId)
    {
        mDefaultTranslation=defaultTranslation;
        mHindiTranslation=hindiTranslation;
        mImageResourceId=notVisible;
        mAudioId=audioId;
    }



    public Word(String defaultTranslation,String hindiTranslation,int imageResourceId,int audioId)
    {
        mDefaultTranslation=defaultTranslation;
        mHindiTranslation=hindiTranslation;
        mImageResourceId=imageResourceId;
        mAudioId=audioId;
    }
    public String getDefaultTranslation()
    {
        return  mDefaultTranslation;
    }
    public String getHindiTranslation()
    {
        return mHindiTranslation;
    }
    public int getImageId(){return mImageResourceId;}
    public int getAudioId(){return mAudioId;}
    public boolean checkImage()
    {
        if(mImageResourceId==notVisible)
            return false;
        return true;
    }

} 