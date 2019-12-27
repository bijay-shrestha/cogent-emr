package com.cogent.admin.query;

import com.cogent.admin.dto.request.profile.ProfileSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author smriti on 7/9/19
 */
public class ProfileQuery {

    public static final String QUERY_TO_FIND_PROFILE_COUNT_BY_NAME =
            "SELECT COUNT(p.id) FROM Profile p WHERE p.name =:name AND p.status != 'D'";

    public static final String QUERY_TO_FIND_PROFILE_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(p.id) FROM Profile p WHERE p.id!= :id AND p.name =:name AND p.status != 'D'";

    private static Function<ProfileSearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCH_PROFILE =
            (searchRequestDTO) -> {
                String whereClause = " WHERE p.status!='D'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
                    whereClause += " AND p.name='" + searchRequestDTO.getName() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
                    whereClause += " AND p.status='" + searchRequestDTO.getStatus() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getSubDepartmentId()))
                    whereClause += " AND p.subDepartment.id=" + searchRequestDTO.getSubDepartmentId();

                whereClause += " ORDER BY p.id DESC";

                return whereClause;
            };

    public static Function<ProfileSearchRequestDTO, String> QUERY_TO_SEARCH_PROFILE = (searchRequestDTO) -> {
        return " SELECT" +
                " p.id as id," +                                             //[0]
                " p.name as name," +                                        //[1]
                " p.status as status," +                                    //[2]
                " p.subDepartment.name AS subDepartmentName" +            //[3]
                " FROM" +
                " Profile p" +
                GET_WHERE_CLAUSE_FOR_SEARCH_PROFILE.apply(searchRequestDTO);
    };

    public static final String QUERY_TO_FETCH_PROFILE_DETAILS =
            " SELECT" +
                    " p.name as name," +                                    //[0]
                    " p.status as status," +                               //[1]
                    " p.description as description," +                     //[2]
                    " p.subDepartment.id as subDepartmentId," +            //[3]
                    " sd.name as subDepartmentName," +                     //[4]
                    " sd.code as subDepartmentCode," +                     //[5]
                    " p.remarks as remarks," +                             //[6]
                    " d.id as departmentId," +                             //[7]
                    " d.name as departmentName" +                          //[8]
                    " FROM" +
                    " Profile p" +
                    " LEFT JOIN SubDepartment sd ON p.subDepartment.id = sd.id" +
                    " LEFT JOIN Department d ON sd.department.id = d.id" +
                    " WHERE" +
                    " p.id=:id" +
                    " AND p.status!='D'";

    public static final String QUERY_TO_FETCH_PROFILE_MENU_DETAILS =
            " SELECT" +
                    " pm.id as profileMenuId," +                              //[0]
                    " pm.roleId as roleId," +                                 //[1]
                    " pm.userMenuId as userMenuId," +                         //[2]
                    " pm.parentId as parentId," +                             //[3]
                    " pm.status as status" +                                  //[4]
                    " FROM" +
                    " ProfileMenu pm" +
                    " LEFT JOIN Profile p ON pm.profile.id = p.id" +
                    " WHERE" +
                    " pm.profile.id=:id" +
                    " AND pm.status='Y'";

    public static final String QUERY_TO_FETCH_PROFILE_DETAILS_WITH_GROUP_CONCAT =
            "SELECT" +
                    " group_concat((concat(pm.id, '-',pm.roleId, '-', pm.userMenuId)))," +      //[0]
                    " p.description," +                                                         //[1]
                    " p.subDepartment.id," +                                                    //[2]
                    " p.remarks" +                                                             //[3]
                    " FROM" +
                    " ProfileMenu pm" +
                    " LEFT JOIN Profile p ON pm.profile.id = p.id" +
                    " WHERE" +
                    " p.id=:id" +
                    " AND pm.status='Y'" +
                    " GROUP BY p.id";

    public static final String QUERY_TO_FETCH_ACTIVE_PROFILES_FOR_DROPDOWN =
            " SELECT id as value, name as label FROM Profile WHERE status ='Y'";

    public static final String QUERY_TO_FETCH_PROFILE_BY_SUB_DEPARTMENT_ID =
            " SELECT p.id as value," +
                    " p.name as label" +
                    " FROM Profile p" +
                    " WHERE p.status ='Y'" +
                    " AND p.subDepartment.id =:id";
}
