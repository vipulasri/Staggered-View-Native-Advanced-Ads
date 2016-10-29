package com.vipulasri.staggeredviewnativeads;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP-HP on 06-10-2016.
 */

public class Artwork implements Parcelable {

    private String title;
    private String webImageUrl;
    private long webImageWidth;
    private long webImageHeight;

    public Artwork(String title, String webImageUrl, long webImageWidth, long webImageHeight){
        this.title = title;
        this.webImageUrl = webImageUrl;
        this.webImageWidth = webImageWidth;
        this.webImageHeight = webImageHeight;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailImage() {
        return webImageUrl.replace("=s0","=s400");
    }

    public float getAspectRatio() {
        return (float)(Math.floor(((double) webImageWidth/webImageHeight) * 1000 +.5)/1000);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.webImageUrl);
        dest.writeLong(this.webImageWidth);
        dest.writeLong(this.webImageHeight);
    }

    protected Artwork(Parcel in) {
        this.title = in.readString();
        this.webImageUrl = in.readString();
        this.webImageWidth = in.readLong();
        this.webImageHeight = in.readLong();
    }

    public static final Parcelable.Creator<Artwork> CREATOR = new Parcelable.Creator<Artwork>() {
        @Override
        public Artwork createFromParcel(Parcel source) {
            return new Artwork(source);
        }

        @Override
        public Artwork[] newArray(int size) {
            return new Artwork[size];
        }
    };
}
