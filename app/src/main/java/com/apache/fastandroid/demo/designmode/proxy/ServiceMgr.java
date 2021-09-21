package com.apache.fastandroid.demo.designmode.proxy;

/**
 * Created by Jerry on 2021/9/21.
 */
public class ServiceMgr {
   private static final ServiceMgr serviceManager = new ServiceMgr();



   private ServiceApi serviceApi;
   private ServiceApiV2 serviceApiV2;
   private UIRouteApi uiRouteApi;
   private WechatApi wechatApi;

   private ServiceMgr() {
      serviceApi = new ServiceApi();
      serviceApiV2 = new ServiceApiV2(serviceApi);
      uiRouteApi = new UIRouteApi();
      wechatApi = new WechatApi();
   }

   public <T extends IService> T getApi(Class<T> tClass) {
      IService ret = serviceApi;
      if (tClass.isAssignableFrom(UIRouteApi.class)) {
         ret = uiRouteApi;
      }else if (tClass.isAssignableFrom(WechatApi.class)){
         ret = wechatApi;
      }else if (tClass.isAssignableFrom(ServiceApiV2.class)){
         ret = serviceApiV2;
      }else if (tClass.isAssignableFrom(ServiceApi.class)){
         ret = serviceApi;
      }
      return (T) ret;
   }

   public static ServiceMgr getInstance() {
      return serviceManager;
   }
}
