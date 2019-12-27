package com.cogent.admin.query;

import com.cogent.admin.dto.request.title.TitleSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class TitleQuery {
    public static final String QUERY_TO_FIND_TITLE_COUNT_BY_NAME =
            "SELECT COUNT(t.id) FROM Title t WHERE t.name =:name AND t.status != 'D'";

    public static final String QUERY_TO_FIND_TITLE_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(t.id) FROM Title t WHERE t.id!= :id AND t.name =:name AND t.status != 'D'";

    public static final String QUERY_TO_FETCH_TITLE_FOR_DROPDOWN =
            " SELECT" +
                    " t.id as value," +
                    " t.name as label" +
                    " FROM" +
                    " Title t" +
                    " WHERE t.status ='Y'";

    public static String QUERY_TO_SEARCH_TITLE(TitleSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " t.id as id," +
                " t.name as name," +
                " t.status as status," +
                " t.remarks as remarks" +
                " FROM" +
                " Title t" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_TITLE.apply(searchRequestDTO);
    }

    private static Function<TitleSearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCHING_TITLE =
            (searchRequestDTO) -> {
                String whereClause = " WHERE t.status!='D'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
                    whereClause += " AND t.status='" + searchRequestDTO.getStatus() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
                    whereClause += " AND t.name='" + searchRequestDTO.getName() + "'";

                whereClause += " ORDER BY t.id DESC";

                return whereClause;
            };

}
