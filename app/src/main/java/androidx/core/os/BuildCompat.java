/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *//*


package androidx.core.os;

import android.os.Build.VERSION;
import android.widget.Toast;

import com.blankj.utilcode.util.Utils;
import com.iwhys.sdkeditor.domain.ReplaceClass;


*/
/**
 * This class contains additional platform version checking methods for targeting pre-release
 * versions of Android.
 * 这是我们要修复的类
 * 在[BuildCompat.isAtLeastQ]方法中添加一个Toast
 *//*

@ReplaceClass("core:1.3.0")
public class BuildCompat {
    private BuildCompat() {
    }

    */
/**
     * Checks if the device is running on the Android N release or newer.
     *
     * @return {@code true} if N APIs are available for use
     * @deprecated Android N is a finalized release and this method is no longer necessary. It will
     *             be removed in a future release of the Support Library. Instead, use
     *             {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.N}.
     *//*

    @Deprecated
    public static boolean isAtLeastN() {
        return VERSION.SDK_INT >= 24;
    }

    */
/**
     * Checks if the device is running on the Android N MR1 release or newer.
     *
     * @return {@code true} if N MR1 APIs are available for use
     * @deprecated Android N MR1 is a finalized release and this method is no longer necessary. It
     *             will be removed in a future release of the Support Library. Instead, use
     *             {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1}.
     *//*

    @Deprecated
    public static boolean isAtLeastNMR1() {
        return VERSION.SDK_INT >= 25;
    }

    */
/**
     * Checks if the device is running on a pre-release version of Android O or newer.
     * <p>
     * @return {@code true} if O APIs are available for use, {@code false} otherwise
     * @deprecated Android O is a finalized release and this method is no longer necessary. It will
     *             be removed in a future release of the Support Library. Instead use
     *             {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.O}.
     *//*

    @Deprecated
    public static boolean isAtLeastO() {
        return VERSION.SDK_INT >= 26;
    }

    */
/**
     * Checks if the device is running on a pre-release version of Android O MR1 or newer.
     * <p>
     * @return {@code true} if O MR1 APIs are available for use, {@code false} otherwise
     * @deprecated Android O MR1 is a finalized release and this method is no longer necessary. It
     *             will be removed in a future release of the Support Library. Instead, use
     *             {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1}.
     *//*

    @Deprecated
    public static boolean isAtLeastOMR1() {
        return VERSION.SDK_INT >= 27;
    }

    */
/**
     * Checks if the device is running on a pre-release version of Android P or newer.
     * <p>
     * @return {@code true} if P APIs are available for use, {@code false} otherwise
     * @deprecated Android P is a finalized release and this method is no longer necessary. It
     *             will be removed in a future release of the Support Library. Instead, use
     *             {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.P}.
     *//*

    @Deprecated
    public static boolean isAtLeastP() {
        return VERSION.SDK_INT >= 28;
    }

    */
/**
     * Checks if the device is running on a pre-release version of Android Q or newer.
     * <p>
     * @return {@code true} if Q APIs are available for use, {@code false} otherwise
     * @deprecated Android Q is a finalized release and this method is no longer necessary. It
     *             will be removed in a future release of the Support Library. Instead, use
     *             {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q}.
     *//*

    @Deprecated
    public static boolean isAtLeastQ() {
        return VERSION.SDK_INT >= 29;
    }

    */
/**
     * Checks if the device is running on a pre-release version of Android R or newer.
     * <p>
     * <strong>Note:</strong> This method will return {@code false} on devices running release
     * versions of Android. When Android R is finalized for release, this method will be deprecated
     * and all calls should be replaced with {@code Build.VERSION.SDK_INT >= Build.VERSION_CODES.R}.
     *
     * @return {@code true} if R APIs are available for use, {@code false} otherwise
     *//*

    public static boolean isAtLeastR() {
        Toast.makeText(Utils.getApp(), "You successfully added a Toast to \"BuildCompat#isAtLeastR()\"", Toast.LENGTH_LONG).show();
        return VERSION.CODENAME.length() == 1
                && VERSION.CODENAME.charAt(0) >= 'R'
                && VERSION.CODENAME.charAt(0) <= 'Z';
    }
}
*/
