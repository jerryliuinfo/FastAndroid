package com.coco.aspectjlib;//package com.coco.aspectj;
//
//import android.os.SystemClock;
//import android.util.Log;
//import android.view.View;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import java.lang.reflect.Method;
//
//@Aspect
//public class SingleClickAspect {
//    private static final String TAG = "Animal";
//    // normal onClick
//    private static final String ON_CLICK_POINTCUTS = "execution(* android.view.View.OnClickListener.onClick(..))";
//    // 如果 onClick 是写在 xml 里面的
//    private static final String ON_CLICK_IN_XML_POINTCUTS = "execution(* android.app.Activity.on**(..))";
//    //    // butterknife on click
//    private static final String ON_CLICK_IN_BUTTER_KNIFE_POINTCUTS = "execution(@butterknife.OnClick * *(..))";
//    // view tag unique key, must be one of resource id
//    private static final int SINGLE_CLICK_KEY = R.string.app_name;
//
//    @Pointcut(ON_CLICK_POINTCUTS)
//    public void onClickPointcuts() {
//    }
//
//    @Pointcut(ON_CLICK_IN_XML_POINTCUTS)
//    public void onClickInXmlPointcuts() {
//    }
//
//    @Pointcut(ON_CLICK_IN_BUTTER_KNIFE_POINTCUTS)
//    public void onClickInButterKnifePointcuts() {
//    }
//
//    @Around("onClickPointcuts() || onClickInXmlPointcuts() || onClickInButterKnifePointcuts()")
//    public void throttleClick(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            // check for Except annotation
//            Signature signature = joinPoint.getSignature();
//            if (signature instanceof MethodSignature) {
//                MethodSignature methodSignature = (MethodSignature) signature;
//                Method method = methodSignature.getMethod();
//                // 如果有 Except 注解，就不需要做点击防抖处理
////                boolean isExcept = method != null && method.isAnnotationPresent(Except.class);
////                if (isExcept) {
////                    Log.d(TAG, "the click method is except, so proceed it");
////                    joinPoint.proceed();
////                    return;
////                }
//            }
//            Object[] args = joinPoint.getArgs();
//            View view = getViewFromArgs(args);
//            // unknown click type, so skip it
//            if (view == null) {
//                Log.d(TAG, "unknown type method, so proceed it");
//                joinPoint.proceed();
//                return;
//            }
//            //获取View 的 string id
//            String resEntryName = null;
//            String resName = null;
//            resEntryName = view.getContext().getResources().getResourceEntryName(view.getId());
//            resName = view.getContext().getResources().getResourceName(view.getId());
//            Util.saveInfo("coco","点前所在类：" + resEntryName+ "  点击按钮id："+resName);
//            String str = view.toString();
//            String temp = str.substring(str.indexOf("app:id"),str.length()-1);
//            Util.saveInfo("coco","点前所在类：" + view.getContext()+ "  点击按钮id："+temp);
//            Long lastClickTime = (Long) view.getTag(SINGLE_CLICK_KEY);
//            // if lastClickTime is null, means click first time
//            if (lastClickTime == null) {
//                Log.d(TAG, "the click event is first time, so proceed it");
//                view.setTag(SINGLE_CLICK_KEY, SystemClock.elapsedRealtime());
//                joinPoint.proceed();
//                return;
//            }
//            if (canClick(lastClickTime)) {
//                Log.d(TAG, "the click event time interval is legal, so proceed it");
//                view.setTag(SINGLE_CLICK_KEY, SystemClock.elapsedRealtime());
//                joinPoint.proceed();
//                return;
//            }
//
//            Log.d(TAG, "throttle the click event, view id = " + view.getId() +" "+ temp);
//            Util.saveInfo("coco","throttle the click event, view id = " + view.getContext()+ "  "+temp);
//        } catch (Throwable e) {
//            e.printStackTrace();
//            Log.d(TAG, e.getMessage());
//            joinPoint.proceed();
//        }
//    }
//
//    /**
//     * 获取 view 参数
//     *
//     * @param args
//     * @return
//     */
//    private View getViewFromArgs(Object[] args) {
//        if (args != null && args.length > 0) {
//            Object arg = args[0];
//            if (arg instanceof View) {
//                return (View) arg;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 判断是否达到可以点击的时间间隔，这里间隔就设置为500L
//     *
//     * @param lastClickTime
//     * @return
//     */
//    private boolean canClick(long lastClickTime) {
//        return SystemClock.elapsedRealtime() - lastClickTime
//                >= 500L;
//    }
//}
