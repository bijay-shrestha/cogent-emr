package com.cogent.admin.query;

import com.cogent.admin.dto.request.surname.SurnameSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author smriti on 2019-09-12
 */
public class SurnameQuery {

    public static final String QUERY_TO_FIND_SURNAME_COUNT_BY_NAME =
            "SELECT COUNT(s.id) FROM Surname s WHERE s.name =:name AND s.status != 'D'";

    public static final String QUERY_TO_FIND_SURNAME_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(s.id) FROM Surname s WHERE s.id!= :id AND s.name =:name AND s.status != 'D'";

    public static final String QUERY_TO_FETCH_ACTIVE_SURNAME_FOR_DROPDOWN =
            " SELECT" +
                    " s.id as value," +                                      //[0]
                    " s.name as label" +                                     //[1]
                    " FROM" +
                    " Surname s" +
                    " WHERE s.status ='Y'";

    public static String QUERY_TO_SEARCH_SURNAME(SurnameSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " s.id as id," +                                      //[0]
                " s.name as name," +                                  //[1]
                " s.status as status," +                              //[2]
                " s.ethnicity.name as ethnicityName" +               //[3]
                " FROM" +
                " Surname s" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_SURNAME.apply(searchRequestDTO);
    }

    private static Function<SurnameSearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCHING_SURNAME =
            (searchRequestDTO) -> {
                String whereClause = " WHERE s.status!='D'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
                    whereClause += " AND s.status='" + searchRequestDTO.getStatus() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
                    whereClause += " AND s.name='" + searchRequestDTO.getName() + "'";

                if (!Objects.isNull(searchRequestDTO.getEthnicityId()))
                    whereClause += " AND s.ethnicity.id = " + searchRequestDTO.getEthnicityId();

                whereClause += " ORDER BY s.id DESC";

                return whereClause;
            };

    public static final String QUERY_TO_FETCH_SURNAME_DETAILS =
            " SELECT" +
                    " s.name as name," +                                  //[1]
                    " s.status as status," +                              //[2]
                    " s.remarks as remarks," +                            //[3]
                    " s.ethnicity.name as ethnicityName" +               //[4]
                    " FROM" +
                    " Surname s" +
                    " WHERE s.status != 'D'" +
                    " AND s.id = :id";

    public static final String QUERY_TO_FETCH_SURNAME_ENTITY =
            " SELECT" +
                    " s.id as id," +
                    " s.name as name," +                                  //[1]
                    " s.status as status," +                              //[2]
                    " s.remarks as remarks," +                            //[3]
                    " s.ethnicity as ethnicity" +               //[4]
                    " FROM" +
                    " Surname s" +
                    " WHERE s.status = 'Y'" +
                    " AND s.id = :id";

}

