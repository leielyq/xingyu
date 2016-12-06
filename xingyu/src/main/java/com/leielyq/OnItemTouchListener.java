package com.leielyq;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2016/8/26.
 */
public abstract class OnItemTouchListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat mgestureDetectorCompat;
    private RecyclerView mrecyclerView;

    public OnItemTouchListener(RecyclerView recyclerView) {
        mrecyclerView = recyclerView;
        mgestureDetectorCompat = new GestureDetectorCompat(mrecyclerView.getContext(),
                new MyGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mgestureDetectorCompat.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mgestureDetectorCompat.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public abstract void onItemClick(RecyclerView.ViewHolder vh,int position);
    public abstract void onItemLongClick(RecyclerView.ViewHolder vh,int position);

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childe = mrecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childe != null) {
                RecyclerView.ViewHolder VH = mrecyclerView.getChildViewHolder(childe);
                onItemClick(VH,VH.getLayoutPosition());
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View childe = mrecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childe != null) {
                RecyclerView.ViewHolder VH = mrecyclerView.getChildViewHolder(childe);
                onItemLongClick(VH,VH.getLayoutPosition());
            }
        }
    }
}
