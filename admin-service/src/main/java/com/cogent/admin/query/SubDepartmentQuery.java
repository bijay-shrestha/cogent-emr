package com.cogent.admin.query;

import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

public class SubDepartmentQuery {

    public static final String QUERY_TO_FETCH_SUB_DEPARTMENT_BY_DEPARTMENT_ID =
            " SELECT" +
                    " sd.id as id," +
                    " sd.name as name," +
                    " sd.code as code," +
                    " sd.status as status," +
                    " sd.department as department" +
                    " FROM SubDepartment sd" +
                    " WHERE sd.department.id =:id" +
                    " AND sd.status !='D'";

    public static final String QUERY_TO_FETCH_SUB_DEPARTMENT_TO_DELETE_BY_DEPARTMENT_ID =
            " SELECT" +
                    " sd.id as id," +
                    " sd.status as status," +
                    " sd.remarks as remarks" +
                    " FROM SubDepartment sd" +
                    " WHERE sd.department.id = :id" +
                    " AND sd.status !='D'";


    public static final String GET_SUB_DEPARTMENT_DROP_DOWN_LIST =
            "SELECT" +
                    " sd.id as value," +
                    " sd.name as label" +
                    " FROM SubDepartment sd" +
                    " WHERE sd.status != 'D'" +
                    " ORDER BY sd.id DESC";

    public static final String GET_SUB_DEPARTMENT_ACTIVE_DROP_DOWN_LIST =
            "SELECT" +
                    " sd.id as value," +
                    " sd.name as label" +
                    " FROM SubDepartment sd" +
                    " WHERE sd.status = 'Y'" +
                    " ORDER BY sd.id DESC";

    public static final String GET_SUB_DEPARTMENT_ACTIVE_DROP_DOWN_LIST_BY_DEPARTMENT_ID =
            "SELECT" +
                    " sd.id as value," +
                    " sd.name as label," +
                    " sd.code as code" +
                    " FROM SubDepartment sd" +
                    " WHERE sd.status = 'Y'" +
                    " AND sd.department.id=:departmentId" +
                    " ORDER BY sd.id DESC";


    public static final Function<SubDepartmentSearchRequestDTO, String> QUERY_TO_SEARCH_SUB_DEPARTMENT = (subDepartmentRequestDTO -> {

        String query =
                " SELECT " +
                        " sd.id as id," +
                        " sd.name as name," +
                        " sd.code as code," +
                        " sd.status as status," +
                        " sd.department.name  as department" +
                        " FROM SubDepartment sd";

        return (query + GET_WHERE_CLAUSE_FOR_SEARCH(subDepartmentRequestDTO));
    });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(SubDepartmentSearchRequestDTO searchRequestDTO) {

        String query = " WHERE sd.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND sd.id='" + searchRequestDTO.getId() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getDepartmentId())) {
            query += " AND sd.department.id='" + searchRequestDTO.getDepartmentId() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName())) {
            query += " AND sd.name='" + searchRequestDTO.getName() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getCode())) {
            query += " AND sd.code='" + searchRequestDTO.getCode() + "'";
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND sd.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY sd.id DESC";

        return query;
    }

    public static final String QUERY_TO_FETCH_SUB_DEPARTMENT_BY_ID =
            "SELECT" +
                    " sd.id as id," +
                    " sd.name as name," +
                    " sd.code as code," +
                    " sd.status as status," +
                    " sd.department as department" +
                    " FROM SubDepartment sd" +
                    " WHERE sd.id = :id" +
                    " AND sd.status!='D'";

    public static final String QUERY_TO_FIND_ACTIVE_SUB_DEPARTMENT_BY_ID_AND_DEPARTMENT_ID =
            "SELECT" +
                    " sd.id as id," +
                    " sd.name as name," +
                    " sd.code as code," +
                    " sd.status as status," +
                    " sd.department as department" +
                    " FROM SubDepartment sd" +
                    " WHERE sd.id = :id" +
                    " AND sd.department.id=:departmentId" +
                    " AND sd.status!='D'";


    public static final String QUERY_TO_FETCH_SUB_DEPARTMENT_DETAILS_BY_ID =
            " SELECT" +
                    " sd.name as name," +
                    " sd.code as code," +
                    " sd.status as status," +
                    " sd.remarks as remarks," +
                    " sd.createdDate as createdDate , " +
                    " sd.lastModifiedDate as lastModifiedDate," +
                    " sd.createdById as createdById," +
                    " sd.modifiedById as modifiedById" +
                    " FROM SubDepartment sd " +
                    "WHERE sd.id = :id" +
                    " AND sd.status!='D'";

    public static final String CREATE_QUERY_TO_FETCH_SUB_DEPARTMENT =
            "SELECT" +
                    " sd.id," +
                    " sd.name," +
                    " sd.code," +
                    " sd.status," +
                    " sd.department.id" +
                    " FROM SubDepartment sd" +
                    " WHERE sd.status !='D'";

    public final static String FETCH_SUB_DEPARTMENT_BY_NAME_AND_CODE =
            "SELECT " +
                    " sd.name," +                        //[0]
                    " sd.code" +                         //[1]
                    " FROM SubDepartment sd" +
                    " WHERE sd.name =:name" +
                    " OR sd.code =:code" +
                    " AND sd.status != 'D'";

    public final static String FETCH_DEPARTMENT_ID_NAME_BY_SUB_DEPARTMENT_ID =
            "SELECT " +
                    " sd.department.id as value," +                        //[0]
                    " sd.department.name as label" +                         //[1]
                    " FROM SubDepartment sd" +
                    " WHERE sd.name =:name" ;


    public final static String CHECK_SUB_DEPARTMENT_NAME_AND_CODE_IF_EXIST =
            "SELECT" +
                    " sd.name, " +
                    " sd.code " +
                    " FROM" +
                    " SubDepartment sd" +
                    " Where" +
                    " sd.id!=:id" +
                    " AND" +
                    " (sd.name =:name" +
                    " OR sd.code =:code)" +
                    " AND sd.status != 'D'";
}
