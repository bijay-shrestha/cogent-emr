package com.cogent.admin.query;

import com.cogent.admin.dto.request.unit.UnitSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 10/2/19
 */
public class UnitQuery {

    public final static String FETCH_UNIT_BY_NAME_AND_CODE =
            "SELECT " +
                    " u.name," +
                    " u.code" +
                    " FROM Unit u" +
                    " WHERE (u.name =:name" +
                    " OR u.code =:code)" +
                    " AND u.status != 'D'";

    public static final Function<UnitSearchRequestDTO, String> QUERY_TO_SEARCH_UNIT = (
            searchRequestDTO -> {

                String query =
                        " SELECT" +
                                " u.name as name," +
                                " u.code as code," +
                                " u.status as status" +
                                " FROM Unit u";


                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));
            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(UnitSearchRequestDTO searchRequestDTO) {
        String query = " WHERE u.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND u.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND u.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND u.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND u.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY u.id DESC";


        return query;

    }

    public final static String FETCH_UNIT_DETAILS =
            "SELECT" +
                    " u.name as name," +
                    " u.code as code," +
                    " u.status as status," +
                    " u.remarks as remarks" +
                    " FROM Unit u" +
                    " WHERE u.id =:id" +
                    " AND u.status != 'D'";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_UNIT =
            " SELECT" +
                    " u.id as value," +
                    " u.name as label" +
                    " FROM Unit u" +
                    " WHERE u.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_UNIT =
            " SELECT" +
                    " u.id as value," +
                    " u.name as label" +
                    " FROM Unit u" +
                    " WHERE u.status != 'D'";

    public final static String CHECK_IF_UNIT_NAME_AND_CODE_EXISTS =
            "SELECT" +
                    " u.name, " +
                    " u.code " +
                    " FROM" +
                    " Unit u" +
                    " WHERE" +
                    " u.id!=:id" +
                    " AND" +
                    " (u.name =:name" +
                    " OR u.code =:code)" +
                    " AND u.status != 'D'";
}
