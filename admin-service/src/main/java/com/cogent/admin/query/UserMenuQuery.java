package com.cogent.admin.query;

/**
 * @author smriti ON 01/01/2020
 */
public class UserMenuQuery {

    public static final String QUERY_TO_FETCH_ASSIGNED_PROFILE_RESPONSE =
            "SELECT" +
                    " pm.parent_id as parentId," +                                      //[0]
                    " pm.user_menu_id as userMenuId," +                                 //[1]
                    " GROUP_CONCAT(pm.role_id) as roleId," +                            //[2]
                    " sd.code as subDepartmentCode," +                                  //[3]
                    " sd.name as subDepartmentName" +                                   //[4]
                    " FROM profile_menu pm" +
                    " LEFT JOIN profile p ON p.id =pm.profile_id" +
                    " LEFT JOIN admin_profile ap ON ap.profile_id = p.id" +
                    " LEFT JOIN admin a ON a.id = ap.admin_id" +
                    " LEFT JOIN sub_department sd ON sd.id = p.sub_department_id" +
                    " WHERE" +
                    " pm.status = 'Y'" +
                    " AND (a.username = :username OR a.email=:email)" +
                    " AND sd.code=:code" +
                    " GROUP BY pm.parent_id, pm.user_menu_id, pm.profile_id";
}
