package com.tesla.framework.component.changelog

import com.tesla.framework.component.changelog.tags.ChangelogTagBugfix
import com.tesla.framework.component.changelog.tags.ChangelogTagInfo
import com.tesla.framework.component.changelog.tags.ChangelogTagNew
import com.tesla.framework.component.changelog.tags.IChangelogTag

/**
 * Created by Jerry on 2023/9/10.
 */
class ChangelogSetup private constructor() {

    private var mValidTags = hashSetOf<IChangelogTag>()


    init {
        mValidTags.add(ChangelogTagInfo())
        mValidTags.add(ChangelogTagNew())
        mValidTags.add(ChangelogTagBugfix())
    }

    fun clearTags() {
        mValidTags.clear()
    }

    fun findTag(tag: String): IChangelogTag? {
        return mValidTags.find {
            it.getXmlTagName() == tag
        }
    }

    fun registeTag(tag: IChangelogTag): ChangelogSetup {
        mValidTags.add(tag)
        return this
    }

    companion object {

        @Volatile
        private var sInstance: ChangelogSetup? = null

        fun get(): ChangelogSetup {
            return sInstance ?: synchronized(ChangelogSetup::class.java) {
                sInstance ?: ChangelogSetup().also {
                    sInstance = it
                }
            }
        }
    }
}