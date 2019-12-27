package com.cogent.admin.query;

import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class DepartmentQuery {

    public static final String GET_DEPARTMENT_NAME_AND_ID =
            "SELECT" +
                    " d.id as value ," +
                    " d.name as label" +
                    " FROM Department d" +
                    " WHERE d.status != 'D'" +
                    " ORDER BY d.id DESC";

    public static final String GET_ACTIVE_DEPARTMENT_NAME_AND_ID =
            "SELECT" +
                    " d.id as value," +
                    " d.name as label" +
                    " FROM Department d" +
                    " WHERE d.status = 'Y'" +
                    " ORDER BY d.id DESC";

    public static final Function<DepartmentSearchRequestDTO, String> QUERY_TO_SEARCH_DEPARTMENT = (departmentSearchRequestDTO -> {

        String query = " SELECT " +
                " d.id as id," +
                " d.name as name," +
                " d.code as code," +
                " d.status as status," +
                " d.remarks as remarks" +
                " FROM Department d";
        return (query + GET_WHERE_CLAUSE_FOR_SEARCH(departmentSearchRequestDTO));
    });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(DepartmentSearchRequestDTO searchRequestDTO) {
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

    public static final String CREATE_QUERY_TO_FETCH_DEPARTMENT =
            "SELECT " +
                    "d.id," +
                    "d.name," +
                    "d.code," +
                    "d.status" +
                    " FROM Department d" +
                    " WHERE d.status !='D'";

    public final static String FETCH_DEPARTMENT_BY_NAME_AND_CODE =
            "SELECT " +
                    " d.name," +                        //[0]
                    " d.code" +                         //[1]
                    " FROM Department d" +
                    " WHERE (d.name =:name" +
                    " OR d.code =:code)" +
                    " AND d.status != 'D'";

    public final static String FETCH_DEPARTMENT_DETAILS =
            "SELECT" +
                    " d.name as name," +
                    " d.code as code," +
                    " d.status as status," +
                    " d.remarks as remarks," +
                    " d.createdDate as createdDate , " +
                    " d.lastModifiedDate as lastModifiedDate," +
                    " d.createdById as createdById," +
                    " d.modifiedById as modifiedById" +
                    " FROM Department d" +
                    " WHERE d.id =:id" +
                    " AND d.status != 'D'";

    public final static String CHECK_IF_DEPARTMENT_EXISTS =
            "SELECT" +
                    " d.name, " +
                    " d.code " +
                    " FROM" +
                    " Department d" +
                    " Where" +
                    " d.id!=:id" +
                    " AND" +
                    " (d.name =:name" +
                    " OR d.code =:code)" +
                    " AND d.status != 'D'";
}
