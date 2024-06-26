package com.tesla.framework.component.logger.adapter;

import com.tesla.framework.component.logger.LogAdapter;
import com.tesla.framework.component.logger.format.FormatStrategy;
import com.tesla.framework.component.logger.format.PrettyFormatStrategy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.tesla.framework.component.logger.Utils.checkNotNull;

/**
 * Android terminal log output implementation for {@link LogAdapter}.
 *
 * Prints output to LogCat with pretty borders.
 *
 * <pre>
 *  ┌──────────────────────────
 *  │ Method stack history
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Log message
 *  └──────────────────────────
 * </pre>
 */
public class AndroidLogAdapter implements LogAdapter {

  @NonNull private final FormatStrategy formatStrategy;

  public AndroidLogAdapter() {
    this.formatStrategy = PrettyFormatStrategy.newBuilder().build();
  }

  public AndroidLogAdapter(@NonNull FormatStrategy formatStrategy) {
    this.formatStrategy = checkNotNull(formatStrategy);
  }

  @Override public boolean isLoggable(int priority, @Nullable String tag) {
    return true;
  }

  @Override public void log(int priority, @Nullable String tag, @NonNull String message) {
    formatStrategy.log(priority, tag, message);
  }

}
