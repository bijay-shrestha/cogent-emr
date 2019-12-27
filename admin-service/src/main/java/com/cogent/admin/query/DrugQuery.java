package com.cogent.admin.query;

import com.cogent.admin.dto.request.drug.DrugSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class DrugQuery {
    public final static String FETCH_DRUG_BY_NAME_AND_CODE =
            "SELECT " +
                    " d.name," +                        //[0]
                    " d.code" +                         //[1]
                    " FROM Drug d" +
                    " WHERE (d.name =:name" +
                    " OR d.code =:code)" +
                    " AND d.status != 'D'";

    public final static String FETCH_DROP_DOWN_LIST =
            "SELECT " +
                    " d.id as value," +                        //[0]
                    " d.name as label" +                         //[1]
                    " FROM Drug d" +
                    " WHERE d.status != 'D'";


    public final static String FETCH_ACTIVE_DROP_DOWN_LIST =
            "SELECT " +
                    " d.id as value," +                        //[0]
                    " d.name as label" +                         //[1]
                    " FROM Drug d" +
                    " WHERE d.status = 'Y'";

    public final static String FETCH_DRUG_DETAILS =
            "SELECT" +
                    " d.name as name," +
                    " d.code as code," +
                    " d.status as status," +
                    " d.remarks as remarks" +
                    " FROM Drug d" +
                    " WHERE d.id =:id";

    public static final Function<DrugSearchRequestDTO, String> QUERY_TO_SEARCH_DRUG = (
            searchRequestDTO -> {

                String query =
                        " SELECT" +
                                " d.id as id," +
                                " d.name as name," +
                                " d.code as code," +
                                " d.status as status" +
                                " FROM Drug d";
                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));
            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(DrugSearchRequestDTO searchRequestDTO) {
        String query = " WHERE d.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND d.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND d.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND d.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND d.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY d.id DESC";

        return query;
    }

    public final static String  CHECK_IF_DRUG_NAME_AND_CODE_EXISTS=
            "SELECT" +
                    " d.name, " +
                    " d.code " +
                    " from" +
                    " Drug d" +
                    " Where" +
                    " d.id!=:id" +
                    " AND" +
                    " (d.name =:name" +
                    " OR d.code =:code)" +
                    " AND d.status != 'D'";
}
