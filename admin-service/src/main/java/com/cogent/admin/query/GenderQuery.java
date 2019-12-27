package com.cogent.admin.query;

/**
 * @author smriti on 08/11/2019
 */
public class GenderQuery {
    public static final String QUERY_TO_FETCH_ACTIVE_GENDER =
            "SELECT" +
                    " g.id as value," +
                    " g.name as label" +
                    " FROM Gender g" +
                    " WHERE g.status = 'Y'";
}
