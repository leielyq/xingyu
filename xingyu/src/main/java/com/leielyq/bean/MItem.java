package com.leielyq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;


/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class MItem implements Parcelable {
    private String title;
    private String q1;
    private String q2;
    private String q3;
    private String q4;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQ1() {
        return q1;
    }

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public String getQ2() {
        return q2;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public String getQ3() {
        return q3;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public String getQ4() {
        return q4;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    public String getN1() {
        return n1;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public String getN3() {
        return n3;
    }

    public void setN3(String n3) {
        this.n3 = n3;
    }

    public String getN4() {
        return n4;
    }

    public void setN4(String n4) {
        this.n4 = n4;
    }

    public File getLoactimg() {
        return loactimg;
    }

    public void setLoactimg(File loactimg) {
        this.loactimg = loactimg;
    }

    public BmobFile getImg() {
        return img;
    }

    public void setImg(BmobFile img) {
        this.img = img;
    }

    private String n1;
    private String n2;
    private String n3;
    private String n4;
    private File loactimg;
    private BmobFile img;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.q1);
        dest.writeString(this.q2);
        dest.writeString(this.q3);
        dest.writeString(this.q4);
        dest.writeString(this.n1);
        dest.writeString(this.n2);
        dest.writeString(this.n3);
        dest.writeString(this.n4);
        dest.writeSerializable(this.loactimg);
        dest.writeSerializable(this.img);
    }

    public MItem() {
    }

    protected MItem(Parcel in) {
        this.title = in.readString();
        this.q1 = in.readString();
        this.q2 = in.readString();
        this.q3 = in.readString();
        this.q4 = in.readString();
        this.n1 = in.readString();
        this.n2 = in.readString();
        this.n3 = in.readString();
        this.n4 = in.readString();
        this.loactimg = (File) in.readSerializable();
        this.img = (BmobFile) in.readSerializable();
    }

    public static final Creator<MItem> CREATOR = new Creator<MItem>() {
        @Override
        public MItem createFromParcel(Parcel source) {
            return new MItem(source);
        }

        @Override
        public MItem[] newArray(int size) {
            return new MItem[size];
        }
    };
}
