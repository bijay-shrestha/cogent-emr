package com.cogent.admin.query;

import com.cogent.admin.dto.request.specialization.SpecializationSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author smriti on 2019-09-25
 */
public class SpecializationQuery {

    public static final String QUERY_TO_FIND_SPECIALIZATION_COUNT_BY_NAME =
            "SELECT COUNT(s.id) FROM Specialization s WHERE s.name =:name AND s.status != 'D'";

    public static final String QUERY_TO_FIND_SPECIALIZATION_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(s.id) FROM Specialization s WHERE s.id!= :id AND s.name =:name AND s.status != 'D'";

    public static final String QUERY_TO_FETCH_ACTIVE_SPECIALIZATION_FOR_DROPDOWN =
            " SELECT" +
                    " s.id as value," +                                      //[0]
                    " s.name as label" +                                     //[1]
                    " FROM" +
                    " Specialization s" +
                    " WHERE s.status ='Y'";

    public static final String QUERY_TO_FETCH_SPECIALIZATION_DETAILS =
            " SELECT s.name as name," +                                     //[0]
                    " s.status as status," +                                //[1]
                    " s.remarks as remarks" +                               //[2]
                    " FROM" +
                    " Specialization s" +
                    " WHERE s.id = :id" +
                    " AND s.status != 'D'";

    public static String QUERY_TO_SEARCH_SPECIALIZATION(SpecializationSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " s.id as id," +                                      //[0]
                " s.name as name," +                                  //[1]
                " s.status as status" +                              //[2]
                " FROM" +
                " Specialization s" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_SPECIALIZATION.apply(searchRequestDTO);
    }

    private static Function<SpecializationSearchRequestDTO, String>
            GET_WHERE_CLAUSE_FOR_SEARCHING_SPECIALIZATION = (searchRequestDTO) -> {

        String whereClause = " WHERE s.status!='D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
            whereClause += " AND s.status='" + searchRequestDTO.getStatus() + "'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
            whereClause += " AND s.name='" + searchRequestDTO.getName() + "'";

        whereClause += " ORDER BY s.id DESC";

        return whereClause;
    };

    public static String QUERY_TO_FETCH_SPECIALIZATION_BY_DOCTOR_ID =
            "SELECT" +
                    " s.id as value," +                                                   //[0]
                    " s.name as label" +                                                 //[1]
                    " FROM DoctorSpecialization cs" +
                    " LEFT JOIN Specialization s ON s.id = cs.specializationId" +
                    " WHERE" +
                    " cs.doctorId =:id" +
                    " AND cs.status = 'Y'" +
                    " AND s.status = 'Y'";
}
