/*
 * @author：kenli
 * @version：1.0.0
 * @created：2014-7-23 上午11:41
 * @remark：
 *
 * Copyright (C) 1998 - 2014 Tencent. All Rights Reserved
 */
package com.apache.fastandroid.util;

/**
 * 表示范围的类，并提供一些范围相关的静态方法
 */
public class Range {
    /**
     * 范围的左边界
     */
    public long mLeft;
    /**
     * 范围的右边界
     */
    public long mRight;

    /**
     * 创建一个表示范围的对象
     */
    public Range() {

    }

    /**
     * 创建一个表示范围的 对象，并初始化值。
     *
     * @param left  左边界
     * @param right 右边界
     */
    public Range(long left, long right) {
        this.mLeft = left;
        this.mRight = right;
    }

    /**
     * 重置数据
     *
     * @param left  左边界
     * @param right 右边界
     */
    public void reset(long left, long right) {
        this.mLeft = left;
        this.mRight = right;
    }

    /**
     * 获取区域的长度
     *
     * @return 如果区域数据有效，则返回长度；否则返回 0；
     */
    public long getLength() {
        if (this.isValidate()) {
            return this.mRight - this.mLeft;
        }
        return 0;
    }

    /**
     * 判断此范围是否有效
     *
     * @return 如果长度大于0，则表示有效，返回true；否则返回false；
     */
    public boolean isValidate() {
        return mRight > mLeft;
    }

    /**
     * 判断此范围是否与另一个范围相交
     *
     * @param otherLeft  另一个范围的左边界
     * @param otherRight 另一个范围的右边界
     * @return 如果两个区域相交，则返回true；否则返回false；
     * <p/>
     * 对于 right == otherLeft 这种情况，判定为不相交。
     */
    public boolean isIntersect(long otherLeft, long otherRight) {
        return Range.isIntersect(this.mLeft, this.mRight, otherLeft, otherRight);
    }

    /**
     * 判断此范围是否与另一个范围相交
     *
     * @param other 另一个范围
     * @return 如果两个区域相交，则返回true；否则返回false；
     * <p/>
     * 对于 right == otherLeft 这种情况，判定为不相交。
     */
    public boolean isIntersect(Range other) {
        return this.isIntersect(other.mLeft, other.mRight);
    }

    /**
     * 根据左右边界，判断范围是否有效
     *
     * @param left  左边界
     * @param right 右边界
     * @return 有效则返回true；否则返回false；left == right 的情况下会判定为无效；
     */
    public static boolean isValidate(long left, long right) {
        return right > left;
    }

    /**
     * 判断两个区域是否相交
     *
     * @param left       区域一的左边界
     * @param right      区域一的右边界
     * @param otherLeft  区域二的左边界
     * @param otherRight 区域二的右边界
     * @return 如果两个区域相交，则返回true；否则返回false；
     * <p/>
     * 对于 right == otherLeft 这种情况，判定为不相交。
     */
    public static boolean isIntersect(long left, long right, long otherLeft, long otherRight) {
        return (left < right) && (otherLeft < otherRight) && (!(right <= otherLeft || otherRight <= left));
    }

    /**
     * 计算此区域与某区域的相交结果
     *
     * @param other  第二个区域
     * @param result 结果存放在这里。
     *               为了避免调用者频繁创建对象，所以此 api 这样设计（好的 api 应该能约束调用者的行为，例如节省内存）。
     * @return 如果两个区域相交，则返回true；否则返回false；
     * <p/>
     * 对于 right == otherLeft 这种情况，判定为不相交。
     */
    public boolean intersect(Range other, Range result) {
        if (this.isIntersect(other)) {
            result.mLeft = Math.max(this.mLeft, other.mLeft);
            result.mRight = Math.min(this.mRight, other.mRight);
            return true;
        }
        return false;
    }
}
