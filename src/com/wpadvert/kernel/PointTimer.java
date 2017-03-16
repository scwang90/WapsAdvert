package com.wpadvert.kernel;

import android.os.Handler;
import android.os.Looper;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * Created by SCWANG on 2015-07-11.
 */
public class PointTimer {

    /** 定时器周期10秒钟 */
    protected static final long KEY_PERIOD = 60000;
    /** 最后一次启动时间 */
    protected static Date mLastStarttime = new Date();
    /*** 监听时间的长度（一个小时关闭监听） */
    protected static int mSpan = 60 * 60 * 1000;
    /** 当前在执行的任务 */
    protected static TimeTask mTask = null;

    protected static Set<Runnable> mltTimeTasks = new LinkedHashSet<>();

    protected static class TimeTask implements Runnable {

        Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void run() {
            try {
                for (Runnable task : mltTimeTasks) {
                    task.run();
                }
                if (System.currentTimeMillis() - mLastStarttime.getTime() < mSpan) {
                    handler.postDelayed(this, KEY_PERIOD);
                } else {
                    handler = null;
                    mTask = null;
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    protected static void doStartTimer(Runnable task){
        mltTimeTasks.add(task);
        mLastStarttime = new Date();
        if (mTask == null){
            mTask = new TimeTask();
        }
    }

    public static void resetTime() {
        mLastStarttime = new Date();
    }
}
