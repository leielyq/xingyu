package com.leielyq.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class ItemChild implements Parcelable {
    private String select;
    private Integer socre;

    public String getSelect() {
        return select;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public Integer getSocre() {
        return socre;
    }

    public void setSocre(Integer socre) {
        this.socre = socre;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.select);
        dest.writeValue(this.socre);
    }

    public ItemChild() {
    }

    protected ItemChild(Parcel in) {
        this.select = in.readString();
        this.socre = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ItemChild> CREATOR = new Parcelable.Creator<ItemChild>() {
        @Override
        public ItemChild createFromParcel(Parcel source) {
            return new ItemChild(source);
        }

        @Override
        public ItemChild[] newArray(int size) {
            return new ItemChild[size];
        }
    };
}
