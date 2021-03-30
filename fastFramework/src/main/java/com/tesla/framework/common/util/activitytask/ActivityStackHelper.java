package com.tesla.framework.common.util.activitytask;

import android.app.Activity;
import android.os.Looper;
import android.os.MessageQueue;

import com.tesla.framework.common.util.log.NLog;

import java.io.IOException;
import java.io.Writer;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Devil on 2015/5/5.
 * modified by devilxie on 2016/5/26 - add idle handler
 */
public class ActivityStackHelper {
    public static final String TAG = ActivityStackHelper.class.getSimpleName();

    public static class ActivityReference extends WeakReference<Activity>
    {
        public ActivityReference(Activity r, ReferenceQueue<? super Activity> q)
        {
            super(r, q);
        }
    }

    /** 垃圾Reference的队列（所引用的对象已经被回收，则将该引用存入队列中） */
    static ReferenceQueue<Activity> sReferenceQueue = null;
    static LinkedList<ActivityReference> sStack = new LinkedList<>();

    public static ActivityReference push(Activity activity) {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");
        NLog.d(TAG, "push activity: %s to stack",activity.getClass().getSimpleName());

        ActivityReference ref = new ActivityReference(activity, getReferenceQueue());
        sStack.push(ref);
        printActivity();
        return ref;
    }

    private static ReferenceQueue<Activity> getReferenceQueue() {
        if (sReferenceQueue == null) {
            sReferenceQueue = new ReferenceQueue<>();
            MessageQueue queue = Looper.myQueue();
            queue.addIdleHandler(new RefQueueIdleHandler(sStack, sReferenceQueue));
        }

        return sReferenceQueue;
    }

    public static void remove(ActivityReference ref) {
        if (ref == null)
            throw new IllegalArgumentException("ActivityReference ref is null");

        sStack.remove(ref);
    }

    public static void remove(Activity activity) {
        if (activity == null)
            throw new IllegalArgumentException("activity is null");
        NLog.d(TAG, "remove activity: %s from stack",activity.getClass().getSimpleName());
        Iterator<ActivityReference> it = sStack.iterator();
        while (it.hasNext()) {
            ActivityReference ref = it.next();
            if (ref != null && ref.get() == activity) {
                it.remove();
                break;
            }
        }
        printActivity();

    }

    private static void clean()
    {
        if (sReferenceQueue == null)
            return;

        ActivityReference ref = null;
        while ((ref = (ActivityReference) sReferenceQueue.poll()) != null)
        {
            sStack.remove(ref);
        }
    }

    public static int sizeOfStack() {
        int count = 0;
        Iterator<ActivityReference> it = sStack.iterator();
        while (it.hasNext()) {
            ActivityReference ref = it.next();
            if (ref != null && ref.get() != null) {
                count++;
            }
        }

        return count;
    }

    public static int sizeOfTask(int taskId) {
        int count = 0;
        Iterator<ActivityReference> it = sStack.iterator();
        while (it.hasNext()) {
            ActivityReference ref = it.next();
            if (ref != null && ref.get() != null) {
                Activity activity = ref.get();
                if (activity.getTaskId() == taskId)
                    count++;
            }
        }

        return count;
    }

    public static void finishActivityStack() {
        clean();
        Iterator<ActivityReference> it = sStack.iterator();
        while (it.hasNext()) {
            ActivityReference ref = it.next();
            if (ref != null && ref.get() != null) {
                Activity activity = ref.get();
                if (!activity.isFinishing())
                    activity.finish();
            }
        }

        sStack.clear();
    }

    public static Activity topActivity() {
        Activity activity = null;
        ActivityReference ref = null;

        do {

            ref = sStack.peek();
            if (ref == null)
                break;
            if ( ref.get() != null) {
                activity = ref.get();
                break;
            } else {
                sStack.pop();
            }

        } while(true);

        return activity;
    }

    public static void recreateActivityStackExcept(Activity a) {
        clean();
        Iterator<ActivityReference> it = sStack.iterator();
        while (it.hasNext()) {
            ActivityReference ref = it.next();
            if (ref != null && ref.get() != null) {
                Activity activity = ref.get();
                if (activity == a)
                    continue;

                if (!activity.isFinishing())
                    activity.recreate();
            }
        }
    }

    public static void finishActivityStackExcept(Activity a) {
        NLog.d(TAG, "finishActivityStackExcept a: %s",a);
        clean();
        Iterator<ActivityReference> it = sStack.iterator();
        while (it.hasNext()) {
            ActivityReference ref = it.next();
            if (ref != null && ref.get() != null) {
                Activity activity = ref.get();
                if (activity == a)
                    continue;

                if (!activity.isFinishing())
                    activity.finish();
            }
        }
    }

    public static void finishActivityStackExcept(List<Class<? extends Activity>> clzList) {
        clean();
        Iterator<ActivityReference> it = sStack.iterator();
        while (it.hasNext()) {
            ActivityReference ref = it.next();
            if (ref != null && ref.get() != null) {
                Activity activity = ref.get();
                Class<? extends Activity> clz = activity.getClass();
                if (clzList.contains(clz))
                    continue;

                if (!activity.isFinishing())
                    activity.finish();
            }
        }

    }

    public static void printStackToStream(Writer writer) throws IOException {
        writer.append("[########################### activity stack begin ##################################]\n");
        Iterator<ActivityReference> it = sStack.iterator();
        while (it.hasNext()) {
            ActivityReference ref = it.next();
            if (ref != null && ref.get() != null) {
                Activity activity = ref.get();
                writer.append("   ").append(activity.getClass().getCanonicalName())
                        .append("(task=")
                        .append(String.valueOf(activity.getTaskId())).append(")\n");
            }
        }
        writer.append("[########################### activity stack end   ##################################]\n");
    }

    private static class RefQueueIdleHandler implements MessageQueue.IdleHandler {

        private final ReferenceQueue<Activity> refQueue;
        private final LinkedList<ActivityReference> stack;

        public RefQueueIdleHandler(LinkedList<ActivityReference> stack,
                                   ReferenceQueue<Activity> queue) {
            this.stack = stack;
            this.refQueue = queue;
        }

        @Override
        public boolean queueIdle() {

            ActivityReference ref = (ActivityReference) refQueue.poll();
            if (ref != null) {
                stack.remove(ref);
            }
            return true;
        }
    }

    private static void printActivity(){
        if (sStack == null){
            return;
        }
        StringBuilder builder = new StringBuilder();
        builder.append("activity in stack:");
        for (WeakReference<Activity> ref : sStack) {
            if (ref != null && ref.get() != null && !ref.get().isFinishing()) {
                Activity activity1 = ref.get();
                builder.append(activity1.getClass().getSimpleName()).append(",");
            }
        }
        builder.append(String.format(" size  : %s", sStack.size()));
        NLog.d(TAG, builder.toString());
    }

}