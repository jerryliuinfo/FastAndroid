package com.apache.fastandroid.demo.storage

import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.kt.*
import com.apache.fastandroid.demo.kt.collection.CollectionDemoFragment
import com.apache.fastandroid.demo.kt.delegate.KotlinDelegateFragment
import com.apache.fastandroid.demo.kt.extension.ExtensionDemoFragment
import com.apache.fastandroid.demo.kt.generics.GenericDemoFragment
import com.apache.fastandroid.demo.kt.lambda.LambdaDemoFragment
import com.apache.fastandroid.demo.kt.practice.JuejinKtDemoListFragment
import com.apache.fastandroid.demo.storage.documenttree.DocumentTreeDemoActivity
import com.apache.fastandroid.demo.storage.filemannager.FileExplorerActivity
import com.apache.fastandroid.demo.storage.opendocument.OpenDocumentDemoFragment

/**
 * Created by Jerry on 2021/10/18.
 * https://github.com/android/storage-samples
 */
class StorageDemoListFragment: BaseListFragment()
{
    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
            ViewItemBean("Storage 用法", "打开pdf",  OpenDocumentDemoFragment::class.java)
            ,ViewItemBean("Document Tree", "Document Tree",  activity = DocumentTreeDemoActivity::class.java)
            ,ViewItemBean("File Manager", "File Manager",   FileExplorerActivity::class.java, addTitleBar = false)

        )
    }
}