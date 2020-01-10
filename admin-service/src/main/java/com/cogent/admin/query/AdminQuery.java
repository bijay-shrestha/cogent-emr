package com.cogent.admin.query;

import com.cogent.admin.dto.request.admin.AdminSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author smriti on 2019-08-05
 */
public class AdminQuery {

    public static final String QUERY_TO_FIND_ADMIN_FOR_VALIDATION =
            "SELECT " +
                    " a.username," +                            //[0]
                    " a.email," +                               //[1]
                    " a.mobileNumber" +                        //[2]
                    " FROM" +
                    " Admin a" +
                    " WHERE" +
                    " a.status != 'D'" +
                    " AND" +
                    " (a.username =:username OR a.email =:email OR a.mobileNumber = :mobileNumber)";

    public static final String QUERY_TO_FIND_ADMIN_EXCEPT_CURRENT_ADMIN =
            "SELECT " +
                    " a.email," +                               //[0]
                    " a.mobileNumber" +                        //[1]
                    " FROM" +
                    " Admin a" +
                    " WHERE" +
                    " a.status != 'D'" +
                    " AND a.id !=:id" +
                    " AND" +
                    " (a.email =:email OR a.mobileNumber = :mobileNumber)";

    public static final String QUERY_TO_FETCH_ACTIVE_ADMIN_FOR_DROPDOWN =
            " SELECT" +
                    " id as value," +                     //[0]
                    " username as label" +               //[1]
                    " FROM" +
                    " Admin" +
                    " WHERE status ='Y'";

    public static String QUERY_TO_SEARCH_ADMIN(AdminSearchRequestDTO searchRequestDTO) {
        return SELECT_CLAUSE_TO_FETCH_ADMIN + "," +
                " tbl.profileName as profileName" +
                " FROM" +
                " admin a" +
                " LEFT JOIN admin_avatar av ON a.id = av.admin_id" +
                " LEFT JOIN admin_meta_info ami ON a.id = ami.admin_id" +
                " LEFT JOIN admin_category ac On ac.id = a.admin_category_id" +
                " LEFT JOIN" +
                " (" +
                QUERY_TO_SEARCH_ADMIN_PROFILE(searchRequestDTO) +
                " ) tbl ON tbl.adminId = a.id" +
                GET_WHERE_CLAUSE_FOR_SEARCH_ADMIN(searchRequestDTO);
    }

    private static final String SELECT_CLAUSE_TO_FETCH_ADMIN =
            " SELECT" +
                    " a.id as id," +
                    " a.full_name as fullName," +
                    " a.username as username," +
                    " a.email as email," +
                    " a.mobile_number as mobileNumber," +
                    " a.status as status," +
                    " a.has_mac_binding as hasMacBinding," +
                    " ac.name as adminCategoryName," +
                    " av.file_uri as fileUri," +
                    " av.is_default_image as isDefaultImage";

    private static String QUERY_TO_SEARCH_ADMIN_PROFILE(AdminSearchRequestDTO requestDTO) {
        String query = "SELECT ap.admin_id as adminId," +
                " GROUP_CONCAT(p.name) as profileName" +
                " FROM admin_profile ap" +
                " LEFT JOIN profile p ON p.id = ap.profile_id" +
                " WHERE ap.status = 'Y'";

        if (!Objects.isNull(requestDTO.getProfileId()))
            query += " AND p.id=" + requestDTO.getProfileId();

        return query + " GROUP BY ap.admin_id";
    }

    private static String GET_WHERE_CLAUSE_FOR_SEARCH_ADMIN(AdminSearchRequestDTO searchRequestDTO) {
        String whereClause = " WHERE a.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
            whereClause += " AND a.status='" + searchRequestDTO.getStatus() + "'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getAdminCategoryId()))
            whereClause += " AND a.adminCategory.id=" + searchRequestDTO.getAdminCategoryId();

        if (!ObjectUtils.isEmpty(searchRequestDTO.getMetaInfo()))
            whereClause += " AND ami.metaInfo LIKE '%" + searchRequestDTO.getMetaInfo() + "%'";

        whereClause += " ORDER BY a.id DESC";

        return whereClause;
    }

    private static final String QUERY_TO_FETCH_ADMIN_PROFILE =
            "SELECT ap.admin_id as adminId," +
                    " GROUP_CONCAT(ap.id) as adminProfileId," +
                    " GROUP_CONCAT(p.id) as profileId" +
                    " FROM admin_profile ap" +
                    " LEFT JOIN profile p ON p.id = ap.profile_id" +
                    " WHERE ap.status = 'Y'" +
                    " GROUP BY ap.admin_id";

