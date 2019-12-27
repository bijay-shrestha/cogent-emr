package com.cogent.admin.query;

import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class DistrictQuery {

    public final static String FETCH_DISTRICT_COUNT_BY_NAME =
            "SELECT " +
                    " count(d.id)" +
                    " FROM District d" +
                    " WHERE d.name=:name" +
                    " AND d.status != 'D'";

    public final static String FETCH_DROP_DOWN_LIST =
            "SELECT " +
                    " d.id as value," +
                    " d.name as label" +
                    " FROM District d" +
                    " WHERE d.status != 'D'";

    public final static String FETCH_ACTIVE_DROP_DOWN_LIST =
            "SELECT " +
                    " d.id as value," +
                    " d.name as label" +
                    " FROM District d" +
                    " WHERE d.status = 'Y'";

    public final static String FETCH_DETAIL_DISTRICT_DATA =
            "SELECT" +
                    " d.name as name," +
                    " d.status as status," +
                    " d.provinces.name as province," +
                    " d.remarks as remarks," +
                    " d.createdDate as createdDate , " +
                    " d.lastModifiedDate as lastModifiedDate," +
                    " d.createdById as createdById," +
                    " d.modifiedById as modifiedById" +
                    " FROM District d" +
                    " WHERE d.id =:id" +
                    " AND d.status != 'D'";

    public static final Function<DistrictSearchRequestDTO, String> QUERY_TO_SEARCH_DISTRICT = (
            searchRequestDTO -> {
                String query =
                        " SELECT" +
                                " d.id as id," +
                                " d.name as name," +
                                " d.status as status," +
                                " d.provinces.name as province" +
                                " FROM District d";

                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));
            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(DistrictSearchRequestDTO searchRequestDTO) {
        String query = " WHERE d.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND d.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND d.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND d.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY d.id DESC";

        return query;
    }


    public static final String QUERY_TO_FETCH_DISTRICT_BY_ID =
            "SELECT" +
                    " d.id as id," +
                    " d.name as name," +
                    " d.status as status," +
                    " d.provinces as provinces" +
                    " FROM District d" +
                    " WHERE d.id = :id" +
                    " AND d.status!='D'";


    public static final String QUERY_TO_FETCH_DISTRICT_BY_PROVINCES_ID =
            " SELECT" +
                    " d.id as id," +
                    " d.name as name," +
                    " d.status as status," +
                    " d.provinces as provinces" +
                    " FROM District d" +
                    " WHERE d.provinces.id = :id" +
                    " AND d.status !='D'";

    public static final String QUERY_TO_FETCH_DISTRICT_TO_DELETE_BY_PROVINCE_ID =
            " SELECT" +
                    " d.id as id," +
                    " d.status as status," +
                    " d.remarks as remarks" +
                    " FROM District d" +
                    " WHERE d.provinces.id = :id" +
                    " AND d.status !='D'";

    public static final String QUERY_TO_FETCH_DISTRICT =
            " SELECT" +
                    " d.id as id," +
                    " d.name as name," +
                    " d.status as status," +
                    " d.remarks as remarks," +
                    " d.provinces as provinces" +
                    " FROM District d" +
                    " WHERE d.id = :id" +
                    " AND d.status !='D'";


    public static final String QUERY_TO_FETCH_PROVINCE =
            " SELECT" +
                    " p " +
                    " FROM Provinces p" +
                    " WHERE p.id = :id" +
                    " AND p.status !='D'";


}
