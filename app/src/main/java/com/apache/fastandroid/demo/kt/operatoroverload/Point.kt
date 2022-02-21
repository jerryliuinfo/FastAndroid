package com.apache.fastandroid.demo.kt.operatoroverload

/**
 * Created by Jerry on 2022/2/20.
 */
data class Point(var x: Int, var y: Int)

operator fun Point.unaryMinus() = Point(-x, -y)

operator fun Point.inc() = Point(x+1, y+1)

operator fun Point.dec() = Point(x-1, y-1)

//operator fun Point.plus(point: Point) = Point(x + point.x, y + point.y)

operator fun Point.minus(point: Point) = Point(x - point.x, y - point.y)

operator fun Point.times(point: Point) = Point(x * point.x, y * point.y)

operator fun Point.div(point: Point) = Point(x / point.x, y / point.y)

operator fun Point.compareTo(point: Point):Int = x -point.x

//a += b
operator fun Point.plusAssign(point: Point){
    x += point.x
    y += point.y
}


//求模
operator fun Point.rem(point: Point) = Point(x % point.x, y % point.y)


fun main() {
//    println(-point)  // prints "Point(x=-10, y=-20)"
}