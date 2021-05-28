package com.apache.fastandroid.wallpaper;

import java.io.File;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.apache.fastandroid.R;
import com.apache.fastandroid.widget.WallpaperViewer;
import com.apache.fastandroid.widget.WaveView;
import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.component.imageloader.ImageLoaderManager;
import com.tesla.framework.support.inject.ViewInject;
import com.tesla.framework.ui.activity.FragmentArgs;
import com.tesla.framework.ui.activity.FragmentContainerActivity;
import com.tesla.framework.ui.fragment.ABaseFragment;
import com.tesla.framework.ui.widget.photoview.AttacherInterface;
import com.tesla.framework.ui.widget.photoview.PhotoView;

/**
 * Created by 01370340 on 2017/11/21.
 */

public class WallpaperSettingFragment extends ABaseFragment implements WallpaperViewer.WallpaperViewerLisenter {



    @ViewInject(id = R.id.settingView)
    WaveView setting;

    @ViewInject(id = R.id.viewer)
    WallpaperViewer viewer;


    @ViewInject(id = R.id.photoview)
    PhotoView mPhotoView;

    @ViewInject(id = R.id.viewFinish)
    View viewFinish;


    private String url;

    public static void launch(Activity from,String url) {
        FragmentArgs args =  new FragmentArgs();
        args.add("url",url);
        FragmentContainerActivity.launch(from,WallpaperSettingFragment.class,args);
    }

    @Override
    public int inflateContentView() {
        return R.layout.ui_wallpaper_setting;
    }

    @Override
    protected void layoutInit(LayoutInflater inflater, Bundle savedInstanceSate) {
        super.layoutInit(inflater, savedInstanceSate);

        setToolbarTitle("设置壁纸");
        if (savedInstanceSate == null){
            url = getArguments().getString("url");
        }else {
            url = savedInstanceSate.getString("url");
        }
        viewer.init();
        viewer.setWallpaperViewerLisenter(this);

        setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               /* if (!cancelIfRunning()) {
                    setWallpaper();
                }*/
               setWallpaper();

            }

        });
        setupPhotoView();
    }


    private void setWallpaper(){



        new AsyncTask<Void,Void,Boolean>(){
            @Override
            protected Boolean doInBackground(Void... params) {
                String cachePath = ImageLoaderManager.getInstance().getImagePahtFromCache(url);
                if (!TextUtils.isEmpty(cachePath) && new File(cachePath).exists()){
                    File file = new File(cachePath);
                    try {
                        WallpaperDownloadTask.setWallpaper(getContext(),file,null);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return false;
                    }
                    return true;
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (!aBoolean){
                    showMessage("设置壁纸失败");
                }else {
                    showMessage("设置壁纸成功");

                }
            }
        }.execute();
    }


    private void setupPhotoView(){
        mPhotoView.setOnPhotoTapListener(new AttacherInterface.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                FastLog.d(TAG, "onPhotoTap x = %s , y = %s", x,y);
                getActivity().finish();
            }
        });
        ImageLoaderManager.getInstance().showImage(mPhotoView,url,getActivity());
    }

    @Override
    public void onWallpaperViewerScroll(float percent) {
        FastLog.d(TAG, "onWallpaperViewerScroll percent = %s", percent);
        mPhotoView.getAttacher().onMove(percent);
    }


}
