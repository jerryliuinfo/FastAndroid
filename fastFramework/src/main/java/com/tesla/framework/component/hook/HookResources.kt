package com.tesla.framework.component.hook

import android.content.res.*
import android.graphics.Movie
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.RequiresApi
import com.tesla.framework.R
import java.io.InputStream

/**
 *
 * Create by lzy on 2022/9/23 16:43
 */
class HookResources(val resources: Resources)
    : Resources(resources.assets, resources.displayMetrics, resources.configuration) {

    companion object {

        private const val TAG = "HookResources"

        /**
         * 替换掉tcl
         */
        fun String.replaceTcl(): String {
            //Log.d(TAG, "replaceTcl: $this")
            return this.replace("tcl ", "", true)
                .replace("tcl", "", true)
        }

        /**
         * 是否包含tcl字符
         */
        fun CharSequence.containsTclStr(): Boolean {
            return this.contains("tcl", true)
        }
    }

    override fun getText(id: Int): CharSequence {
        //hook 全局替换掉tcl
        val originTxt = resources.getText(id)
        if (originTxt.containsTclStr()) {
            return originTxt.toString().replaceTcl()
        }
        return originTxt
    }

    override fun getText(id: Int, def: CharSequence?): CharSequence? {
        //hook 全局替换掉tcl
        val originTxt = resources.getText(id, def)
        if (!originTxt.isNullOrEmpty() && originTxt.containsTclStr()) {
            return originTxt.toString().replaceTcl()
        }
        return originTxt
    }

    override fun getString(id: Int): String {
        if (id == R.string.text_hook) {
            return "哈哈，这是Context.getString(123)的返回结果"
        }
        //hook 全局替换掉tcl
        val originTxt = resources.getString(id)
        if (originTxt.containsTclStr()) {
            return originTxt.replaceTcl()
        }
        return originTxt
    }

    override fun getString(id: Int, vararg formatArgs: Any?): String {
        val originTxt = resources.getString(id, *formatArgs)
        if (originTxt.containsTclStr()) {
            return originTxt.replaceTcl()
        }
        return originTxt
    }

    override fun getTextArray(id: Int): Array<CharSequence?> {
        return resources.getTextArray(id)
    }

    override fun getStringArray(id: Int): Array<String?> {
        return resources.getStringArray(id)
    }

    override fun getQuantityText(id: Int, quantity: Int): CharSequence {
        return resources.getQuantityText(id, quantity)
    }

    override fun getQuantityString(id: Int, quantity: Int, vararg formatArgs: Any?): String {
        return resources.getQuantityString(id, quantity, *formatArgs)
    }

    override fun getQuantityString(id: Int, quantity: Int): String {
        return resources.getQuantityString(id, quantity)
    }

    override fun getIntArray(id: Int): IntArray {
        return resources.getIntArray(id)
    }

    override fun obtainTypedArray(id: Int): TypedArray {
        return resources.obtainTypedArray(id)
    }

    override fun getDimension(id: Int): Float {
        return resources.getDimension(id)
    }

    override fun getDimensionPixelOffset(id: Int): Int {
        return resources.getDimensionPixelOffset(id)
    }

    override fun getDimensionPixelSize(id: Int): Int {
        return resources.getDimensionPixelSize(id)
    }

    override fun getFraction(id: Int, base: Int, pbase: Int): Float {
        return resources.getFraction(id, base, pbase)
    }

    override fun getDrawable(id: Int): Drawable? {
        return resources.getDrawable(id)
    }

    @RequiresApi(21)
    override fun getDrawable(id: Int, theme: Theme?): Drawable? {
        return resources.getDrawable(id, theme)
    }

    override fun getDrawableForDensity(id: Int, density: Int): Drawable? {
        return resources.getDrawableForDensity(id, density)
    }

    @RequiresApi(21)
    override fun getDrawableForDensity(id: Int, density: Int, theme: Theme?): Drawable? {
        return resources.getDrawableForDensity(id, density, theme)
    }

    override fun getMovie(id: Int): Movie? {
        return resources.getMovie(id)
    }

    override fun getColor(id: Int): Int {
        return resources.getColor(id)
    }

    override fun getColorStateList(id: Int): ColorStateList {
        return resources.getColorStateList(id)
    }

    override fun getBoolean(id: Int): Boolean {
        return resources.getBoolean(id)
    }

    override fun getInteger(id: Int): Int {
        return resources.getInteger(id)
    }

    override fun getLayout(id: Int): XmlResourceParser {
        return resources.getLayout(id)
    }

    override fun getAnimation(id: Int): XmlResourceParser {
        return resources.getAnimation(id)
    }

    override fun getXml(id: Int): XmlResourceParser {
        return resources.getXml(id)
    }

    override fun openRawResource(id: Int): InputStream {
        return resources.openRawResource(id)
    }

    override fun openRawResource(id: Int, value: TypedValue?): InputStream {
        return resources.openRawResource(id, value)
    }

    override fun openRawResourceFd(id: Int): AssetFileDescriptor? {
        return resources.openRawResourceFd(id)
    }

    override fun getValue(id: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        resources.getValue(id, outValue, resolveRefs)
    }

    override fun getValueForDensity(id: Int, density: Int, outValue: TypedValue?, resolveRefs: Boolean) {
        resources.getValueForDensity(id, density, outValue, resolveRefs)
    }

    override fun getValue(name: String?, outValue: TypedValue?, resolveRefs: Boolean) {
        resources.getValue(name, outValue, resolveRefs)
    }

    override fun obtainAttributes(set: AttributeSet?, attrs: IntArray?): TypedArray? {
        return resources.obtainAttributes(set, attrs)
    }

    override fun updateConfiguration(config: Configuration?, metrics: DisplayMetrics?) {
        if (resources == null)
            super.updateConfiguration(config, metrics)
        else
            resources.updateConfiguration(config, metrics)
    }

    override fun getDisplayMetrics(): DisplayMetrics? {
        return resources.displayMetrics
    }

    override fun getConfiguration(): Configuration? {
        return resources.configuration
    }

    override fun getIdentifier(name: String?, defType: String?, defPackage: String?): Int {
        return resources.getIdentifier(name, defType, defPackage)
    }

    override fun getResourceName(resid: Int): String? {
        return resources.getResourceName(resid)
    }

    override fun getResourcePackageName(resid: Int): String? {
        return resources.getResourcePackageName(resid)
    }

    override fun getResourceTypeName(resid: Int): String? {
        return resources.getResourceTypeName(resid)
    }

    override fun getResourceEntryName(resid: Int): String? {
        return resources.getResourceEntryName(resid)
    }

    override fun parseBundleExtras(parser: XmlResourceParser?, outBundle: Bundle?) {
        resources.parseBundleExtras(parser, outBundle)
    }

    override fun parseBundleExtra(tagName: String?, attrs: AttributeSet?, outBundle: Bundle?) {
        resources.parseBundleExtra(tagName, attrs, outBundle)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getColor(id: Int, theme: Theme?): Int {
        return resources.getColor(id, theme)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getColorStateList(id: Int, theme: Theme?): ColorStateList {
        return resources.getColorStateList(id, theme)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getFont(id: Int): Typeface {
        return resources.getFont(id)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun getFloat(id: Int): Float {
        return resources.getFloat(id)
    }


}