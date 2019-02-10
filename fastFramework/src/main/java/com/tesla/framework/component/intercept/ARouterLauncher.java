package com.tesla.framework.component.intercept;

import android.content.Context;

import com.tesla.framework.support.bean.InterceptorBean;

/**
 * Created by Jerry on 2019/2/10.
 */
public class ARouterLauncher {
    private static InterceptorService interceptorService = new InterceptorServiceImpl();

    protected Object navigation(final Context context, final InterceptorBean interceptorBean, final int requestCode, final NavigationCallback callback) {
        try {
            //首先对postcard进行一些处理，设置postcard的destination，type，priority 等一些属性值，completion()后面会有分析
            //LogisticsCenter.completion(postcard);
        } catch (NoRouteFoundException ex) {
            //logger.warning(Consts.TAG, ex.getMessage());

            // 如果处理postcard失败，通过 callback 回调失败结果
            // callback为空的情况下，如果有定义全局的降级处理（DegradeService），则使用全局处理
            //降级处理也需要我们自己实现DegradeService接口
            if (null != callback) {
                callback.onLost();
            } else {    // No callback for this invoke, then we use the global degrade service.
                //DegradeService degradeService = ARouter.getInstance().navigation(DegradeService.class);
                DegradeService degradeService = null;
                if (null != degradeService) {
                    degradeService.onLost(context);
                }
            }

            return null;
        }
        //回调onFound
        if (null != callback) {
            callback.onFound();
        }

        //目前来说，PROVIDER服务类型，以及FRAGMENT类型不需要通过拦截器外，其他类型均需要通过拦截器
        boolean isGreenChannel = true;
        if (isGreenChannel) {   // It must be run in async thread, maybe interceptor cost too mush time made ANR.
            //如果需要拦截就执行onInterrupt(),否则执行onContinue,在onContinue中执行_navigation
            interceptorService.doInterceptions(interceptorBean, new InterceptorCallback() {

                @Override
                public void onContinue(InterceptorBean interceptorBean) {
                    _navigation(context, interceptorBean, requestCode, callback);
                }

                /**
                 * Interrupt process, pipeline will be destory when this method called.
                 *
                 * @param exception Reson of interrupt.
                 */
                @Override
                public void onInterrupt(Throwable exception) {
                    if (null != callback) {
                        callback.onInterrupt();
                    }

                }
            });
        } else {
            return _navigation(context, interceptorBean, requestCode, callback);
        }

        return null;
    }

    private Object  _navigation(final Context context, final InterceptorBean interceptorBean, final int requestCode, final NavigationCallback callback){
        return null;
    }
}
