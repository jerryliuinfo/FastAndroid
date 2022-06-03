# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/jerryliu/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interfaces
# class:
#-keepclassmembers class fqcn.of.javascript.interfaces.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-optimizationpasses 5                                                           # 代码混淆的压缩比例，值介于0-7，默认5
-verbose                                                                        # 混淆时记录日志
-dontshrink                                                                     # 关闭压缩
-dontpreverify                                                                  # 关闭预校验(作用于Java平台，Android不需要，去掉可加快混淆)
-dontoptimize                                                                   # 关闭代码优化
#-dontobfuscate                                                                  # 关闭混淆
-ignorewarnings                                                                 # 忽略警告
-dontusemixedcaseclassnames                                                     # 混淆后类型都为小写
-dontskipnonpubliclibraryclasses                                                # 不跳过非公共的库的类
-printmapping mapping.txt                                                       # 生成原类名与混淆后类名的映射文件mapping.txt
-useuniqueclassmembernames                                                      # 把混淆类中的方法名也混淆
-allowaccessmodification                                                        # 优化时允许访问并修改有修饰符的类及类的成员
-renamesourcefileattribute SourceFile                                           # 将源码中有意义的类名转换成SourceFile，用于混淆具体崩溃代码
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 指定混淆时采用的算法
-keepattributes *Annotation*d                                                   # 保留注解
-keepattributes Signature                                                       # 保留泛型
-keepattributes SourceFile,LineNumberTable



# 指定外部模糊字典
-obfuscationdictionary ./dictionary
# 指定class模糊字典
-classobfuscationdictionary ./dictionary
# 指定package模糊字典
-packageobfuscationdictionary ./dictionary

# 保留指定类不被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
# 保留support下的所有类及其内部类
-dontwarn android.support.**
-keep class android.support.* {*;}
# 保留support下的类的继承类及其内部类
-keep public class * extends android.support.v4.*
-keep public class * extends android.support.v7.*
-keep public class * extends android.support.annotation.*
# 不混淆资源类
-keep class **.R$* {*;}
-keepclassmembers class **.R$* {
    public static <fields>;
}
# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 保留方法参数是view的方法，使@OnClick等不会被影响
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keepclassmembers class * extends androidx.fragment.app.Fragment {
   public void *(android.view.View);
}
# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}
# 不混淆自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 不混淆枚举类
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}


# 保留Parcelable序列化类不被混淆
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}
# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 避免Log打印输出
-assumenosideeffects class timber.log.Timber {
   public static *** v(...);
   public static *** d(...);
   public static *** i(...);
   public static *** w(...);
}


#----------------------------- kotlinx.coroutines ---------------------------------
# ServiceLoader support
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
# Most of volatile fields are updated with AFU and should not be mangled
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}
# Same story for the standard library's SafeContinuation that also uses AtomicReferenceFieldUpdater
-keepclassmembers class kotlin.coroutines.SafeContinuation {
    volatile <fields>;
}



#----------------------------- gson ---------------------------------
# Gson specific classes
-dontwarn sun.misc.**
# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}
# 保留通过Gson序列化/反序列化的应用程序类不被混淆
# 将下面替换成自己的实体类
-keep class com.apache.fastandroid.network.model.** {*;}






-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}



