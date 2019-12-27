package com.cogent.admin.query;

/**
 * @author smriti on 2019-10-22
 */
public class BillTypeQuery {

    public static final String QUERY_TO_FETCH_ACTIVE_BILLTYPE =
            "SELECT" +
                    " b.id as value," +
                    " b.name as label" +
                    " FROM BillType b" +
                    " WHERE b.status = 'Y'";
}
