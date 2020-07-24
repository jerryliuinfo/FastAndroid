package com.apache.fastandroid.sample.builder;

import android.content.Context;

/**
 * author: jerry
 * created on: 2020/7/13 1:00 PM
 * description:
 */
public class BuildDemo {
    private final Context context;

    private BuildDemo(Builder builder) {
        this.context = builder.context;
    }

    public static Builder create(Context context) {
        return new Builder(context);
    }
    public static class Builder {
        private Context context;
        private Builder(Context context) {
            this.context = context;
        }


        public BuildDemo build() {
            if (context == null) {
                throw new IllegalArgumentException("context == null");
            }
            return new BuildDemo(this);
        }
    }

}
