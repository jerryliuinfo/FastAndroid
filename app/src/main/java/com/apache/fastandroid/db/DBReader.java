package com.apache.fastandroid.db;

import android.database.Cursor;

import com.apache.fastandroid.bean.Feed;
import com.apache.fastandroid.db.mapper.FeedCursorMapper;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Jerry on 2023/6/23.
 */
public final class DBReader {

   private static final String TAG = "DBReader";

   private DBReader() {
   }

   @NonNull
   private static List<Feed> getFeedList(PodDBAdapter adapter) {
      try (Cursor cursor = adapter.getAllFeedsCursor()) {
         List<Feed> feeds = new ArrayList<>(cursor.getCount());
         while (cursor.moveToNext()) {
            Feed feed = FeedCursorMapper.convert(cursor);
            feeds.add(feed);
         }
         return feeds;
      }
   }
}
