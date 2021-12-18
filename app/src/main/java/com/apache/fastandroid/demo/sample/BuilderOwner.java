package com.apache.fastandroid.demo.sample;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  on 2021/12/18.
 */
public class BuilderOwner {

   private Builder builder;

   private Map<Class<? extends ICallback>, ICallback> callbacks = new HashMap<>();


   public BuilderOwner(Builder builder) {
      this.builder = builder;
   }

   public void register(){
      initCallback(builder);
   }

   private void initCallback(Builder builder) {
      List<ICallback> callbacks = builder.getCallbacks();
      final Class<? extends ICallback> defalutCallback = builder.getDefaultCallback();
      if (callbacks != null && callbacks.size() > 0) {
         for (ICallback callback : callbacks) {
            setupCallback(callback);
         }
      }

   }

   public void setupCallback(ICallback callback) {
      ICallback cloneCallback = callback.copy();
      addCallback(cloneCallback);
   }

   public void addCallback(ICallback callback) {
      if (!callbacks.containsKey(callback.getClass())) {
         callbacks.put(callback.getClass(), callback);
      }
   }

   public void showCallback(Class<? extends ICallback> callback){
     checkCallbackExist(callback);
   }

   private void checkCallbackExist(Class<? extends ICallback> callback) {
      if (!callbacks.containsKey(callback)) {
         throw new IllegalArgumentException(String.format("The Callback (%s) is nonexistent.", callback
                 .getSimpleName()));
      }
   }

   public static class Builder{

      private List<ICallback> callbacks = new ArrayList<>();

      private Class<? extends ICallback> defaultCallback;


      public Builder addCallback(ICallback callback){
         callbacks.add(callback);
         return this;
      }

      public Builder setDefaultCallback(Class<? extends ICallback> defaultCallback) {
         this.defaultCallback = defaultCallback;
         return this;
      }

      public BuilderOwner build(){
         return new BuilderOwner(this);
      }

      public List<ICallback> getCallbacks() {
         return callbacks;
      }


      public Class<? extends ICallback> getDefaultCallback() {
         return defaultCallback;
      }
   }



   public static abstract class ICallback implements Serializable {

      public ICallback copy() {
         ByteArrayOutputStream bao = new ByteArrayOutputStream();
         ObjectOutputStream oos;
         Object obj = null;
         try {
            oos = new ObjectOutputStream(bao);
            oos.writeObject(this);
            oos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(bao.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
         } catch (Exception e) {
            e.printStackTrace();
         }
         return (ICallback) obj;
      }
   }

   public static class CallbackImpl1 extends ICallback{

   }

   public static class CallbackImpl2 extends ICallback{

   }

   public static class CallbackImpl3 extends ICallback{

   }
}
