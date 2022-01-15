package per.goweii.basic.core.glide.progress;

/**
 * 描述：
 *
 * @author Cuizhen
 * @date 2018/9/17
 */
/*@GlideModule
public class ProgressAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getOkHttpClient()));
        Logger.d("ProgressAppGlideModule", "registerComponents down");
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    private static OkHttpClient getOkHttpClient() {
        Logger.d("ProgressAppGlideModule", "getOkHttpClient");
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        HttpsCompat.ignoreSSLForOkHttp(builder);
//        HttpsCompat.enableTls12ForOkHttp(builder);
        builder.addInterceptor(new ProgressInterceptor());
        Logger.d("ProgressAppGlideModule", "getOkHttpClient down");
        return builder.build();
    }
}*/
