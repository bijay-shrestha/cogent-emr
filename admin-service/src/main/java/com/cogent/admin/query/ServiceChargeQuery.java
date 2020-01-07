package com.cogent.admin.query;

import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 11/13/19
 */
public class ServiceChargeQuery {

    public final static String FETCH_SERVICE_CHARGE_COUNT_BY_SERVICE_ID_AND_BILLING_MODE_ID =
            "SELECT" +
                    " COUNT(sc.id)" +
                    " FROM" +
                    " service_charge sc" +
                    " LEFT JOIN service_charge_billing_modes scbm ON scbm.service_charge_id=sc.id" +
                    " WHERE" +
                    " sc.service_id=:serviceId" +
                    " AND scbm.billing_modes_id=:billingModeId" +
                    " AND sc.status!='D'";

    public final static String CHECK_IF_SERVICE_ALREADY_EXISTS =
            "SELECT" +
                    " COUNT(sc.id)" +
                    " FROM" +
                    " service_charge sc" +
                    " LEFT JOIN service_charge_billing_modes scbm ON scbm.service_charge_id=sc.id" +
                    " WHERE" +
                    " sc.service_id=:serviceId" +
                    " AND scbm.billing_modes_id=:billingModeId" +
                    " AND sc.id!=:id" +
                    " AND sc.status!='D'";


