package com.apache.fastandroid.db;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Jerry on 2023/6/23.
 */
public class DBWriter {

   private static final String TAG = "DBWriter";

   private static final ExecutorService dbExec;

   static {
      dbExec = Executors.newSingleThreadExecutor(r -> {
         Thread t = new Thread(r);
         t.setName("DatabaseExecutor");
         t.setPriority(Thread.MIN_PRIORITY);
         return t;
      });
   }

   private DBWriter() {
   }


   public static Future<?> updateToDownloadStatus() {
      return dbExec.submit(() -> {
         PodDBAdapter adapter = PodDBAdapter.getInstance();
         adapter.open();
         adapter.setDownloadStatus();
         adapter.close();
      });

   }
}
