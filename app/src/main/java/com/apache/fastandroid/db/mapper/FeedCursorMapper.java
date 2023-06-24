package com.apache.fastandroid.db.mapper;

import android.database.Cursor;

import com.apache.fastandroid.bean.Feed;

import androidx.annotation.NonNull;

/**
 * Created by Jerry on 2023/6/23.
 */
public class FeedCursorMapper {

   @NonNull
   public static Feed convert(@NonNull Cursor cursor){
      return new Feed();
   }
}
