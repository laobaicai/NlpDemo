package com.demo.nlp.constants;

/**
 * Created by Lori on 2017/12/15.
 */

public class Constants {
    public static final String TRAIN_REGEX = "([CcDdGgTtKkDdLlYyZz]{1}[0-9]{1,4}/[0-9]{1,4})|([CcDdGgTtKkDdLlYyZz]{1}[0-9]{1,4})"; // G1258/1255或G31，暂不含普通旅客快车（1001-5998），普通旅客列车（6001-7598）
    public static final String TRIO_NAME = "guomei";
    public static final String TRIO_ID = "10012";

    public static final int TRIO_REQUEST_SUCCESS = 0;
    public static final String INVALID_NER_TYPE = "O";

    // 需随着分析线程数的增减而增减
    public static final int TASK_COUNT = 4;


}
