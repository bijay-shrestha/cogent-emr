package com.cogent.admin.constants;

import java.util.function.Function;

public class ExcelConstants {

    public final static String IMAGE_LOCATION = "./admin-service/src/main/resources/static/excelimage/cogent.png";

    public final static int COLUMN_SIZE = 3;

    public final static int ROW_SIZE = 11;

    public final static int SET_X_COORDINATE_IN_FIRST_CELL = 1;

    public final static int SET_ROW = 0;

    public final static Function<Integer, Integer> SET_COLUMN = (columnLength) -> (
            ((columnLength) / 2) - 1
    );

    public final static int ROW_NUM = 12;

    public final static int SERIAL_NUMBER = 1;

    public final static int CELL_FOR_SN = 0;

    public final static int ROW_FOR_HEADER = 11;

    public final static int FONT_SIZE_FOR_HEADER = 10;


}
