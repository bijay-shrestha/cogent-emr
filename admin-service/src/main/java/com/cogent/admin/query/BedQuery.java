package com.cogent.admin.query;

import com.cogent.admin.dto.request.bed.BedSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 11/1/19
 */
public class BedQuery {
    public final static String FETCH_BED_BY_NAME_AND_CODE =
            "SELECT " +
                    " b.name," +
                    " b.code" +
                    " FROM Bed b" +
                    " WHERE (b.name =:name" +
                    " OR b.code =:code)" +
                    " AND b.status != 'D'";

    public static final Function<BedSearchRequestDTO, String> QUERY_TO_SEARCH_BED = (
            searchRequestDTO -> {

                String query =
                        " SELECT " +
                                " b.name as name," +
                                " b.code as code," +
                                " b.status as status," +
                                " b.remarks as remarks" +
                                " FROM Bed b";
                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));

            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(BedSearchRequestDTO searchRequestDTO) {

        String query = " WHERE b.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND b.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND b.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND b.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND b.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += "ORDER BY b.id DESC";

        return query;

    }

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_BED =
            " SELECT" +
                    " b.id as value," +
                    " b.name as label" +
                    " FROM Bed b" +
                    " WHERE b.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_BED =
            " SELECT" +
                    " b.id as value," +
                    " b.name as label" +
                    " FROM Bed b" +
                    " WHERE b.status != 'D'";

    public final static String FETCH_BED_DETAILS =
            "SELECT" +
                    " b.name as name," +
                    " b.code as code," +
                    " b.status as status," +
                    " b.remarks as remarks" +
                    " FROM Bed b" +
                    " WHERE b.id =:id" +
                    " AND b.status != 'D'" +
                    " GROUP BY b.id ";

    public final static String CHECK_BED_NAME_AND_CODE_IF_EXIST =
            "SELECT" +
                    " b.name, " +
                    " b.code " +
                    " from" +
                    " Bed b" +
                    " Where" +
                    " b.id!=:id" +
                    " AND" +
                    " (b.name =:name" +
                    " OR b.code =:code)" +
                    " AND b.status != 'D'";
}
