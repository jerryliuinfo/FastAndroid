package com.apache.fastandroid.demo.temp.localdata

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentLocaldatatimeBinding
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date


/**
 * Created by Jerry on 2023/8/5.
 * LocalDate:只含年月日的日期对象
 * LocalTime ：只含时分秒的时间对象
 * LocalDateTime ： 同时含有年月日时分秒的日期对象
 *
 */
class LocalDataTimeDemoFragment:BaseBindingFragment<FragmentLocaldatatimeBinding>(FragmentLocaldatatimeBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnGettime.setOnClickListener {
            getTimeUsage()
        }
        mBinding.btnSettime.setOnClickListener {
            setTimeUsage()
        }
        mBinding.btnTimeAddSubtract.setOnClickListener {
            dateTimePlusOrMinus()
        }

        mBinding.btnSettime2.setOnClickListener {
            setTimeUsage2()
        }
        mBinding.btnGetTime2.setOnClickListener {
            getTimeUsage2()
        }
        mBinding.btnDatatimeCompare.setOnClickListener {
            dataTimeCompare()
        }

        mBinding.btnTimestamp.setOnClickListener {
            timeStampUsage()
        }

        mBinding.btnFormatData.setOnClickListener {
            formatDataUsage()
        }

        mBinding.btnMillesToDatatime.setOnClickListener {
            millesToDataTime()
        }

    }

    private fun millesToDataTime() {
        println("---------long毫秒值转换为日期---------")
        val df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val longToDateTime = df.format(
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.of("Asia/Shanghai")
            )
        )
        println(longToDateTime)
    }

    private fun formatDataUsage() {
        //使用jdk自身配置好的日期格式
        val formatter1 = DateTimeFormatter.ISO_DATE_TIME
        val formatter2 = DateTimeFormatter.ofPattern("dd MMM yyyy")
        val formatter3 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")

        //反过来调用也可以 : date1.format(formatter1);
        val date1Str = formatter1.format(LocalDateTime.now())
        val date2Str = formatter2.format(LocalDateTime.now())
        val date3Str = formatter3.format(LocalDateTime.now())
        println("date1Str:$date1Str, date2Str:$date2Str, date3Str:$date3Str")
    }

    /**
     * 事实上Instant就是java8以前的Date，
        可以使用以下两个类中的方法在这两个类型之间进行转换，
        比如Date.from(Instant)就是用来把Instant转换成java.util.date的，
        而new Date().toInstant()就是将Date转换成Instant的
     */
    private fun timeStampUsage() {
        val instant = Instant.now()
//2019-06-08T16:50:19.174Z
        println(instant)
        val date: Date = Date.from(instant)
        val instant2: Instant = date.toInstant()
//Sun Jun 09 00:50:19 CST 2019
        System.out.println(date)
//2019-06-08T16:50:19.174Z
        println(instant2)
    }

    private fun dataTimeCompare() {
        //判断两个时间点的前后
        val localDate1 = LocalDate.of(2017, 8, 8)
        val localDate2 = LocalDate.of(2018, 8, 8)
        val date1IsBeforeDate2 = localDate1.isBefore(localDate2)
        println("date1IsBeforeDate2 : $date1IsBeforeDate2")
    }

    private fun getTimeUsage2() {
        val localDateTime = LocalDateTime.now()
        val dayOfYear = localDateTime.dayOfYear
        val dayOfMonth = localDateTime.dayOfMonth
        val dayOfWeek = localDateTime.dayOfWeek
        println(
            "今天是" + localDateTime + "\n"
                    + "本年当中第" + dayOfYear + "天" + "\n"
                    + "本月当中第" + dayOfMonth + "天" + "\n"
                    + "本周中星期" + dayOfWeek.value + "-即" + dayOfWeek + "\n"
        )

//获取当天时间的年月日时分秒
        val year = localDateTime.year
        val month = localDateTime.month
        val day = localDateTime.dayOfMonth
        val hour = localDateTime.hour
        val minute = localDateTime.minute
        val second = localDateTime.second
        println(
            ("今天是" + localDateTime + "\n"
                    + "年 ： " + year + "\n"
                    + "月 ： " + month.value + "-即 " + month + "\n"
                    + "日 ： " + day + "\n"
                    + "时 ： " + hour + "\n"
                    + "分 ： " + minute + "\n"
                    + "秒 ： " + second + "\n")
        )
    }

    /**
     * 将年、月、日等修改为指定的值，并返回新的日期（时间）对象
     * 其效果与时间日期相加减差不多，如今天是2018-01-13，要想变为2018-01-20有两种方式
        a. localDate.plusDays(20L) -> 相加指定的天数
        b. localDate.withDayOfYear(20) -> 直接指定到哪一天
     */
    private fun setTimeUsage2() {
        val localDate = LocalDate.now()
        //当前时间基础上，指定本年当中的第几天，取值范围为1-365,366
        val withDayOfYearResult = localDate.withDayOfYear(200)
        //当前时间基础上，指定本月当中的第几天，取值范围为1-29,30,31
        val withDayOfMonthResult = localDate.withDayOfMonth(5)
        //当前时间基础上，直接指定年份
        val withYearResult = localDate.withYear(2017)
        //当前时间基础上，直接指定月份
        val withMonthResult = localDate.withMonth(5)
        println(
            """
        当前时间是 : $localDate
        指定本年当中的第200天 : $withDayOfYearResult
        指定本月当中的第5天 : $withDayOfMonthResult
        直接指定年份为2017 : $withYearResult
        直接指定月份为5月 : $withMonthResult
        
        """.trimIndent()
        )
    }


    /**
     * 日期时间的加减
     * 对于LocalDate,只有精度大于或等于日的加减，如年、月、日；
     * 对于LocalTime,只有精度小于或等于时的加减，如时、分、秒、纳秒；
     * 对于LocalDateTime,则可以进行任意精度的时间相加减；
     */
    private fun dateTimePlusOrMinus() {
        val localDateTime = LocalDateTime.now()
        //以下方法的参数都是long型，返回值都是LocalDateTime
        val plusYearsResult = localDateTime.plusYears(2L)
        val plusMonthsResult = localDateTime.plusMonths(3L)
        val plusDaysResult = localDateTime.plusDays(7L)
        val plusHoursResult = localDateTime.plusHours(2L)
        val plusMinutesResult = localDateTime.plusMinutes(10L)
        val plusSecondsResult = localDateTime.plusSeconds(10L)

        println(
            "当前时间是 : " + localDateTime + "\n"
                    + "当前时间加2年后为 : " + plusYearsResult + "\n"
                    + "当前时间加3个月后为 : " + plusMonthsResult + "\n"
                    + "当前时间加7日后为 : " + plusDaysResult + "\n"
                    + "当前时间加2小时后为 : " + plusHoursResult + "\n"
                    + "当前时间加10分钟后为 : " + plusMinutesResult + "\n"
                    + "当前时间加10秒后为 : " + plusSecondsResult + "\n"
        )



//也可以以另一种方式来相加减日期，即plus(long amountToAdd, TemporalUnit unit)
//                  参数1 ： 相加的数量， 参数2 ： 相加的单位
        val nextMonth = localDateTime.plus(1, ChronoUnit.MONTHS)
        val nextYear = localDateTime.plus(1, ChronoUnit.YEARS)
        val nextWeek = localDateTime.plus(1, ChronoUnit.WEEKS)

        println(
            ("now : " + localDateTime + "\n"
                    + "nextYear : " + nextYear + "\n"
                    + "nextMonth : " + nextMonth + "\n"
                    + "nextWeek :" + nextWeek + "\n")
        )

//日期的减法用法一样，在此不再举例
    }


    /**
     * 根据指定日期/时间创建对象
     */
    private fun setTimeUsage() {
        val localDate = LocalDate.of(2018, 1, 13)
        val localTime = LocalTime.of(9, 43, 20)
        val localDateTime = LocalDateTime.of(2018, 1, 13, 9, 43, 20)
        Logger.d("setTimeUsage localDate:$localDate, localtime:$localTime, localDateTime:$localDateTime")
    }

    /**
     * 获取当前时间
     */
    private fun getTimeUsage() {
        val localDate = LocalDate.now() //2023-08-05
        val localtime = LocalTime.now() //09:30:21.567
        val localDateTime = LocalDateTime.now() //2023-08-05 09:30:21.567
        Logger.d("getTimeUsage localDate:$localDate, localtime:$localtime, localDateTime:$localDateTime")

    }
}