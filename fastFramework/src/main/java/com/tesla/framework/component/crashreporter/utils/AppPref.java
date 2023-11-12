// SPDX-License-Identifier: GPL-3.0-or-later

package com.tesla.framework.component.crashreporter.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.ArrayUtils;
import com.blankj.utilcode.util.Utils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class AppPref {
    private static final String PREF_NAME = "preferences";

    private static final int PREF_SKIP = 5;

    /**
     * Preference keys. It's necessary to do things manually as the shared prefs in Android is
     * literary unusable.
     * <br/>
     * Keep these in sync with {@link #getDefaultValue(PrefKey)}.
     */
    public enum PrefKey {
        //必须符合命名规范 PREF_开头, 结尾以 _BOOL,_INT,_LONG,_STR 结尾
        PREF_ADB_MODE_ENABLED_BOOL,
        PREF_LOG_VIEWER_DISPLAY_LIMIT_INT,
        PREF_LAST_VERSION_CODE_LONG,
        PREF_BACKUP_COMPRESSION_METHOD_STR,

        ;



        public static final String[] keys = new String[values().length];
        @Type
        public static final int[] types = new int[values().length];
        public static final List<PrefKey> prefKeyList = Arrays.asList(values());

        static {
            String keyStr;
            int typeSeparator;
            PrefKey[] keyValues = values();
            for (int i = 0; i < keyValues.length; ++i) {
                keyStr = keyValues[i].name();
                typeSeparator = keyStr.lastIndexOf('_');
                keys[i] = keyStr.substring(PREF_SKIP, typeSeparator).toLowerCase(Locale.ROOT);
                types[i] = inferType(keyStr.substring(typeSeparator + 1));
            }
        }

        public static int indexOf(PrefKey key) {
            return prefKeyList.indexOf(key);
        }

        public static int indexOf(String key) {
            return ArrayUtils.indexOf(keys, key);
        }

        @Type
        private static int inferType(@NonNull String typeName) {
            switch (typeName) {
                case "BOOL":
                    return TYPE_BOOLEAN;
                case "FLOAT":
                    return TYPE_FLOAT;
                case "INT":
                    return TYPE_INTEGER;
                case "LONG":
                    return TYPE_LONG;
                case "STR":
                    return TYPE_STRING;
                default:
                    throw new IllegalArgumentException("Unsupported type.");
            }
        }
    }

    @IntDef(value = {
            TYPE_BOOLEAN,
            TYPE_FLOAT,
            TYPE_INTEGER,
            TYPE_LONG,
            TYPE_STRING
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static final int TYPE_BOOLEAN = 0;
    public static final int TYPE_FLOAT = 1;
    public static final int TYPE_INTEGER = 2;
    public static final int TYPE_LONG = 3;
    public static final int TYPE_STRING = 4;

    @SuppressLint("StaticFieldLeak")
    private static AppPref appPref;

    @NonNull
    public static AppPref getInstance() {
        if (appPref == null) {
            appPref = new AppPref(Utils.getApp());
        }
        return appPref;
    }

    @NonNull
    public static AppPref getNewInstance(@NonNull Context context) {
        return new AppPref(context);
    }

    @NonNull
    public static Object get(PrefKey key) {
        int index = PrefKey.indexOf(key);
        AppPref appPref = getInstance();
        switch (PrefKey.types[index]) {
            case TYPE_BOOLEAN:
                return appPref.preferences.getBoolean(PrefKey.keys[index], (boolean) appPref.getDefaultValue(key));
            case TYPE_FLOAT:
                return appPref.preferences.getFloat(PrefKey.keys[index], (float) appPref.getDefaultValue(key));
            case TYPE_INTEGER:
                return appPref.preferences.getInt(PrefKey.keys[index], (int) appPref.getDefaultValue(key));
            case TYPE_LONG:
                return appPref.preferences.getLong(PrefKey.keys[index], (long) appPref.getDefaultValue(key));
            case TYPE_STRING:
                return Objects.requireNonNull(appPref.preferences.getString(PrefKey.keys[index],
                        (String) appPref.getDefaultValue(key)));
        }
        throw new IllegalArgumentException("Unknown key or type.");
    }

    public static boolean getBoolean(PrefKey key) {
        return (boolean) get(key);
    }

    public static int getInt(PrefKey key) {
        return (int) get(key);
    }

    @NonNull
    public static String getString(PrefKey key) {
        return (String) get(key);
    }

    public static boolean isAdbEnabled() {
        return getBoolean(PrefKey.PREF_ADB_MODE_ENABLED_BOOL);
    }


    public static void set(PrefKey key, Object value) {
        getInstance().setPref(key, value);
    }

    public static void setDefault(PrefKey key) {
        AppPref appPref = getInstance();
        appPref.setPref(key, appPref.getDefaultValue(key));
    }

    @NonNull
    private final SharedPreferences preferences;
    @NonNull
    private final SharedPreferences.Editor editor;

    private final Context context;

    @SuppressLint("CommitPrefEdits")
    private AppPref(@NonNull Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        init();
    }

    public void setPref(PrefKey key, Object value) {
        int index = PrefKey.indexOf(key);
        if (value instanceof Boolean) editor.putBoolean(PrefKey.keys[index], (Boolean) value);
        else if (value instanceof Float) editor.putFloat(PrefKey.keys[index], (Float) value);
        else if (value instanceof Integer) editor.putInt(PrefKey.keys[index], (Integer) value);
        else if (value instanceof Long) editor.putLong(PrefKey.keys[index], (Long) value);
        else if (value instanceof String) editor.putString(PrefKey.keys[index], (String) value);
        editor.apply();
        editor.commit();
    }

    public void setPref(String key, @Nullable Object value) {
        int index = PrefKey.indexOf(key);
        if (index == -1) throw new IllegalArgumentException("Invalid key: " + key);
        // Set default value if the requested value is null
        if (value == null) value = getDefaultValue(PrefKey.prefKeyList.get(index));
        if (value instanceof Boolean) editor.putBoolean(key, (Boolean) value);
        else if (value instanceof Float) editor.putFloat(key, (Float) value);
        else if (value instanceof Integer) editor.putInt(key, (Integer) value);
        else if (value instanceof Long) editor.putLong(key, (Long) value);
        else if (value instanceof String) editor.putString(key, (String) value);
        editor.apply();
        editor.commit();
    }

    @NonNull
    public Object get(String key) {
        int index = PrefKey.indexOf(key);
        if (index == -1) throw new IllegalArgumentException("Invalid key: " + key);
        AppPref appPref = getInstance();
        Object defaultValue = appPref.getDefaultValue(PrefKey.prefKeyList.get(index));
        switch (PrefKey.types[index]) {
            case TYPE_BOOLEAN:
                return appPref.preferences.getBoolean(key, (boolean) defaultValue);
            case TYPE_FLOAT:
                return appPref.preferences.getFloat(key, (float) defaultValue);
            case TYPE_INTEGER:
                return appPref.preferences.getInt(key, (int) defaultValue);
            case TYPE_LONG:
                return appPref.preferences.getLong(key, (long) defaultValue);
            case TYPE_STRING:
                return Objects.requireNonNull(appPref.preferences.getString(key, (String) defaultValue));
        }
        throw new IllegalArgumentException("Unknown key or type.");
    }

    private void init() {
        for (int i = 0; i < PrefKey.keys.length; ++i) {
            if (!preferences.contains(PrefKey.keys[i])) {
                switch (PrefKey.types[i]) {
                    case TYPE_BOOLEAN:
                        editor.putBoolean(PrefKey.keys[i], (boolean) getDefaultValue(PrefKey.prefKeyList.get(i)));
                        break;
                    case TYPE_FLOAT:
                        editor.putFloat(PrefKey.keys[i], (float) getDefaultValue(PrefKey.prefKeyList.get(i)));
                        break;
                    case TYPE_INTEGER:
                        editor.putInt(PrefKey.keys[i], (int) getDefaultValue(PrefKey.prefKeyList.get(i)));
                        break;
                    case TYPE_LONG:
                        editor.putLong(PrefKey.keys[i], (long) getDefaultValue(PrefKey.prefKeyList.get(i)));
                        break;
                    case TYPE_STRING:
                        editor.putString(PrefKey.keys[i], (String) getDefaultValue(PrefKey.prefKeyList.get(i)));
                }
            }
        }
        editor.apply();
    }

    @NonNull
    public Object getDefaultValue(@NonNull PrefKey key) {
        switch (key) {

            case PREF_ADB_MODE_ENABLED_BOOL:
                return false;
            case PREF_LOG_VIEWER_DISPLAY_LIMIT_INT:
                return 100;
            case PREF_LAST_VERSION_CODE_LONG:
                return 0L;

        }
        throw new IllegalArgumentException("Pref key not found.");
    }
}
