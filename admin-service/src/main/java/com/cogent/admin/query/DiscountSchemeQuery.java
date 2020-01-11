package com.cogent.admin.query;

import com.cogent.admin.dto.request.discountscheme.DiscountSchemeSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 11/11/19
 */
public class DiscountSchemeQuery {

    public final static String FETCH_DISCOUNT_SCHEME_BY_NAME_AND_CODE =
            "SELECT " +
                    " ds.name," +                        //[0]
                    " ds.code" +                         //[1]
                    " FROM DiscountScheme ds" +
                    " WHERE (ds.name =:name" +
                    " OR ds.code =:code)" +
                    " AND ds.status != 'D'";


    public static String QUERY_TO_SEARCH_DISCOUNT_SCHEME(DiscountSchemeSearchRequestDTO searchRequestDTO) {
        return
                "SELECT " +
                        SELECT_CLAUSE_TO_SEARCH_DISCOUNT_SCHEME +
                        " FROM " +
                        " discount_scheme ds" +
                        QUERY_TO_LEFT_JOIN +
                        GET_WHERE_CLAUSE_FOR_SEARCH.apply(searchRequestDTO);
    }


    private static final Function<DiscountSchemeSearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCH =
            (searchRequestDTO) -> {

                String query = " WHERE ds.status != 'D'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
                    query += " AND ds.id=" + searchRequestDTO.getId();
                }

                if (!ObjectUtils.isEmpty(searchRequestDTO.getDepartmentId())) {
                    query += " AND d.id=" + searchRequestDTO.getDepartmentId();
                }

                if (!ObjectUtils.isEmpty(searchRequestDTO.getDepartmentName())) {
                    query += " AND d.name= '" + searchRequestDTO.getDepartmentName() + "'";
                }

                if (!ObjectUtils.isEmpty(searchRequestDTO.getNetDiscount())) {
                    query += " AND ds.total_discount_percentage=" + searchRequestDTO.getNetDiscount();
                }

                if (!ObjectUtils.isEmpty(searchRequestDTO.getDiscountSchemeName())) {
                    query += " AND ds.name= '" + searchRequestDTO.getDiscountSchemeName() + "'";
                }

                if (!ObjectUtils.isEmpty(searchRequestDTO.getDiscountSchemeCode())) {
                    query += " AND ds.code= '" + searchRequestDTO.getDiscountSchemeCode() + "'";
                }

                if (!ObjectUtils.isEmpty(searchRequestDTO.getServiceId())) {
                    query += " AND s.id= " + searchRequestDTO.getServiceId();
                }

                if (!ObjectUtils.isEmpty(searchRequestDTO.getServiceName())) {
                    query += " AND s.name= '" + searchRequestDTO.getServiceName() + "'";
                }

