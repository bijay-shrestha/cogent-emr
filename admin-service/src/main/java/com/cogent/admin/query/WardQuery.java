package com.cogent.admin.query;

import com.cogent.admin.dto.request.ward.WardSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 10/2/19
 */
public class WardQuery {

    public final static String FETCH_WARD_BY_NAME_AND_CODE =
            "SELECT " +
                    " w.name," +
                    " w.code" +
                    " FROM Ward w" +
                    " WHERE (w.name =:name" +
                    " OR w.code =:code)" +
                    " AND w.status != 'D'";

    public static final Function<WardSearchRequestDTO, String> QUERY_TO_SEARCH_WARD = (
            searchRequestDTO -> {

                String query =
                        " SELECT " +
                                " w.name," +
                                " w.code," +
                                " w.status," +
                                " w.remarks," +
                                " w.has_Unit," +
                                " GROUP_CONCAT(IFNULL(u.name,'-'))" +
                                " FROM ward w" +
                                " LEFT JOIN ward_unit wu ON w.id=wu.ward_id AND wu.status !='D'" +
                                " LEFT JOIN unit u ON u.id=wu.unit_id";

                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));

            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(WardSearchRequestDTO searchRequestDTO) {

        String query = " WHERE w.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND w.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND w.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND w.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND w.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " GROUP BY w.id " +
                "ORDER BY w.id DESC";

        return query;

    }


    public final static String FETCH_WARD_DETAILS =
            "SELECT" +
                    " w.name," +
                    " w.code," +
                    " w.status," +
                    " w.remarks," +
                    " w.has_Unit," +
                    " GROUP_CONCAT(IFNULL(u.name,'-'))" +
                    " FROM ward w" +
                    " LEFT JOIN ward_unit wu ON w.id=wu.ward_id AND wu.status !='D'" +
                    " LEFT JOIN unit u ON u.id=wu.unit_id" +
                    " WHERE w.id =:id" +
                    " AND w.status != 'D'" +
                    " GROUP BY w.id ";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_WARD =
            " SELECT" +
                    " w.id as value," +
                    " w.name as label" +
                    " FROM Ward w" +
                    " WHERE w.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_WARD =
            " SELECT" +
                    " w.id as value," +
                    " w.name as label" +
                    " FROM Ward w" +
                    " WHERE w.status != 'D'";

    public final static String CHECK_IF_WARD_NAME_AND_CODE_EXISTS =
            "SELECT" +
                    " w.name, " +
                    " w.code " +
                    " FROM" +
                    " Ward w" +
                    " WHERE" +
                    " w.id!=:id" +
                    " AND" +
                    " (w.name =:name" +
                    " OR w.code =:code)" +
                    " AND w.status != 'D'";
}
