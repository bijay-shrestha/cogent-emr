package com.cogent.admin.query;

import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 12/10/19
 */
public class RegisteredBankQuery {
    public final static String FETCH_REGISTERED_BANK_BY_NAME_AND_CODE =
            "SELECT " +
                    " rb.name," +
                    " rb.code" +
                    " FROM RegisteredBank rb" +
                    " WHERE (rb.name =:name" +
                    " OR rb.code =:code)" +
                    " AND rb.status != 'D'";

    public static final Function<RegisteredBankSearchRequestDTO, String> QUERY_TO_SEARCH_REGISTERED_BANK = (
            searchRequestDTO -> {

                String query =
                        " SELECT" +
                                " rb.name as name," +
                                " rb.code as code," +
                                " rb.status as status," +
                                " rb.contact as contact," +
                                " rb.address as address," +
                                " rb.swiftCode as swiftCode" +
                                " FROM RegisteredBank rb";
                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));

            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(RegisteredBankSearchRequestDTO searchRequestDTO) {

        String query = " WHERE rb.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND rb.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND rb.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND rb.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getAddress())) {
            query += " AND rb.address='" + searchRequestDTO.getAddress() + "'";
        }
        if (!ObjectUtils.isEmpty(searchRequestDTO.getContact())) {
            query += " AND rb.contact='" + searchRequestDTO.getContact() + "'";
        }
        if (!ObjectUtils.isEmpty(searchRequestDTO.getSwiftCode())) {
            query += " AND rb.swiftCode='" + searchRequestDTO.getSwiftCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND rb.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY rb.id DESC";

        return query;

    }

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_REGISTERED_BANK =
            " SELECT" +
                    " rb.id as value," +
                    " rb.name as label" +
                    " FROM RegisteredBank rb" +
                    " WHERE rb.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_REGISTERED_BANK =
            " SELECT" +
                    " rb.id as value," +
                    " rb.name as label" +
                    " FROM RegisteredBank rb" +
                    " WHERE rb.status != 'D'";

    public final static String FETCH_REGISTERED_BANK_DETAILS =
            "SELECT" +
                    " rb.name as name," +
                    " rb.code as code," +
                    " rb.status as status," +
                    " rb.contact as contact," +
                    " rb.address as address," +
                    " rb.swiftCode as swiftCode," +
                    " rb.remarks as remarks" +
                    " FROM RegisteredBank rb" +
                    " WHERE rb.id =:id" +
                    " AND rb.status != 'D'" +
                    " GROUP by rb.id ";

    public final static String CHECK_IF_REGISTERED_BANK_EXISTS =
            "SELECT" +
                    " rb.name, " +
                    " rb.code " +
                    " FROM RegisteredBank rb" +
                    " WHERE" +
                    " rb.id!=:id" +
                    " AND" +
                    " (rb.name =:name" +
                    " OR rb.code =:code)" +
                    " AND rb.status != 'D'";

}
