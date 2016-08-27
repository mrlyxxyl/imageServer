package com.yuexin.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getYearMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        return format.format(new Date());
    }
}
