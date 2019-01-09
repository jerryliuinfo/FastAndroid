package com.tesla.framework.common.util.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;


/**
 * Created by allen on 2017/5/10.
 */

public class SPUtil {
    /**
     * 保存在手机里面的文件名
     */
    public static  String FILE_NAME = "share_data";

    private SPUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Context sContext;
    public static void init(Context context){
        sContext = context;
    }

    public static void init(Context context,String prefFilename){
        sContext = context;
        if (!TextUtils.isEmpty(prefFilename)){
            FILE_NAME = prefFilename;
        }
    }

    private static void checkInitialize(){
        if (sContext == null){
            throw new NullPointerException("必须先调用SPUtils.init()初始化 ");
        }
    }



    /**
     * 查询键对应的值
     *
     * @param key
     * @param defaultValue 当该键不存在时返回的值
     * @return
     */
    public static String getString(String key, String defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getString(key, defaultValue);
    }

    public static int getInt(String key, int defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getInt(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getBoolean(key, defaultValue);
    }

    public static float getFloat(String key, float defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getFloat(key, defaultValue);
    }

    public static long getLong(String key, long defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getLong(key, defaultValue);
    }

    public static Set<String> getSet(String key, Set<String> defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getStringSet(key, defaultValue);
    }

    /**
     * 写入新的键值对，如果已存在该键，则覆盖对应的值
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putInt(String key, int value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putFloat(String key, float value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putFloat(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putLong(String key, long value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putLong(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void putSet(String key, Set value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putStringSet(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 移除一个键值对
     *
     * @param key 待移除数据对应的键值
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clearAll() {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = obtainPref();
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = obtainPref();
        return sp.getAll();
    }


    /**
     * 获取SharedPreferences对象
     *
     * @return
     */
    private static SharedPreferences obtainPref() {
        checkInitialize();
        SharedPreferences pref = sContext.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return pref;
    }

    /**
     * 获取SharedPreferences.Editor对象
     *
     * @return
     */
    private static SharedPreferences.Editor obtainPrefEditor() {
        return obtainPref().edit();
    }

/**************************************** 以下为需要传入Context参数的API ******************************************/

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clearAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }


    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }



    public static void putObject(String key, Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SharedPreferences.Editor editor = obtainPrefEditor();
            editor.putString(key, objectVal);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> T getObject(String key, Class<T> clazz) {
        String str = getString(key,"");
        if (TextUtils.isEmpty(str)) {
            byte[] buffer = Base64.decode(str, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(bais);
                T t = (T) ois.readObject();
                return t;
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bais != null) {
                        bais.close();
                    }
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
