package com.leielyq.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class MResult implements Parcelable {
    private String max;
    private String min;
    private String result;

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.max);
        dest.writeString(this.min);
        dest.writeString(this.result);
    }

    public MResult() {
    }

    protected MResult(Parcel in) {
        this.max = in.readString();
        this.min = in.readString();
        this.result = in.readString();
    }

    public static final Parcelable.Creator<MResult> CREATOR = new Parcelable.Creator<MResult>() {
        @Override
        public MResult createFromParcel(Parcel source) {
            return new MResult(source);
        }

        @Override
        public MResult[] newArray(int size) {
            return new MResult[size];
        }
    };
}
