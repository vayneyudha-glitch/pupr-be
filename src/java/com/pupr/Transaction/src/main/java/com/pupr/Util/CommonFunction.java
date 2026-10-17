package com.pupr.Util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CommonFunction {


    public static String mapperBigDecimalToStringCurrencyFormat(BigDecimal valAmount){
        DecimalFormat df = new DecimalFormat("#,###");

        String amount = df.format(new BigDecimal(String.valueOf(valAmount)));

        return amount;
    }
}
