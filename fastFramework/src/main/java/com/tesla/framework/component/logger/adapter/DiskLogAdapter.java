package com.tesla.framework.component.logger.adapter;

import com.tesla.framework.component.logger.format.CsvFormatStrategy;
import com.tesla.framework.component.logger.format.FormatStrategy;
import com.tesla.framework.component.logger.LogAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.tesla.framework.component.logger.Utils.checkNotNull;


/**
 * This is used to saves log messages to the disk.
 * By default it uses {@link CsvFormatStrategy} to translates text message into CSV format.
 */
public class DiskLogAdapter implements LogAdapter {

  @NonNull private final FormatStrategy formatStrategy;

  public DiskLogAdapter() {
    formatStrategy = CsvFormatStrategy.newBuilder().build();
  }

  public DiskLogAdapter(@NonNull FormatStrategy formatStrategy) {
    this.formatStrategy = checkNotNull(formatStrategy);
  }

  @Override public boolean isLoggable(int priority, @Nullable String tag) {
    return true;
  }

  @Override public void log(int priority, @Nullable String tag, @NonNull String message) {
    formatStrategy.log(priority, tag, message);
  }
}
