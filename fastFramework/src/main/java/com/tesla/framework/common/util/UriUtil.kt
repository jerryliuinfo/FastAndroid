package com.tesla.framework.common.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.VisibleForTesting
import com.tesla.framework.component.logger.Logger
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder

object UriUtil {

    const val WIKI_REGEX = "/(wiki|[a-z]{2,3}|[a-z]{2,3}-.*)/"

    @JvmStatic
    fun decodeURL(url: String): String {
        return try {
            // Force decoding of plus sign, since the built-in decode() function will replace
            // plus sign with space.
            URLDecoder.decode(url.replace("+", "%2B"), "UTF-8")
        } catch (e: IllegalArgumentException) {
            // Swallow IllegalArgumentException (can happen with malformed encoding), and just
            // return the original string.
            Logger.d("URL decoding failed. String was: $url")
            url
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }
    }

    @JvmStatic
    fun encodeURL(url: String): String {
        return try {
            // Before returning, explicitly convert plus signs to encoded spaces, since URLEncoder
            // does that for some reason.
            URLEncoder.encode(url, "UTF-8").replace("+", "%20")
        } catch (e: UnsupportedEncodingException) {
            throw RuntimeException(e)
        }
    }

    private fun visitInExternalBrowserExplicit(context: Context, uri: Uri) {
        context.packageManager.queryIntentActivities(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com")), PackageManager.MATCH_DEFAULT_ONLY)
            .first().let {
                val componentName = ComponentName(it.activityInfo.packageName, it.activityInfo.name)
                val newIntent = Intent(Intent.ACTION_VIEW)
                newIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                newIntent.data = uri
                newIntent.component = componentName
                context.startActivity(newIntent)
            }
    }


    @JvmStatic
    fun isValidPageLink(uri: Uri): Boolean {
        return ((!uri.authority.isNullOrEmpty() &&
                uri.authority!!.endsWith("wikipedia.org") &&
                !uri.path.isNullOrEmpty() &&
                uri.path!!.matches(("^$WIKI_REGEX.*").toRegex())) &&
                (uri.fragment == null || (uri.fragment!!.isNotEmpty() &&
                        !uri.fragment!!.startsWith("cite"))))
    }


    @JvmStatic
    fun getFilenameFromUploadUrl(url: String): String {
        val splitList = url.split("/")
        val thumbnailName = splitList[splitList.size - 1]
        return if (url.contains("/thumb/") && splitList.size > 2) {
            splitList[splitList.size - 2]
        } else thumbnailName
    }

    @JvmStatic
    fun getTitleFromUrl(url: String): String {
        return removeFragment(removeLinkPrefix(url)).replace("_", " ")
    }

    /** Get language variant code from a Uri, e.g. "zh.*", otherwise returns empty string.  */
    @JvmStatic
    fun getLanguageVariantFromUri(uri: Uri): String {
        if (uri.path.isNullOrEmpty()) {
            return ""
        }
        val parts = uri.path!!.split('/')
        return if (parts.size > 1 && parts[0] != "wiki") parts[0] else ""
    }

    /** For internal links only  */
    @JvmStatic
    fun removeInternalLinkPrefix(link: String): String {
        return link.replaceFirst(WIKI_REGEX.toRegex(), "")
    }

    /** For links that could be internal or external links  */
    @JvmStatic
    fun removeLinkPrefix(link: String): String {
        return link.replaceFirst("^.*?$WIKI_REGEX".toRegex(), "")
    }

    /** Removes an optional fragment portion of a URL  */
    @JvmStatic
    @VisibleForTesting
    fun removeFragment(link: String): String {
        return link.replaceFirst("#.*$".toRegex(), "")
    }

    @JvmStatic
    fun parseTalkTopicFromFragment(fragment: String): String {
        val index = fragment.indexOf("Z-")
        return if (index >= 0) fragment.substring(index + 2) else fragment
    }
}
