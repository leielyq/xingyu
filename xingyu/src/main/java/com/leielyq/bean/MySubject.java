package com.leielyq.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2016/8/21.
 */
public class MySubject extends BmobObject {
    private String mtitle;
    private String mcontext;
    private List<MItem> mitem;
    private List<MResult> mresult;
    private String sender;
    private boolean recommend = false;
    private Integer num;
    private Integer rank = 1;
    private List<BmobFile> mimgs;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public String getMtitle() {
        return mtitle;
    }

    public void setMtitle(String mtitle) {
        this.mtitle = mtitle;
    }

    public String getMcontext() {
        return mcontext;
    }

    public void setMcontext(String mcontext) {
        this.mcontext = mcontext;
    }

    public List<MItem> getMitem() {
        return mitem;
    }

    public void setMitem(List<MItem> mitem) {
        this.mitem = mitem;
    }

    public List<MResult> getMresult() {
        return mresult;
    }

    public void setMresult(List<MResult> mresult) {
        this.mresult = mresult;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<BmobFile> getMimgs() {
        return mimgs;
    }

    public void setMimgs(List<BmobFile> mimgs) {
        this.mimgs = mimgs;
    }
}
