package com.cogent.admin.query;

import com.cogent.admin.dto.request.referrer.ReferrerSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Rupak
 */
public class ReferrerQuery {

    public static final String QUERY_TO_FIND_REFERRER_COUNT_BY_NAME =
            "SELECT COUNT(r.id) FROM Referrer r WHERE r.name =:name AND r.status != 'D'";

    public static final String QUERY_TO_FIND_REFERRER_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(r.id) FROM Referrer r WHERE r.id!= :id AND r.name =:name AND r.status != 'D'";

    public static final String QUERY_TO_FETCH_REFERRER_FOR_DROPDOWN =
            " SELECT" +
                    " r.id as value," +
                    " r.name as label" +
                    " FROM" +
                    " Referrer r" +
                    " WHERE r.status ='Y'";

    public static String QUERY_TO_SEARCH_REFERRER(ReferrerSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " r.id as id," +
                " r.name as name," +
                " r.status as status," +
                " r.remarks as remarks" +
                " FROM" +
                " Referrer r" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_REFERRER.apply(searchRequestDTO);
    }

    private static Function<ReferrerSearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCHING_REFERRER =
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