    private static final String QUERY_TO_FETCH_ADMIN_APPLICATION_MODULE =
            "SELECT aam.admin_id as adminId," +
                    " GROUP_CONCAT(aam.id) as adminApplicationModuleId," +
                    " GROUP_CONCAT(aam.id) as applicationModuleId" +
                    " FROM admin_application_module aam" +
                    " LEFT JOIN application_module am ON am.id = aam.application_module_id" +
                    " WHERE aam.status = 'Y'" +
                    " GROUP BY aam.admin_id";

    public static final String QUERY_TO_FETCH_ADMIN_DETAIL =
            SELECT_CLAUSE_TO_FETCH_ADMIN + "," +
                    " ac.id as adminCategoryId," +
                    " a.remarks as remarks," +
                    " tbl1.adminProfileId as adminProfileId," +
                    " tbl1.profileId as profileId," +
                    " tbl2.adminApplicationModuleId as adminApplicationModuleId," +
                    " tbl2.applicationModuleId as applicationModuleId," +
                    " h.name as hospitalName," +
                    " h.id as hospitalId" +
                    " FROM admin a" +
                    " LEFT JOIN admin_avatar av ON a.id = av.admin_id" +
                    " LEFT JOIN admin_category ac On ac.id = a.admin_category_id" +
                    " LEFT JOIN hospital h ON h.id = a.hospital_id" +
                    " LEFT JOIN" +
                    " (" +
                    QUERY_TO_FETCH_ADMIN_PROFILE +
                    " )tbl1 ON tbl1.adminId = a.id" +
                    " LEFT JOIN" +
                    " (" +
                    QUERY_TO_FETCH_ADMIN_APPLICATION_MODULE +
                    ")tbl2 ON tbl2.adminId = a.id" +
                    " WHERE a.id = :id" +
                    " AND a.status !='D'";

    public static final String QUERY_FO_FETCH_MAC_ADDRESS_INFO =
            "SELECT m.id as id," +                                  //[0]
                    " m.macAddress as macAddress" +               //[1]
                    " FROM MacAddressInfo m" +
                    " WHERE" +
                    " m.status = 'Y'" +
                    " AND m.admin.id = :id";

    public static final String QUERY_TO_FETCH_ADMIN_BY_USERNAME_OR_EMAIL =
            " SELECT a FROM Admin a" +
                    " WHERE" +
                    " (a.username=:username OR a.email =:email)" +
                    " AND a.status != 'D'";

    public static final String QUERY_TO_FETCH_ADMIN_INFO =
            " SELECT a.id as adminId," +                                                //[0]
                    " a.username as username," +                                        //[1]
                    " a.fullName as fullName," +                                        //[2]
                    " sd.id as subDepartmentId," +                                      //[3]
                    " sd.name as subDepartmentName," +
                    " sd.department.id as departmentId," +                                  //[4]
                    " p.name as profileName," +                                         //[5]
                    " p.id as profileId" +                                              //[6]
                    " FROM Admin a " +
                    " LEFT JOIN AdminProfile ap ON ap.adminId = a.id" +
                    " LEFT JOIN Profile p ON p.id = ap.profileId" +
                    " LEFT JOIN SubDepartment sd ON sd.id = p.subDepartment.id" +
                    " WHERE" +
                    " a.status = 'Y'" +
                    " AND (a.username=:username OR a.email =:email)" +
                    " AND sd.code=:code";

    public static final String QUERY_TO_FETCH_ADMIN_INFO_BY_USERNAME =
            " SELECT GROUP_CONCAT(sd.code)," +                                                              //[0]
                    " a.password" +                                                                         //[1]
                    " FROM application_module am" +
                    " LEFT JOIN admin_application_module aam ON aam.application_module_id=am.id" +
                    " LEFT JOIN sub_department sd ON sd.id = am.sub_department_id" +
                    " LEFT JOIN admin a ON a.id = aam.admin_id" +
                    " WHERE am.status = 'Y'" +
                    " AND aam.status = 'Y'" +
                    " AND a.status ='Y'" +
                    " AND (a.username =:username OR a.email =:email)" +
                    " GROUP BY aam.admin_id";

    public static final String QUERY_TO_FETCH_LOGGED_IN_ADMIN_SUB_DEPARTMENT_LIST =
            "SELECT" +
                    " sd.id as subDepartmentId," +
                    " sd.name as subDepartmentName," +
                    " sd.code as subDepartmentCode" +
                    " FROM SubDepartment sd" +
                    " LEFT JOIN Profile p ON p.subDepartment.id=sd.id" +
                    " LEFT JOIN AdminProfile ap ON ap.profileId=p.id" +
                    " LEFT JOIN Admin a ON a.id=ap.adminId" +
                    " WHERE a.username=:username" +
                    " AND a.status='Y'";
}