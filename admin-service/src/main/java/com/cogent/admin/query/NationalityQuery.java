package com.cogent.admin.query;

import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi on 2019-09-19
 */
public class NationalityQuery {

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_NATIONALITY =
            " SELECT" +
                    " n.id as value," +
                    " n.name as label" +
                    " FROM Nationality n" +
                    " WHERE n.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_NATIONALITY =
            " SELECT" +
                    " n.id as value," +
                    " n.name as label" +
                    " FROM Nationality n" +
                    " WHERE n.status != 'D'";

    public static final Function<NationalitySearchRequestDTO, String> QUERY_TO_SEARCH_NATIONALITY = (
            searchRequestDTO -> {

                String query =
                        " SELECT" +
                                " n.id as id," +
                                " n.name as name," +
                                " n.status as status" +
                                " FROM Nationality n";

                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));
            });


    private static String GET_WHERE_CLAUSE_FOR_SEARCH(NationalitySearchRequestDTO searchRequestDTO) {
        String query = " WHERE n.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND n.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND n.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND n.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY n.id DESC";


        return query;

    }

    public final static String FETCH_NATIONALITY_DETAILS =
            "SELECT" +
                    " n.name as name," +
                    " n.status as status," +
                    " n.remarks as remarks" +
                    " FROM Nationality n" +
                    " WHERE n.id =:id" +
                    " AND n.status != 'D'";
}
