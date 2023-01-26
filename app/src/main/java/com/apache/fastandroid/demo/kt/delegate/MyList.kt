package com.apache.fastandroid.demo.kt.delegate

/**
 * Created by Jerry on 2023/1/6.
 * 按照 java的写法，实现 SoundBehavior 接口就需要实现该接口的所有方法
 */
class MyList<E>(val list: MutableList<E>):List<E> by list {




}