package cn.easyproject.easyee.sm.base.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 日期格式化工具类
 * 
 * @author easyproject.cn
 *
 */
public class DateUtil {
/*    
    public static final String FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String FULL_SPRIT = "yyyy/MM/dd HH:mm:ss";
    public static final String FULL_ZH_CN = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FULL_ZH_CN2 = "yyyy年MM月dd日 HH时mm分ss秒";

    public static final String FULL_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String FULL_NO_SECOND_SPRIT = "yyyy/MM/dd HH:mm";
    public static final String FULL_NO_SECOND_ZH_CN = "yyyy年MM月dd日 HH:mm";
    public static final String FULL_NO_SECOND_ZH_CN2 = "yyyy年MM月dd日 HH时mm分";

    public static final String YEAR = "yyyy-MM-dd";
    public static final String YEAR_SPRIT = "yyyy/MM/dd";
    public static final String YEAR_ZH_CN = "yyyy年MM月dd日";

    public static final String TIME = "HH:mm:ss";
    public static final String TIME_ZH_CN = "HH时mm分ss秒";
    public static final String NO_SECOND_C = "yyyy年MM月dd日 HH:mm";
*/
    
    /**
     * SimpleDateFormat 格式化说明</br>
     * 字母 日期或时间元素 表示 示例 </br>
     * G Era 标志符 Text AD </br>
     * y 年 Year 1996; 96 </br>
     * M 年中的月份 Month July; Jul; 07 </br>
     * w 年中的周数 Number 27 </br>
     * W 月份中的周数 Number 2 </br>
     * D 年中的天数 Number 189 </br>
     * d 月份中的天数 Number 10 </br>
     * F 月份中的星期 Number 2 </br>
     * E 星期中的天数 Text Tuesday; Tue </br>
     * a Am/pm 标记 Text PM </br>
     * H 一天中的小时数（0-23） Number 0 </br>
     * k 一天中的小时数（1-24） Number 24 </br>
     * K am/pm 中的小时数（0-11） Number 0 </br>
     * h am/pm 中的小时数（1-12） Number 12 </br>
     * m 小时中的分钟数 Number 30 </br>
     * s 分钟中的秒数 Number 55 </br>
     * S 毫秒数 Number 978 </br>
     * z 时区 General time zone Pacific Standard Time; PST; GMT-08:00 </br>
     * Z 时区 RFC 822 time zone -0800 </br>
     */
    
    /**
     * 格式化日期 指定格式
     * @param date
     * @param formatStyle
     * @return
     */
    public static String formatDate(Date date, String formatStyle) {
        String defaultStyle = "yyyy-MM-dd HH:mm:ss";
        if (null != formatStyle && !"".equals(formatStyle.trim())) {
            defaultStyle = formatStyle;
        } 
        DateFormat sdf = new SimpleDateFormat(defaultStyle);
        return sdf.format(date);
    }

    /**
     * 字符串转日期对象
     * @param dateStr
     * @param parseStyle
     * @return
     */
    public static Date parseDate(String dateStr, String parseStyle) {
        Date date = null;
        if (null != parseStyle && !"".equals(parseStyle.trim()) && null != dateStr && !"".equals(dateStr.trim())) {
            SimpleDateFormat sdf = new SimpleDateFormat(parseStyle);
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    /**
     * 获取计算后的日期
     * @param date 参照日期
     * @param field  the java.util.Calendar field.
     * @param amount the amount of date or time to be added to the field.
     * @return
     */
    public static Date getAddDate(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 获取当前日期（时分秒为0）
     */
    public static Date getCurrentDate() {
        return parseDate(formatDate(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
    }
    
    /**
     * 获取日期的前一天（时分秒为0）
     * @param date 参照日期
     */
    public static Date getYesterday(Date date) {
        return getAddDate(date == null ? getCurrentDate() : date, Calendar.DATE, -1);
    }

    /**
     * 获取日期的后一天（时分秒为0）
     * @param date 参照日期
     */
    public static Date getTomorrow(Date date) {
        return getAddDate(date == null ? getCurrentDate() : date, Calendar.DATE, 1);
    }

    /**
     * 获取某月的最后一天，@monthAmount基于当前月加减
     * @param date 参照日期
     * @param monthAmount the amount of date or time to be added on the java.util.Calendar.MONTH field.
     * @return
     */
    public static Date getLastDateOfMonth(Date date, int monthAmount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((date == null ? getCurrentDate() : date));
        // 当前月＋1，即下个月
        calendar.add(Calendar.MONTH, 1 + monthAmount);
        // 将下个月1号作为日期初始值
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 下个月1号减去一天，即得到当前月最后一天
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }
    
    /**
     * 获取某月的第一天 ，@monthAmount基于当前月加减
     * @param date 参照日期
     * @param monthAmount the amount of date or time to be added on the java.util.Calendar.MONTH field.
     * @return
     */
    public static Date getFirstDateOfMonth(Date date, int monthAmount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime((date == null ? getCurrentDate() : date));
        calendar.add(Calendar.MONTH, monthAmount);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 
     * 获取某月的天数 ，@monthAmount基于当前月加减
     * @param date 参照日期
     * @param monthAmount the amount of date or time to be added on the java.util.Calendar.MONTH field.
     * @return
     */
    public static int getMaxDayOfMonth(Date date, int monthAmount){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthAmount);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    public static void main(String[] args) {
        System.out.println(getMaxDayOfMonth(getTomorrow(null), 0));
    }
    
}