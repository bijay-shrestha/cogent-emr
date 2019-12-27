package com.cogent.admin.query;

import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author smriti on 2019-09-26
 */
public class AppointmentTypeQuery {
    public static final String QUERY_TO_FIND_APPOINTMENT_TYPE_COUNT_BY_NAME =
            "SELECT COUNT(a.id) FROM AppointmentType a WHERE a.name =:name AND a.status != 'D'";

    public static final String QUERY_TO_FIND_APPOINTMENT_TYPE_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(a.id) FROM AppointmentType a WHERE a.id!= :id AND a.name =:name AND a.status != 'D'";

    public static final String QUERY_TO_FETCH_ACTIVE_APPOINTMENT_TYPE =
            " SELECT" +
                    " a.id as value," +                                      //[0]
                    " a.name as label" +                                     //[1]
                    " FROM" +
                    " AppointmentType a" +
                    " WHERE a.status ='Y'";

    public static final String QUERY_TO_FETCH_APPOINTMENT_TYPE_DETAILS =
            " SELECT" +
                    " a.remarks as remarks" +                               //[0]
                    " FROM" +
                    " AppointmentType a" +
                    " WHERE a.id = :id";

    public static String QUERY_TO_SEARCH_APPOINTMENT_TYPE(AppointmentTypeSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " a.id as id," +                                      //[0]
                " a.name as name," +                                  //[1]
                " a.status as status" +                              //[2]
                " FROM" +
                " AppointmentType a" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_APPOINTMENT_TYPE.apply(searchRequestDTO);
    }

    private static Function<AppointmentTypeSearchRequestDTO, String>
            GET_WHERE_CLAUSE_FOR_SEARCHING_APPOINTMENT_TYPE = (searchRequestDTO) -> {

        String whereClause = " WHERE a.status!='D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
            whereClause += " AND a.status='" + searchRequestDTO.getStatus() + "'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
            whereClause += " AND a.name='" + searchRequestDTO.getName() + "'";

        whereClause += " ORDER BY a.id DESC";

        return whereClause;
    };
}
