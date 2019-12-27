package com.cogent.admin.query;

import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi on 2019-09-19
 */
public class InsuranceCompanyQuery {

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_INSURANCE_COMPANY =
            " SELECT" +
                    " ic.id as value," +
                    " ic.name as label" +
                    " FROM InsuranceCompany ic" +
                    " WHERE ic.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_INSURANCE_COMPANY =
            " SELECT" +
                    " ic.id as value," +
                    " ic.name as label" +
                    " FROM InsuranceCompany ic" +
                    " WHERE ic.status != 'D'";

    public static final Function<InsuranceCompanySearchRequestDTO, String> QUERY_TO_SEARCH_INSURANCE_COMPANY = (
            searchRequestDTO -> {

                String query =
                        " SELECT" +
                                " ic.id as id," +
                                " ic.name as name," +
                                " ic.status as status" +
                                " FROM InsuranceCompany ic";

                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));
            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(InsuranceCompanySearchRequestDTO searchRequestDTO) {
        String query = " WHERE ic.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND ic.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND ic.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND ic.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY ic.id DESC";

        return query;

    }


    public final static String FETCH_INSURANCE_COMPANY_DETAILS =
            "SELECT" +
                    " ic.name as name," +
                    " ic.status as status," +
                    " ic.remarks as remarks" +
                    " FROM InsuranceCompany ic" +
                    " WHERE ic.id =:id" +
                    " AND ic.status != 'D'";
}
