package com.apache.fastandroid.component.spkiller;


import android.content.Context;
import android.os.Build;

import org.lsposed.hiddenapibypass.HiddenApiBypass;

import static android.os.Build.VERSION.SDK_INT;

/**
 * created by Knight-ZXW on 2021/9/14
 */
class DefaultHiddenApiExempter  implements HiddenApiExempter{

    @Override
    public boolean exempt(Context context) {
        if (SDK_INT >= Build.VERSION_CODES.P) {
            return HiddenApiBypass.addHiddenApiExemptions("");
        }
        return true;
    }


}
