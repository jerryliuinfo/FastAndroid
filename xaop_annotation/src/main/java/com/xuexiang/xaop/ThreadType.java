/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xuexiang.xaop;

/**
 * <pre>
 *     desc   :
 *     author : xuexiang
 *     time   : 2018/4/23 上午1:02
 * </pre>
 */
public enum ThreadType {

    /**
     * 单线程池
     */
    Single,

    /**
     * 多线程池
     */
    Fixed,

    /**
     * 磁盘读写线程池(本质上是单线程池）
     */
    Disk,

    /**
     * 网络请求线程池(本质上是多线程池）
     */
    Network
}
