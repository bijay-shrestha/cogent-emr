package com.cogent.admin.query;

import com.cogent.admin.dto.request.municipality.MunicipalitySearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author smriti on 2019-09-15
 */
public class MunicipalityQuery {

    public static final String QUERY_TO_FIND_MUNICIPALITY_COUNT_BY_NAME =
            "SELECT COUNT(m.id) FROM Municipality m WHERE m.name =:name AND m.status != 'D'";

    public static final String QUERY_TO_FIND_MUNICIPALITY_COUNT_BY_ID_AND_NAME =
            "SELECT COUNT(m.id) FROM Municipality m WHERE m.id!= :id AND m.name =:name AND m.status != 'D'";

    public static final String QUERY_TO_FETCH_ACTIVE_MUNICIPALITY_FOR_DROPDOWN =
            " SELECT" +
                    " m.id as value," +                                 //[0]
                    " m.name as label" +                               //[1]
                    " FROM" +
                    " Municipality m" +
                    " WHERE" +
                    " m.status ='Y'";

    public static String QUERY_TO_SEARCH_MUNICIPALITY(MunicipalitySearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                " m.id as id," +                                      //[0]
                " m.name as name," +                                  //[1]
                " m.status as status," +                              //[2]
                " m.district.name as districtName" +                  //[3]
                " FROM" +
                " Municipality m" +
                GET_WHERE_CLAUSE_FOR_SEARCHING_MUNICIPALITY.apply(searchRequestDTO);
    }

    private static Function<MunicipalitySearchRequestDTO, String>
            GET_WHERE_CLAUSE_FOR_SEARCHING_MUNICIPALITY = (searchRequestDTO) -> {

        String whereClause = " WHERE m.status!='D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
            whereClause += " AND m.status='" + searchRequestDTO.getStatus() + "'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
            whereClause += " AND m.name='" + searchRequestDTO.getName() + "'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getDistrictId()))
            whereClause += " AND m.district=" + searchRequestDTO.getDistrictId();

        whereClause += " ORDER BY m.id DESC";

        return whereClause;
    };

    public static final String QUERY_TO_FETCH_MUNICIPALITY_DETAILS =
            " SELECT" +
                    " m.remarks as remarks," +
                    " m.district.id as districtId" +
                    " FROM" +
                    " Municipality m" +
                    " WHERE m.id = :id";

    public static final String QUERY_TO_FETCH_MUNICIPALITY =
            " SELECT" +
                    " m.id as id," +
                    " m.name as name," +
                    " m.status as status," +
                    " m.remarks as remarks," +
                    " m.district as district" +
                    " FROM" +
                    " Municipality m" +
                    " WHERE m.id = :id" +
                    " AND m.status='Y'";


    public static final String QUERY_TO_FETCH_MUNICIPALITY_ENTITY =
           "select " +
                   " m.id," +                     //[0]
                   " m.name," +                   //[1]
                   " m.remarks," +               //[2]
                   " m.status," +                //[3]
                   " m.district," +             //[4]
                   " d.provinces " +            //[5]
                   " from Municipality m " +
                   " LEFT JOIN District d ON d.id=m.district.id" +
                   " WHERE m.id = :id" +
                   " AND m.status='Y'";
}
