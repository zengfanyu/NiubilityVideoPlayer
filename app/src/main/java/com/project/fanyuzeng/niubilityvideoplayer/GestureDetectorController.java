package com.project.fanyuzeng.niubilityvideoplayer;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by fanyuzeng on 2017/10/13.
 * Function:
 */

public class GestureDetectorController implements GestureDetector.OnGestureListener {
    private static final String TAG = "GestureDetectorCtro";
    public static final int SCROLL_NOTHING = 0x1000;
    public static final int SCROLL_VERTICAL_LEFT = 0x1001;
    public static final int SCROLL_VERTICAL_RIGHT = 0x1002;
    public static final int SCROLL_HORIZONTAL = 0x1003;

    private GestureDetector mGestureDetector;
    private IGestureListener mGestureListener;
    private int mWidth;
    private int mCurrentType;

    public GestureDetectorController(Context context, IGestureListener listener) {
        mWidth = context.getResources().getDisplayMetrics().widthPixels;
        mGestureListener = listener;
        mGestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        mCurrentType = SCROLL_NOTHING;
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, ">> onScroll >> " + "mGestureListener:" + mGestureListener + ",mCurrentType:" + Integer.toHexString(mCurrentType));
        if (mGestureListener != null) {
            if (mCurrentType != SCROLL_NOTHING) {
                switch (mCurrentType) {
                    case SCROLL_VERTICAL_LEFT:
                        mGestureListener.onScrollVerticalLeft(distanceY, e1.getY() - e2.getY());
                        break;
                    case SCROLL_VERTICAL_RIGHT:
                        mGestureListener.onScrollVerticalRight(distanceY, e1.getY() - e2.getY());
                        break;
                    case SCROLL_HORIZONTAL:
                        mGestureListener.onScrollHorizontal(distanceX, e2.getX() - e1.getX());
                        break;
                    default:
                        break;
                }
                return false;
            }
        }
        //mCurrentType == Nothing

        if (Math.abs(distanceY) <= Math.abs(distanceX)) {
            //横向滑动
            mCurrentType = SCROLL_HORIZONTAL;
            mGestureListener.onScrollStart(SCROLL_HORIZONTAL);
            return false;
        }
        //竖向滑动
        int i = mWidth / 3;
        if (e1.getX() <= i) {
            //竖向屏幕左侧滑动
            mCurrentType = SCROLL_VERTICAL_LEFT;
            mGestureListener.onScrollStart(mCurrentType);
        } else if (e1.getX() >= 2 * i) {
            //竖向屏幕右侧滑动
            mCurrentType = SCROLL_VERTICAL_RIGHT;
            mGestureListener.onScrollStart(mCurrentType);
        } else {
            //竖向屏幕中间互动
            mGestureListener.onScrollStart(SCROLL_NOTHING);
        }

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public interface IGestureListener {
        void onScrollStart(int scrollType);

        void onScrollHorizontal(float x1, float x2);

        void onScrollVerticalLeft(float y1, float y2);

        void onScrollVerticalRight(float y1, float y2);
    }
}
