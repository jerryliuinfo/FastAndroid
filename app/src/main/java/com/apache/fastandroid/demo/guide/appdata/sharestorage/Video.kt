package com.apache.fastandroid.demo.guide.appdata.sharestorage

import android.net.Uri

/**
 * Created by Jerry on 2024/2/3.
 */
data class Video(val uri: Uri,
                 val name: String,
                 val duration: Int,
                 val size: Int
)