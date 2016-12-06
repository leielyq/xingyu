package com.leielyq.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class Item implements Parcelable {
    private List<ItemChild> itemchildrens;
    private File loactimg;
    private BmobFile img;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemChild> getItemchildrens() {
        return itemchildrens;
    }

    public void setItemchildrens(List<ItemChild> itemchildrens) {
        this.itemchildrens = itemchildrens;
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

    public Item() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.itemchildrens);
        dest.writeSerializable(this.loactimg);
        dest.writeSerializable(this.img);
        dest.writeString(this.title);
    }

    protected Item(Parcel in) {
        this.itemchildrens = in.createTypedArrayList(ItemChild.CREATOR);
        this.loactimg = (File) in.readSerializable();
        this.img = (BmobFile) in.readSerializable();
        this.title = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}
