package com.cogent.admin.query;

import com.cogent.admin.dto.request.billingmode.BillingModeSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 12/4/19
 */
public class BillingModeQuery {
    public final static String FETCH_BILLING_MODE_BY_NAME_AND_CODE =
            "SELECT " +
                    " bm.name," +
                    " bm.code" +
                    " FROM BillingMode bm" +
                    " WHERE (bm.name =:name" +
                    " OR bm.code =:code)" +
                    " AND bm.status != 'D'";

    public static final Function<BillingModeSearchRequestDTO, String> QUERY_TO_SEARCH_BILLING_MODE = (
            searchRequestDTO -> {

                String query =
                        " SELECT " +
                                " bm.name as name," +
                                " bm.code as code," +
                                " bm.status as status," +
                                " bm.remarks as remarks," +
                                " bm.description as description" +
                                " FROM BillingMode bm";
                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));

            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(BillingModeSearchRequestDTO searchRequestDTO) {

        String query = " WHERE bm.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND bm.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND bm.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND bm.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND bm.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY bm.id DESC";

        return query;

    }

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_BILLING_MODE =
            " SELECT" +
                    " bm.id as value," +
                    " bm.name as label" +
                    " FROM BillingMode bm" +
                    " WHERE bm.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_BILLING_MODE =
            " SELECT" +
                    " bm.id as value," +
                    " bm.name as label" +
                    " FROM BillingMode bm" +
                    " WHERE bm.status != 'D'";

    public final static String FETCH_BILLING_MODE_DETAILS =
            "SELECT" +
                    " bm.name as name," +
                    " bm.code as code," +
                    " bm.status as status," +
                    " bm.remarks as remarks," +
                    " bm.description as description" +
                    " FROM BillingMode bm" +
                    " WHERE bm.id =:id" +
                    " AND bm.status != 'D'" +
                    " GROUP BY bm.id ";

    public final static String CHECK_IF_BILLING_MODE_EXISTS =
            "SELECT" +
                    " bm.name, " +
                    " bm.code " +
                    " FROM BillingMode bm" +
                    " WHERE" +
                    " bm.id!=:id" +
                    " AND" +
                    " (bm.name =:name" +
                    " OR bm.code =:code)" +
                    " AND bm.status != 'D'";

}
