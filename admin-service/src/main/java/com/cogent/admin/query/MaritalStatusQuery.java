package com.cogent.admin.query;

import com.cogent.admin.dto.request.maritalStatus.MaritalStatusSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class MaritalStatusQuery {
    public static final String QUERY_TO_FIND_MARITAL_STATUS_COUNT_BY_NAME =
            "SELECT COUNT(m.id) FROM MaritalStatus m WHERE m.name =:name AND m.status != 'D'";

    public static final String QUERY_TO_FIND_MARITAL_STATUS_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(m.id) FROM MaritalStatus m WHERE m.id!= :id AND m.name =:name AND m.status != 'D'";

    public static final String QUERY_TO_FETCH_MARITAL_STATUS_FOR_DROPDOWN =
            " SELECT" +
                    " m.id as value," +
                    " m.name as label" +
                    " FROM" +
                    " MaritalStatus m" +
                    " WHERE m.status ='Y'";

    public static String QUERY_TO_SEARCH_MARITAL_STATUS(MaritalStatusSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " m.id as id," +
                " m.name as name," +
                " m.status as status," +
                " m.remarks as remarks" +
                " FROM" +
                " MaritalStatus m" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_MARITAL_STATUS.apply(searchRequestDTO);
    }

    private static Function<MaritalStatusSearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCHING_MARITAL_STATUS =
            (searchRequestDTO) -> {
                String whereClause = " WHERE m.status!='D'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
                    whereClause += " AND m.status='" + searchRequestDTO.getStatus() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
                    whereClause += " AND m.name='" + searchRequestDTO.getName() + "'";

                whereClause += " ORDER BY m.id DESC";

                return whereClause;
            };

}
