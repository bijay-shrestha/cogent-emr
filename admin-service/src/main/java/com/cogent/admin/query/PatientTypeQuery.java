package com.cogent.admin.query;

import com.cogent.admin.dto.request.patientType.PatientTypeSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author smriti on 2019-09-26
 */
public class PatientTypeQuery {
    public static final String QUERY_TO_FIND_PATIENT_TYPE_COUNT_BY_NAME =
            "SELECT COUNT(p.id) FROM PatientType p WHERE p.name =:name AND p.status != 'D'";

    public static final String QUERY_TO_FIND_PATIENT_TYPE_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(p.id) FROM PatientType p WHERE p.id!= :id AND p.name =:name AND p.status != 'D'";

    public static final String QUERY_TO_FETCH_ACTIVE_PATIENT_TYPE =
            " SELECT" +
                    " p.id as value," +                                      //[0]
                    " p.name as label" +                                     //[1]
                    " FROM" +
                    " PatientType p" +
                    " WHERE p.status ='Y'";

    public static final String QUERY_TO_FETCH_PATIENT_TYPE_DETAILS =
            " SELECT" +
                    " p.remarks as remarks" +                               //[0]
                    " FROM" +
                    " PatientType p" +
                    " WHERE p.id = :id";

    public static String QUERY_TO_SEARCH_PATIENT_TYPE(PatientTypeSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " p.id as id," +                                      //[0]
                " p.name as name," +                                  //[1]
                " p.status as status" +                              //[2]
                " FROM" +
                " PatientType p" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_PATIENT_TYPE.apply(searchRequestDTO);
    }

    private static Function<PatientTypeSearchRequestDTO, String>
            GET_WHERE_CLAUSE_FOR_SEARCHING_PATIENT_TYPE = (searchRequestDTO) -> {

        String whereClause = " WHERE p.status!='D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
            whereClause += " AND p.status='" + searchRequestDTO.getStatus() + "'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
            whereClause += " AND p.name='" + searchRequestDTO.getName() + "'";

        whereClause += " ORDER BY p.id DESC";

        return whereClause;
    };
}
