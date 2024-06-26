package com.example.demo.common.constant;

import java.util.Arrays;
import java.util.List;

public class ProductType {
    public static final String TRA_TRUOC = "tra truoc";
    public static final String TRA_SAU = "tra sau";
    public static final String DATA = "data";
    public static final String ROAMING = "roaming";

    public static List<String>getListProductTypes(){
        return Arrays.asList(TRA_TRUOC, TRA_SAU, DATA, ROAMING);
    }
}