                if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
                    query += " AND ds.status='" + searchRequestDTO.getStatus() + "'";
                }

                query += " GROUP BY ds.id";

                return query;
            };

    private static final String SELECT_CLAUSE_TO_SEARCH_DISCOUNT_SCHEME =
            " ds.name as name," +
                    " ds.code as code," +
                    " ds.total_discount_percentage as netDiscount," +
                    " ds.status as status," +
                    " Group_concat(DISTINCT d.name) as departmentName," +
                    " Group_concat(DISTINCT s.name) as serviceName," +
                    " Group_Concat(DISTINCT dds.discount_percentage) as departmentDiscount," +
                    " Group_concat(DISTINCT sd.discount_percentage) as serviceDiscountRate ";

    private static final String QUERY_TO_LEFT_JOIN =
            " LEFT JOIN department_discount dds ON dds.discountscheme_id=ds.id AND dds.status!='D'" +
                    " LEFT JOIN department d ON d.id=dds.department_id AND d.status!='D'" +
                    " LEFT JOIN service_discount sd ON sd.discountscheme_id=ds.id AND sd.status!='D'" +
                    " LEFT JOIN service s ON s.id=sd.service_id AND s.status!='D'";

    private static final String SELECT_CLAUSE_TO_FETCH_DISCOUNT_SCHEME_DETAILS =
            " ds.name as name," +
                    " ds.code as code," +
                    " ds.total_discount_percentage as netDiscount," +
                    " ds.status as status," +
                    " Group_concat(DISTINCT d.name) as departmentName," +
                    " Group_concat(DISTINCT s.name) as serviceName," +
                    " Group_Concat(DISTINCT dds.discount_percentage) as departmentDiscount," +
                    " Group_concat(DISTINCT sd.discount_percentage) as serviceDiscountRate ";


    public static final String QUERY_TO_GET_DISCOUNT_DETAILS =
            "SELECT " +
                    SELECT_CLAUSE_TO_FETCH_DISCOUNT_SCHEME_DETAILS +
                    " FROM " +
                    " discount_scheme ds" +
                    QUERY_TO_LEFT_JOIN +
                    " WHERE ds.status != 'D'" +
                    " AND ds.id=:id" +
                    " GROUP BY ds.id";


    public static final String QUERY_FOR_DROP_DOWN_BY_DISCOUNT_SCHEME =
            "SELECT " +
                    " ds.id as value," +
                    " ds.name as label," +
                    " ds.netDiscount as discountPercentage " +
                    " FROM DiscountScheme ds" +
                    " WHERE ds.status !='D'" +
                    " AND ds.has_netDiscount=TRUE";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_BY_DISCOUNT_SCHEME =
            "SELECT " +
                    " ds.id as value," +
                    " ds.name as label," +
                    " ds.netDiscount as discountPercentage " +
                    " FROM DiscountScheme ds" +
                    " WHERE ds.status ='Y'" +
                    " AND ds.has_netDiscount=TRUE";

    public static final String QUERY_FOR_DROP_DOWN_BY_DEPARTMENT_ID =
            "SELECT " +
                    " dds.id as value," +
                    " ds.name as label," +
                    " dds.discount as discountPercentage " +
                    " FROM DepartmentDiscount dds" +
                    " LEFT JOIN DiscountScheme ds ON ds.id=dds.discountScheme.id" +
                    " WHERE dds.status !='D'" +
                    " AND dds.department.id=:departmentId";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_BY_DEPARTMENT_ID =
            "SELECT " +
                    " dds.id as value," +
                    " ds.name as label," +
                    " dds.discount as discountPercentage " +
                    " FROM DepartmentDiscount dds" +
                    " LEFT JOIN DiscountScheme ds ON ds.id=dds.discountScheme.id" +
                    " WHERE dds.status ='Y'" +
                    " AND dds.department.id=:departmentId";

    public static final String QUERY_FOR_DROP_DOWN_BY_SERVICE_ID =
            "SELECT " +
                    " sd.id as value," +
                    " ds.name as label," +
                    " sd.discount as discountPercentage " +
                    " FROM ServiceDiscount sd" +
                    " LEFT JOIN DiscountScheme ds ON ds.id=sd.discountScheme.id" +
                    " WHERE sd.status !='D'" +
                    " AND sd.service.id=:serviceId";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_BY_SERVICE_ID =
            "SELECT " +
                    " sd.id as value," +
                    " ds.name as label," +
                    " sd.discount as discountPercentage " +
                    " FROM ServiceDiscount sd" +
                    " LEFT JOIN DiscountScheme ds ON ds.id=sd.discountScheme.id" +
                    " WHERE sd.status ='Y'" +
                    " AND sd.service.id=:serviceId";

    public final static String CHECK_IF_DISCOUNT_SCHEME_EXISTS =
            "SELECT" +
                    " ds.name, " +
                    " ds.code " +
                    " from" +
                    " DiscountScheme ds" +
                    " Where" +
                    " ds.id!=:id" +
                    " AND" +
                    " (ds.name =:name" +
                    " OR ds.code =:code)" +
                    " AND ds.status != 'D'";


}
