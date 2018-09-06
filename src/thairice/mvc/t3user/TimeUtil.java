package thairice.mvc.t3user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author xxx
 * @description 时间工具类
 * @time 2016年8月13日
 */
public class TimeUtil {
    public static List<Map<String, Object>> list;
    private final static ThreadLocal<SimpleDateFormat> timeFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 格式化日期(精确到秒)
     *
     * @return
     */
    public static String getNow() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(System.currentTimeMillis());
    }

    /**
     * 格式化日期(精确到秒)
     *
     * @return
     */
    public static String getTIME() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(System.currentTimeMillis());
    }

    /**
     * 格式化日期(精确到天)
     *
     * @return
     */
    public static String getDay() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(System.currentTimeMillis());
    }

    public static String getDayFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年-MM月-dd日");
        return format.format(System.currentTimeMillis());
    }

    //获取时分
    public static String getHour() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(System.currentTimeMillis());
    }


    /**
     * 将字符串转为日期类型
     *
     * @param date
     * @return
     */
    public static Date toDate(String date) {
        return toDate(date, timeFormater.get());
    }

    public static Date toDate(String date, SimpleDateFormat dateFormater) {
        try {
            return dateFormater.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param format    时间格式 例如:yyyy-MM-dd HH:mm:ss
     * @param str       计算 d:计算天数，h:计算小时，m:计算分钟
     * @return
     */
    public static Long dateDiff(String startTime, String endTime, String format, String str) {
        // 按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat(format);
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        // 获得两个时间的毫秒时间差异
        try {
            diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
            day = diff / nd;// 计算差多少天
            hour = diff % nd / nh + day * 24;// 计算差多少小时
            min = diff % nd % nh / nm + day * 24 * 60;// 计算差多少分钟
            sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
/*            System.out.println(
                    "时间相差：" + day + "天" + (hour - day * 24) + "小时" + (min - day * 24 * 60) + "分钟" + sec + "秒。");
            System.out.println("hour=" + hour + ",min=" + min);*/
            if (str.equalsIgnoreCase("d")) {
                return day;
            } else if (str.equalsIgnoreCase("h")) {
                return hour;
            } else {
                return min;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (str.equalsIgnoreCase("h")) {
            return hour;
        } else {
            return min;
        }
    }

    /**
     * 判断是否是过去的日期
     *
     * @param str 输入的日期
     * @return true 早于现在，false 晚于现在
     */
    public static boolean isLaterThanNow(String str) {

        boolean flag = false;
        Date nowDate = new Date();
        Date pastDate = null;
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.CHINA);
        //在日期字符串非空时执行
        try {
            //将字符串转为日期格式，如果此处字符串为非合法日期就会抛出异常。
            pastDate = sdf.parse(str);
            //调用Date里面的before方法来做判断
            flag = pastDate.before(nowDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
