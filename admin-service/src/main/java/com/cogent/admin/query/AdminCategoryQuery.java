package com.cogent.admin.query;

import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author smriti on 2019-08-11
 */
public class AdminCategoryQuery {

    public static final String QUERY_TO_FIND_ADMIN_CATEGORY_BY_NAME_OR_CODE =
            "SELECT" +
                    " a.name," +                        //[0]
                    " a.code" +                         //[1]
                    " FROM AdminCategory a" +
                    " WHERE" +
                    " a.name =:name OR a.code =:code AND a.status != 'D'";

    public static final String QUERY_TO_FIND_ADMIN_CATEGORY_BY_ID_AND_NAME_OR_CODE =
            "SELECT " +
                    " a.name," +                        //[0]
                    " a.code" +                         //[1]
                    " FROM AdminCategory a" +
                    " WHERE" +
                    " a.id!=:id AND (a.name =:name OR a.code =:code) AND a.status != 'D'";

    public static final String QUERY_TO_FETCH_ACTIVE_ADMIN_CATEGORY_FOR_DROPDOWN =
            " SELECT" +
                    " a.id as value," +                                      //[0]
                    " a.name as label" +                                     //[1]
                    " FROM" +
                    " AdminCategory a WHERE a.status ='Y'";

    public static final String QUERY_TO_FETCH_ADMIN_CATEGORY_DETAILS =
            " SELECT" +
                    " a.name as name," +                                           //[0]
                    " a.code as code," +                                           //[1]
                    " a.status as status," +                                       //[2]
                    " a.designation as designation ," +                            //[3]
                    " a.registrationNumber as registrationNumber," +               //[4]
                    " a.remarks as remarks" +                                      //[5]
                    " FROM" +
                    " AdminCategory a" +
                    " WHERE a.id = :id";

    private static Function<AdminCategorySearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCHING_ADMIN_CATEGORY =
            (searchRequestDTO) -> {
                String whereClause = " WHERE a.status!='D'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getId()))
                    whereClause += " AND a.id=" + searchRequestDTO.getId();

                if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
                    whereClause += " AND a.status='" + searchRequestDTO.getStatus() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getCode()))
                    whereClause += " AND a.code='" + searchRequestDTO.getCode() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getDesignation()))
                    whereClause += " AND a.designation='" + searchRequestDTO.getDesignation() + "'";

                whereClause += " ORDER BY a.id DESC";

                return whereClause;
            };

    public static Function<AdminCategorySearchRequestDTO, String> QUERY_TO_SEARCH_ADMIN_CATEGORY =
            (searchRequestDTO) -> (
                    " SELECT" +
                            " a.id as id," +                                               //[0]
                            " a.name as name," +                                           //[1]
                            " a.code as code," +                                          //[2]
                            " a.status as status," +                                     //[3]
                            " a.designation as designation ," +                         //[4]
                            " a.registrationNumber as registrationNumber" +            //[5]
                            " FROM" +
                            " AdminCategory a" +
                            GET_WHERE_CLAUSE_FOR_SEARCHING_ADMIN_CATEGORY.apply(searchRequestDTO));

    public static final String QUERY_TO_FETCH_ADMIN_CATEGORY_FOR_EXCEL =
            " SELECT" +
                    " a.id," +                                                    //[0]
                    " a.name," +                                                  //[1]
                    " a.code," +                                                  //[2]
                    " a.status," +                                               //[3]
                    " a.designation," +                                           //[4]
                    " a.registrationNumber" +                                    //[5]
                    " FROM" +
                    " AdminCategory a" +
                    " WHERE a.status !='D'";

}
