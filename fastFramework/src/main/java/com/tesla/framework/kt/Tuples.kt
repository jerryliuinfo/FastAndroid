package com.tesla.framework.kt

/** Alternative to Pair<Long, Object> that doesn't box long.*/
internal data class LongObjectPair<out B>(
  val first: Long,
  val second: B
)

/** Alternative to Pair<Int, Object> that doesn't box int.*/
internal data class IntObjectPair<out B>(
  val first: Int,
  val second: B
)

/** Alternative to Pair<Long, Long> that doesn't box longs. */
internal data class LongLongPair(
  val first: Long,
  val second: Long
)

/**
 * 标有 infix 关键字的函数也可以使用中缀表示法（忽略该调用的点与圆括号）调用。 中缀函数必须满足以下要求：

 * @receiver Long
 * @param that B
 * @return LongObjectPair<B>
 */
internal infix fun <B> Long.to(that: B): LongObjectPair<B> = LongObjectPair(this, that)

internal infix fun <B> Int.to(that: B): IntObjectPair<B> = IntObjectPair(this, that)

internal infix fun Long.to(that: Long): LongLongPair = LongLongPair(this, that)
