package com.example.android.bakingapp.Data;


import android.os.Parcel;
import android.os.Parcelable;

public class Steps implements Parcelable {
    private String id;

    private String shortDescription;

    private String description;

    private String videoURL;

    private String thumbnailURL;


    public Steps() {
    }

    private Steps(Parcel parcel) {
        id = parcel.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);

    }

    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>(){
        @Override
        public Steps createFromParcel(Parcel parcel) {
            return new Steps(parcel);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[0];
        }
    };

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getShortDescription ()
    {
        return shortDescription;
    }

    public void setShortDescription (String shortDescription)
    {
        this.shortDescription = shortDescription;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getVideoURL ()
    {
        return videoURL;
    }

    public void setVideoURL (String videoURL)
    {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL ()
    {
        return thumbnailURL;
    }

    public void setThumbnailURL (String thumbnailURL)
    {
        this.thumbnailURL = thumbnailURL;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", shortDescription = "+shortDescription+", description = "+description+", videoURL = "+videoURL+", thumbnailURL = "+thumbnailURL+"]";
    }


}
