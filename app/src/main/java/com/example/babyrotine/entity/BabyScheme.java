package com.example.babyrotine.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "babyScheme")
public class BabyScheme implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "img")
    private int image;
    @ColumnInfo(name = "activity")
    private String activity;
    @ColumnInfo(name = "hour")
    private String hour;

    public BabyScheme(){

    }

    public BabyScheme(int image, String activity){
        this.activity = activity;
        this.image = image;
        DateFormat dateFormat = new SimpleDateFormat("HH:MM:ss", Locale.ENGLISH);
        Date date = new Date();
        this.hour = dateFormat.format(date);
    }
    public BabyScheme(long id, String activity){
        this.id = id;
        this.activity = activity;
    }

    public BabyScheme(String activity){
        this.activity = activity;
    }

    protected BabyScheme(Parcel in) {
        id = in.readLong();
        activity = in.readString();
        hour = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour){
        this.hour = hour;
    }

    public String makeHour() {
        DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.MEDIUM);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(image);
        dest.writeString(activity);
        dest.writeString(hour);
    }

    public static final Creator<BabyScheme> CREATOR = new Creator<BabyScheme>() {
        @Override
        public BabyScheme createFromParcel(Parcel in) {
            BabyScheme b = new BabyScheme();
            b.setId(in.readLong());
            b.setImage(in.readInt());
            b.setActivity(in.readString());
            b.setHour(in.readString());
            return b;
        }

        @Override
        public BabyScheme[] newArray(int size) {
            return new BabyScheme[size];
        }
    };
}
