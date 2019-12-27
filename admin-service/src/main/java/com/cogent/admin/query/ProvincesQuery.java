package com.cogent.admin.query;

import com.cogent.admin.dto.request.provinces.ProvincesSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class ProvincesQuery {
    public final static String FETCH_PROVINCE_COUNT_BY_NAME =
            "SELECT " +
                    " count (p.id)" +
                    " FROM Provinces p" +
                    " WHERE p.name=:name" +
                    " AND p.status != 'D'";

    public final static String FETCH_DROP_DOWN_LIST =
            "SELECT " +
                    " p.id as value," +                        //[0]
                    " p.name as label" +                         //[1]
                    " FROM Provinces p" +
                    " WHERE p.status != 'D'";

    public final static String FETCH_ACTIVE_DROP_DOWN_LIST =
            "SELECT " +
                    " p.id as value," +                        //[0]
                    " p.name as label" +                         //[1]
                    " FROM Provinces p" +
                    " WHERE p.status = 'Y'";

    public static final Function<ProvincesSearchRequestDTO, String> QUERY_TO_SEARCH_PROVINCES = (
            searchRequestDTO -> {

                String query =
                        " SELECT" +
                                " p.id as id," +
                                " p.name as name," +
                                " p.status as status" +
                                " FROM Provinces p";

                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));

            });


    private static String GET_WHERE_CLAUSE_FOR_SEARCH(ProvincesSearchRequestDTO searchRequestDTO) {
        String query = " WHERE p.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND p.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND p.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND p.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY p.id DESC";

        return query;

    }

    public final static String FETCH_DETAIL_PROVINCES_DATA =
            "SELECT" +
                    " p.name as name," +
                    " p.status as status," +
                    " p.remarks as remarks," +
                    " p.createdDate as createdDate , " +
                    " p.lastModifiedDate as lastModifiedDate," +
                    " p.createdById as createdById," +
                    " p.modifiedById as modifiedById" +
                    " FROM Provinces p" +
                    " WHERE p.id =:id";
}
