package com.leielyq.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class Result implements Parcelable {
    private Integer min;
    private Integer max;
    private String result;

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
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
        dest.writeValue(this.min);
        dest.writeValue(this.max);
        dest.writeString(this.result);
    }

    public Result() {
    }

    protected Result(Parcel in) {
        this.min = (Integer) in.readValue(Integer.class.getClassLoader());
        this.max = (Integer) in.readValue(Integer.class.getClassLoader());
        this.result = in.readString();
    }

    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
