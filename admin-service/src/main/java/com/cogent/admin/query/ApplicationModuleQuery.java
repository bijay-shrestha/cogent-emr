package com.cogent.admin.query;

import com.cogent.admin.dto.request.applicationModules.ApplicationModuleSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * @author smriti ON 24/12/2019
 */

public class ApplicationModuleQuery {

    public static final String QUERY_TO_FIND_APPOINTMENT_MODULE_COUNT_BY_NAME =
            "SELECT COUNT(a.id) FROM ApplicationModule a WHERE a.name =:name AND a.status != 'D'";

    public static final String QUERY_TO_FIND_APPOINTMENT_MODULE_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(a.id) FROM ApplicationModule a WHERE a.id!= :id AND a.name =:name AND a.status != 'D'";

    public static String QUERY_TO_SEARCH_APPLICATION_MODULE(ApplicationModuleSearchRequestDTO searchRequestDTO) {

        String query = " SELECT" +
                " a.id as id," +                                               //[0]
                " a.name as name," +                                           //[1]
                " a.subDepartmentId.name as subDepartmentName," +              //[2]
                " a.status as status" +                                        //[3]
                " FROM" +
                " ApplicationModule a" +
                " WHERE a.status !='D'";

        if (!Objects.isNull(searchRequestDTO.getSubDepartmentId()))
            query += " AND a.subDepartmentId.id=" + searchRequestDTO.getSubDepartmentId();

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
            query += " AND a.name LIKE '%" + searchRequestDTO.getName() + "%'";

        return query;
    }

    public static final String QUERY_TO_FETCH_APPLICATION_MODULE_DETAILS =
            " SELECT" +
                    " a.name as name," +                                           //[0]
                    " a.subDepartmentId.id as subDepartmentId," +                  //[1]
                    " a.subDepartmentId.name as subDepartmentName," +              //[2]
                    " a.status as status," +                                       //[3]
                    " a.remarks as remarks" +
                    " FROM" +
                    " ApplicationModule a" +
                    " WHERE" +
                    " a.status !='D'" +
                    " AND a.id=:id";

    public static final String QUERY_TO_FETCH_ACTIVE_APPLICATION_MODULE_FOR_DROPDOWN =
            " SELECT" +
                    " a.id as id," +                                      //[0]
                    " a.name as name," +                                  //[1]
                    " a.subDepartmentId.id as subDepartmentId" +          //[2]
                    " FROM" +
                    " ApplicationModule a" +
                    " WHERE a.status ='Y'";

}
