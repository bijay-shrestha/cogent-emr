package com.cogent.admin.query;

/**
 * @author Sauravi Thapa 11/10/19
 */
public class WardUnitQuery {

    public final static String FETCH_WARD_UNIT_BY_ID =
            "SELECT " +
                    " wu.id as id," +
                    " wu.ward as ward," +
                    " wu.unit as unit," +
                    " wu.status as status," +
                    " wu.remarks as remarks" +
                    " FROM Ward_Unit wu" +
                    " WHERE wu.ward.id=:id " +
                    " AND wu.status !='D'";

    public final static String FETCH_WARD_UNIT_BY_UNIT_AND_WARD_ID =
            "SELECT " +
                    " wu.id as id," +
                    " wu.ward as ward," +
                    " wu.unit as unit," +
                    " wu.status as status," +
                    " wu.remarks as remarks" +
                    " FROM Ward_Unit wu" +
                    " WHERE wu.ward.id=:wardId" +
                    " AND wu.unit.id=:unitId " +
                    " AND wu.status !='D'";




}
