package com.tesla.framework.component.crashreporter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.tesla.framework.R;
import com.tesla.framework.common.util.MScreenShotUtil;
import com.tesla.framework.component.crashreporter.utils.AppUtils;
import com.tesla.framework.component.crashreporter.utils.FileUtils;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LogMessageActivity extends AppCompatActivity {

    private TextView appInfo;

    private String crashContent;

    private ScrollView scrollView;

    private ImageView iv_screen_shot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crash_report_activity_log_message);
        appInfo = (TextView) findViewById(R.id.appInfo);

        scrollView =  findViewById(R.id.scrollView);
        iv_screen_shot =  findViewById(R.id.iv_screen_shot);

        Intent intent = getIntent();
        if (intent != null) {
            String dirPath = intent.getStringExtra("LogMessage");
            File file = new File(dirPath);
            crashContent = FileUtils.readFromFile(file);
            TextView textView = (TextView) findViewById(R.id.logMessage);
            textView.setText(crashContent);
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle(getString(R.string.crash_reporter));
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getAppInfo();
    }

    private void getAppInfo() {
        appInfo.setText(AppUtils.getDeviceDetails(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crash_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = getIntent();
        String filePath = null;
        int id = item.getItemId();
        if (intent != null) {
            filePath = intent.getStringExtra("LogMessage");
        }

        if (id == R.id.delete_log) {
            if (FileUtils.delete(filePath)) {
                finish();
            }
            return true;
        } else if (id== R.id.share_crash_log) {
            shareCrashReport(filePath);
            return true;
        } else if (id == R.id.menu_copy){
            putTextIntoClip();
            ToastUtils.showShort("复制成功");

           return true;
        }else if (id == R.id.menu_capture){
           saveScreenShot();

            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }


    /**
     * 保存截图
     */
    private void saveScreenShot() {
//        showProgressLoading("正在保存截图...");
        //生成截图
        final Bitmap bitmap = MScreenShotUtil.getBitmapByView(scrollView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    String crashPicPath = PathUtils.getInternalAppCachePath() + "/crash_pic_" + System.currentTimeMillis() + ".jpg";
                    boolean saveBitmap = ImageUtils.save(bitmap, crashPicPath, Bitmap.CompressFormat.PNG);
                    if (saveBitmap) {
                        ToastUtils.showShort("保存截图成功，请到相册查看\n路径：" + crashPicPath);
                        final Bitmap bitmapCompress = ImageUtils.getBitmap(new File(crashPicPath), 200, 200);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
//                                dismissProgressLoading();
                                //设置图片
                                iv_screen_shot.setImageBitmap(bitmapCompress);
                                //显示
                                iv_screen_shot.setVisibility(View.VISIBLE);
                                //设置宽高
                                ViewGroup.LayoutParams layoutParams = iv_screen_shot.getLayoutParams();
                                layoutParams.width = ScreenUtils.getScreenWidth();
                                layoutParams.height = bitmapCompress.getHeight() * layoutParams.width / bitmapCompress.getWidth();
                                iv_screen_shot.setLayoutParams(layoutParams);
                                //设置显示动画
                                iv_screen_shot.setPivotX(0);
                                iv_screen_shot.setPivotY(0);
                                AnimatorSet animatorSetScale = new AnimatorSet();
                                ObjectAnimator scaleX = ObjectAnimator.ofFloat(iv_screen_shot, "scaleX", 1, 0.2f);
                                ObjectAnimator scaleY = ObjectAnimator.ofFloat(iv_screen_shot, "scaleY", 1, 0.2f);
                                animatorSetScale.setDuration(1000);
                                animatorSetScale.setInterpolator(new DecelerateInterpolator());
                                animatorSetScale.play(scaleX).with(scaleY);
                                animatorSetScale.start();

                                //三秒后消失
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        iv_screen_shot.setVisibility(View.GONE);
                                    }
                                }, 3000);
                            }
                        });
                    } else {
                        ToastUtils.showShort("保存截图失败");
                    }
                } else {
                    ToastUtils.showShort("保存截图失败");
                }
            }
        }).start();
    }

    /**
     * 添加到剪切板
     */
    public void putTextIntoClip() {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        //创建ClipData对象
        ClipData clipData = ClipData.newPlainText("CrashLog", crashContent);
        //添加ClipData对象到剪切板中
        clipboardManager.setPrimaryClip(clipData);
    }

    private void shareCrashReport(String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_TEXT, appInfo.getText().toString());
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        startActivity(Intent.createChooser(intent, "Share via"));
    }
}
