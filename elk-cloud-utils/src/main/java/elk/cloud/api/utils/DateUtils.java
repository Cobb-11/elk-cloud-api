package elk.cloud.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @since 2021年10月16日20:34:18
 *
 * @author Cobb
 */
public class DateUtils {

    /**
     * 指定日期转成指定格式的字符串
     * @param date 日期
     * @param formatStr 格式
     * @return 转换后的字符串
     */
    public static String formatDate(Date date, String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        return format.format(date);
    }

    /**
     * string转date 带格式
     * @param dateStr 日期字符串
     * @param formatStr 格式
     * @return 日期
     */
    public static String formatDate(String dateStr,String formatStr){
        SimpleDateFormat format = new SimpleDateFormat(formatStr);

        Date date = null ;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(date);
    }

    /**
     * string转date
     * @param dateStr string
     * @return date'
     */
    public static Date formatUTCDate(String dateStr){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        dateStr = dateStr.replace("Z","UTC");

        Date date = null;
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
