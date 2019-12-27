package com.cogent.admin.query;

import com.cogent.admin.dto.request.religion.ReligionSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class ReligionQuery {

    public static final String QUERY_TO_FIND_RELIGION_COUNT_BY_NAME =
            "SELECT COUNT(r.id) FROM Religion r WHERE r.name =:name AND r.status != 'D'";

    public static final String QUERY_TO_FIND_RELIGION_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(r.id) FROM Religion r WHERE r.id!= :id AND r.name =:name AND r.status != 'D'";

    public static final String QUERY_TO_FETCH_RELIGION_FOR_DROPDOWN =
            " SELECT" +
                    " r.id as value," +
                    " r.name as label" +
                    " FROM" +
                    " Religion r" +
                    " WHERE r.status ='Y'";

    public static String QUERY_TO_SEARCH_RELIGION(ReligionSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " r.id as id," +
                " r.name as name," +
                " r.status as status," +
                " r.remarks as remarks" +
                " FROM" +
                " Religion r" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_RELIGION.apply(searchRequestDTO);
    }

    private static Function<ReligionSearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCHING_RELIGION =
            (searchRequestDTO) -> {
                String whereClause = " WHERE r.status!='D'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
                    whereClause += " AND r.status='" + searchRequestDTO.getStatus() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
                    whereClause += " AND r.name='" + searchRequestDTO.getName() + "'";

                whereClause += " ORDER BY r.id DESC";

                return whereClause;
            };


}
