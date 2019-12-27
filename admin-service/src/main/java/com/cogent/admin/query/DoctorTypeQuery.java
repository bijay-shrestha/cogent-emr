package com.cogent.admin.query;

/**
 * @author smriti on 08/11/2019
 */
public class DoctorTypeQuery {
    public static final String QUERY_TO_FETCH_ACTIVE_DOCTOR_TYPE =
            "SELECT" +
                    " c.id as value," +
                    " c.name as label" +
                    " FROM DoctorType c" +
                    " WHERE c.status = 'Y'";
}
