package com.cogent.admin.query;


import com.cogent.admin.dto.request.ethnicity.EthnicitySearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi  on 2019-08-25
 */

public class EthnicityQuery {

    public final static String FETCH_ETHNICITY_DETAILS =
            "SELECT" +
                    " e.name as name," +
                    " e.code as code," +
                    " e.status as status," +
                    " e.remarks as remarks," +
                    " e.createdDate as createdDate , " +
                    " e.lastModifiedDate as lastModifiedDate," +
                    " e.createdById as createdById," +
                    " e.modifiedById as modifiedById" +
                    " FROM Ethnicity e" +
                    " WHERE e.id =:id" +
                    " AND e.status != 'D'";

    public final static String FETCH_ETHNICITY_BY_NAME_AND_CODE =
            "SELECT " +
                    " e.name," +
                    " e.code" +
                    " FROM Ethnicity e" +
                    " WHERE (e.name =:name" +
                    " OR e.code =:code)" +
                    " AND e.status != 'D'";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_ETHNICITY =
            " SELECT" +
                    " e.id as value," +
                    " e.name as label" +
                    " FROM Ethnicity e" +
                    " WHERE e.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_ETHNICITY =
            " SELECT" +
                    " e.id as value," +
                    " e.name as label" +
                    " FROM Ethnicity e" +
                    " WHERE e.status != 'D'";


    public static final Function<EthnicitySearchRequestDTO, String> QUERY_TO_SEARCH_ETHNICITY = (
            ethnicitySearchRequestDTO -> {

                String query =
                        " SELECT" +
                                " e.id as id," +
                                " e.name as name," +
                                " e.code as code," +
                                " e.status as status" +
                                " FROM Ethnicity e" ;

       return (query + GET_WHERE_CLAUSE_FOR_SEARCH(ethnicitySearchRequestDTO));
});

private static String GET_WHERE_CLAUSE_FOR_SEARCH(EthnicitySearchRequestDTO searchRequestDTO) {
        String query = " WHERE e.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
        query += " AND e.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
        query += " AND e.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
        query += " AND e.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
        query += " AND e.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY e.id DESC";

        return query;
        }

    public final static String CHECK_IF_ETHNICITY_NAME_AND_CODE_EXISTS =
            "SELECT" +
                    " e.name, " +
                    " e.code " +
                    " from" +
                    " Ethnicity e" +
                    " Where" +
                    " e.id!=:id" +
                    " AND" +
                    " (e.name =:name" +
                    " OR e.code =:code)" +
                    " AND e.status != 'D'";

}