    public static final Function<ServiceChargeSearchRequestDTO, String> QUERY_TO_SEARCH_SERVICE_CHARGE = (
            searchRequestDTO -> {

                String query =
                        "SELECT" +
                                " s.name as name," +
                                " s.code as code," +
                                " sc.price as price," +
                                " bm.name as billingMode," +
                                " sc.status as status" +
                                " FROM" +
                                " service_charge sc" +
                                " LEFT JOIN service s ON s.id=sc.service_id" +
                                " LEFT JOIN service_charge_billing_modes scbm ON scbm.service_charge_id=sc.id" +
                                " LEFT JOIN billing_mode bm On bm.id=scbm.billing_modes_id" +
                                " LEFT JOIN department d On d.id=s.department_id" +
                                " LEFT JOIN sub_department sd On sd.id=s.sub_department_id" +
                                " LEFT JOIN service_type st On st.id=s.service_type_id";

                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));

            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(ServiceChargeSearchRequestDTO searchRequestDTO) {

        String query = " WHERE sc.status != 'D'";


        if (!ObjectUtils.isEmpty(searchRequestDTO.getServiceId())) {
            query += " AND s.id=" + searchRequestDTO.getServiceId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getDepartmentId())) {
            query += " AND d.id=" + searchRequestDTO.getDepartmentId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getSubDepartmentId())) {
            query += " AND sd.id=" + searchRequestDTO.getSubDepartmentId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getBillModeId())) {
            query += " AND bm.id=" + searchRequestDTO.getBillModeId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getServiceTypeId())) {
            query += " AND st.id=" + searchRequestDTO.getServiceTypeId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getServiceName())) {
            query += " AND s.name='" + searchRequestDTO.getServiceName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getServiceCode())) {
            query += " AND s.code='" + searchRequestDTO.getServiceCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND sc.status='" + searchRequestDTO.getStatus() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getPrice())) {
            query += " AND sc.price='" + searchRequestDTO.getPrice() + "'";
        }

        query += " ORDER BY sc.id DESC";

        return query;

    }

    public final static String FETCH_SERVICE_CHARGE_DETAILS =
            "SELECT" +
                    " s.name," +
                    " s.code," +
                    " d.name as department," +
                    " sd.name as subDepartment," +
                    " bm.name as billingMode," +
                    " st.name as serviceType," +
                    " sc.price," +
                    " sc.status," +
                    " sc.remarks" +
                    " FROM" +
                    " service_charge sc" +
                    " LEFT JOIN service s ON s.id=sc.service_id" +
                    " LEFT JOIN service_charge_billing_modes scbm ON scbm.service_charge_id=sc.id" +
                    " LEFT JOIN billing_mode bm On bm.id=scbm.billing_modes_id" +
                    " LEFT JOIN department d On d.id=s.department_id" +
                    " LEFT JOIN sub_department sd On sd.id=s.sub_department_id" +
                    " LEFT JOIN service_type st On st.id=s.service_type_id" +
                    " WHERE" +
                    " sc.id=:id" +
                    " AND" +
                    " sc.status!='D'";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_SERVICE_CHARGE =
            "SELECT" +
                    " sc.id as value," +
                    " s.name as label," +
                    " bm.name as billingMode," +
                    " sc.price as price" +
                    " FROM service_charge sc" +
                    " LEFT JOIN service s ON s.id=sc.service_id" +
                    " LEFT JOIN service_charge_billing_modes scbm ON scbm.service_charge_id=sc.id" +
                    " LEFT JOIN billing_mode bm ON bm.id=scbm.billing_modes_id" +
                    " WHERE sc.status='Y'";

    public static final String QUERY_FOR_DROP_DOWN_SERVICE_CHARGE =
            "SELECT" +
                    " sc.id as value," +
                    " s.name as label," +
                    " bm.name as billingMode," +
                    " sc.price as price" +
                    " FROM service_charge sc" +
                    " LEFT JOIN service s ON s.id=sc.service_id" +
                    " LEFT JOIN service_charge_billing_modes scbm ON scbm.service_charge_id=sc.id" +
                    " LEFT JOIN billing_mode bm ON bm.id=scbm.billing_modes_id" +
                    " WHERE sc.status!='D'";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_SERVICE_CHARGE_BY_DEPARTMENT_ID =
            "SELECT " +
                    " sc.id as value," +
                    " sc.service.name as label" +
                    " from" +
                    " ServiceCharge sc" +
                    " where" +
                    " sc.status = 'Y'" +
                    " AND sc.service.department.id =:departmentId";

    public static final String QUERY_FOR_DROP_DOWN_SERVICE_CHARGE_BY_DEPARTMENT_ID =
            "SELECT " +
                    " sc.id as value," +
                    " sc.service.name as label" +
                    " from" +
                    " ServiceCharge sc" +
                    " where" +
                    " sc.status != 'D'" +
                    " AND sc.service.department.id =:departmentId";

    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_SERVICE_CHARGE_BY_BILLING_MODE_ID =
           "SELECT " +
                   " s.id as value," +
                   " s.name as label," +
                   " sc.price as price" +
                   " FROM service_charge_billing_modes scbm" +
                   " LEFT JOIN service_charge sc ON sc.id= scbm.service_charge_id" +
                   " LEFT JOIN service s ON s.id=sc.service_id" +
                   " WHERE billing_modes_id=:billingModeId";
    public static final String QUERY_TO_FETCH_LIST_BY_BILLING_MODE_ID_AND_SERVICE_ID =
            "SELECT" +
                    " sc.id," +
                    " sc.price," +
                    " sc.remarks," +
                    " sc.status," +
                    " sc.service_id" +
                    " FROM" +
                    " service_charge sc" +
                    " LEFT JOIN service_charge_billing_modes sb ON sb.service_charge_id = sc.id" +
                    " WHERE sb.billing_modes_id = :billingModeId" +
                    " AND sc.service_id = :serviceId";

    public static final String QUERY_TO_FETCH_SERVICE_BY_SERVICE_ID =
            "SELECT" +
                    " s.id as id," +
                    " s.code as code," +
                    " s.name as name," +
                    " s.remarks as remarks," +
                    " s.status as status," +
                    " s.department as department," +
                    " s.serviceType as serviceType," +
                    " s.subDepartment as subDepartment" +
                    " FROM" +
                    " Service s" +
                    " WHERE s.id=:id" +
                    " AND s.status!='D'";

}
