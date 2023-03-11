package com.tesla.framework.component.logger.format;

import android.text.TextUtils;

import com.tesla.framework.component.logger.LogStrategy;
import com.tesla.framework.component.logger.output.LogcatLogStrategy;
import com.tesla.framework.component.logger.Logger;
import com.tesla.framework.component.logger.LoggerPrinter;
import com.tesla.framework.component.logger.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.tesla.framework.component.logger.Utils.checkNotNull;

public class SimpleFormatStrategy implements FormatStrategy {

  /**
   * Android's max limit for a log entry is ~4076 bytes,
   * so 4000 bytes is used as chunk size since default charset
   * is UTF-8
   */
  private static final int CHUNK_SIZE = 4000;

  /**
   * The minimum stack trace index, starts at this class after two native calls.
   */
  private static final int MIN_STACK_OFFSET = 5;

  /**
   * Drawing toolbox
   */
  private static final char TOP_LEFT_CORNER = '┌';
  private static final char BOTTOM_LEFT_CORNER = '└';
  private static final char MIDDLE_CORNER = '├';
  private static final char HORIZONTAL_LINE = '│';
  private static final String DOUBLE_DIVIDER = "────────────────────────────────────────────────────────";
  private static final String SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄";
  private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
  private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

  private final int methodCount;
  private final int methodOffset;
  private final boolean showThreadInfo;
  @NonNull private final LogStrategy logStrategy;
  @Nullable private final String tag;

  private SimpleFormatStrategy(@NonNull Builder builder) {
    checkNotNull(builder);

    methodCount = builder.methodCount;
    methodOffset = builder.methodOffset;
    showThreadInfo = builder.showThreadInfo;
    logStrategy = builder.logStrategy;
    tag = builder.tag;
  }

  @NonNull public static Builder newBuilder() {
    return new Builder();
  }

  @Override public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
    checkNotNull(message);

    String tag = formatTag(onceOnlyTag);


    //get bytes of message with system's default charset (which is UTF-8 for Android)
    byte[] bytes = message.getBytes();
    int length = bytes.length;
    if (length <= CHUNK_SIZE) {
      logContent(priority, tag, message);
      return;
    }

    for (int i = 0; i < length; i += CHUNK_SIZE) {
      int count = Math.min(length - i, CHUNK_SIZE);
      //create a new String with system's default charset (which is UTF-8 for Android)
      logContent(priority, tag, new String(bytes, i, count));
    }
  }

  private void logTopBorder(int logType, @Nullable String tag) {
    logChunk(logType, tag, TOP_BORDER);
  }


  private void logContent(int logType, @Nullable String tag, @NonNull String chunk) {
    checkNotNull(chunk);

    String[] lines = chunk.split(System.getProperty("line.separator"));
    for (String line : lines) {
      logChunk(logType, tag, HORIZONTAL_LINE + " " + line);
    }
  }

  private void logChunk(int priority, @Nullable String tag, @NonNull String chunk) {
    checkNotNull(chunk);

    logStrategy.log(priority, tag, chunk);
  }

  private String getSimpleClassName(@NonNull String name) {
    checkNotNull(name);

    int lastIndex = name.lastIndexOf(".");
    return name.substring(lastIndex + 1);
  }

  /**
   * Determines the starting index of the stack trace, after method calls made by this class.
   *
   * @param trace the stack trace
   * @return the stack offset
   */
  private int getStackOffset(@NonNull StackTraceElement[] trace) {
    checkNotNull(trace);

    for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
      StackTraceElement e = trace[i];
      String name = e.getClassName();
      if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
        return --i;
      }
    }
    return -1;
  }

  @Nullable private String formatTag(@Nullable String tag) {
    if (!Utils.isEmpty(tag) && !TextUtils.equals(this.tag, tag)) {
      return this.tag + "-" + tag;
    }
    return this.tag;
  }

  public static class Builder {
    int methodCount = 2;
    int methodOffset = 0;
    boolean showThreadInfo = true;
    @Nullable LogStrategy logStrategy;
    @Nullable String tag = "PRETTY_LOGGER";

    private Builder() {
    }

    @NonNull public static SimpleFormatStrategy.Builder newBuilder() {
      return new SimpleFormatStrategy.Builder();
    }

    @NonNull public Builder tag(@Nullable String tag) {
      this.tag = tag;
      return this;
    }

    @NonNull public SimpleFormatStrategy build() {
      if (logStrategy == null) {
        logStrategy = new LogcatLogStrategy();
      }
      return new SimpleFormatStrategy(this);
    }
  }

}
