package com.cogent.admin.query;

import com.cogent.admin.dto.request.service.ServiceSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class ServiceQuery {
    public final static String FETCH_SERVICE_BY_NAME_AND_CODE =
            "SELECT " +
                    " s.name," +                        //[0]
                    " s.code" +                         //[1]
                    " FROM Service s" +
                    " WHERE (s.name =:name" +
                    " OR s.code =:code)" +
                    " AND s.status != 'D'";

    public final static String FETCH_DROP_DOWN_LIST =
            "SELECT " +
                    " s.id as value," +                        //[0]
                    " s.name as label" +                         //[1]
                    " FROM Service s" +
                    " WHERE s.status != 'D'" +
                    " AND s.department.id=: departmentId";


    public final static String FETCH_ACTIVE_DROP_DOWN_LIST =
            "SELECT " +
                    " s.id as value," +                        //[0]
                    " s.name as label" +                         //[1]
                    " FROM Service s" +
                    " WHERE s.status = 'Y'"+
                    " AND s.department.id=: departmentId";

    public final static String FETCH_SERVICE_DETAILS =
            "SELECT" +
                    " s.name as name," +
                    " s.code as code," +
                    " s.status as status," +
                    " s.remarks as remarks," +
                    " d.name as departmentName," +
                    " sd.name as subDepartmentName," +
                    " st.name as ServiceTypeName" +
                    " FROM Service s" +
                    " LEFT JOIN ServiceType st ON st.id=s.serviceType.id" +
                    " LEFT JOIN SubDepartment sd ON sd.id=s.subDepartment.id" +
                    " LEFT JOIN Department d ON d.id=s.department.id" +
                    " WHERE s.id =:id" +
                    " AND s.status !='D'";

    public static final Function<ServiceSearchRequestDTO, String> QUERY_TO_SEARCH_SERVICE = (
            searchRequestDTO -> {

                String query =
                        " SELECT" +
                                " s.id as id," +
                                " s.name as name," +
                                " s.code as code," +
                                " s.status as status," +
                                " s.remarks as remarks" +
                                " FROM Service s";
                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));
            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(ServiceSearchRequestDTO searchRequestDTO) {
        String query = " WHERE s.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND s.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND s.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND s.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND s.status='" + searchRequestDTO.getStatus() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getDepartmentId())) {
            query += " AND s.department.id=" + searchRequestDTO.getDepartmentId() ;
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getSubDepartmentId())) {
            query += " AND s.subDepartment.id=" + searchRequestDTO.getSubDepartmentId() ;
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getServiceTypeId())) {
            query += " AND s.serviceType.id=" + searchRequestDTO.getServiceTypeId();
        }

        query += " ORDER BY s.id DESC";

        return query;
    }

    public final static String CHECK_IF_SERVICE_NAME_AND_CODE_EXISTS =
            "SELECT" +
                    " s.name," +                        //[0]
                    " s.code" +
                    " FROM Service s" +
                    " Where" +
                    " s.id!=:id" +
                    " AND" +
                    " (s.name =:name" +
                    " OR s.code =:code)" +
                    " AND s.status != 'D'";
}
