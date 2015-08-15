package com.wpadvert.kernel;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Timer;

import android.os.Message;

import com.andframe.helper.java.AfTimeSpan;
import com.andframe.thread.AfHandlerTimerTask;
import com.andframe.thread.AfTimerTask;

/**
 * Created by SCWANG on 2015-07-11.
 */
public class PointTimer {

    /** 定时器周期10秒钟 */
    protected static final long KEY_PERIOD = 60000;
    /** 最后一次启动时间 */
    protected static Date mLastStarttime = new Date();
    /** 监听时间的长度（一个小时关闭监听）*/
    protected static AfTimeSpan mSpan = AfTimeSpan.FromHours(1);
    /** 当前在执行的任务 */
    protected static TimeTask mTask = null;

    protected static Timer mTimer = new Timer();

    protected static Set<AfTimerTask> mltTimeTasks = new LinkedHashSet<AfTimerTask>();

    protected static class TimeTask extends AfHandlerTimerTask {
        @Override
        protected boolean onHandleTimer(Message msg) {
            for (AfTimerTask task :	mltTimeTasks) {
                task.run();
            }
            if (AfTimeSpan.FromDate(mLastStarttime, new Date()).GreaterThan(mSpan)) {
                doStopTimer();
            }
            return true;
        }

        private void doStopTimer() {
            if (mTask != null){
                mTask.cancel();
                mTask = null;
            }
        }
    }

    protected static void doStartTimer(AfTimerTask task){
        mltTimeTasks.add(task);
        mLastStarttime = new Date();
        if (mTask == null){
            mTask = new TimeTask();
            mTimer.schedule(mTask,KEY_PERIOD,KEY_PERIOD);
        }
    }

    public static void resetTime() {
        mLastStarttime = new Date();
    }
}
