package com.tesla.framework.component.progress



/**
 *
 * @property type String: 任务类型,用于区分唯一任务
 * @property weight Int: 任务所占比重
 * @constructor
 */
abstract class ITaskInfo(val type:String, var weight:Int)