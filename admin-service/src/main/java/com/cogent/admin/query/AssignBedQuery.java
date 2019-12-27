package com.cogent.admin.query;

import com.cogent.admin.dto.request.assignbed.AssignBedSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.function.Function;

/**
 * @author Sauravi Thapa 11/7/19
 */
public class AssignBedQuery {


    public static final Function<AssignBedSearchRequestDTO, String> QUERY_TO_SEARCH_ASSIGNED_BED = (
            searchRequestDTO -> {

                String query =
                        "SELECT " +
                                " b.name as bedName," +
                                " w.name as wardName," +
                                " u.name as unitName," +
                                " ab.status as status," +
                                " ab.remarks as remarks" +
                                " FROM AssignBed ab" +
                                " LEFT JOIN Bed b ON b.id=ab.bed.id" +
                                " LEFT JOIN Ward w ON w.id=ab.ward.id" +
                                " LEFT JOIN Unit u ON u.id=ab.unit.id";

                return (query + GET_WHERE_CLAUSE_FOR_SEARCH(searchRequestDTO));

            });

    private static String GET_WHERE_CLAUSE_FOR_SEARCH(AssignBedSearchRequestDTO searchRequestDTO) {

        String query = " WHERE ab.status != 'D'";

        if (!ObjectUtils.isEmpty(searchRequestDTO.getId())) {
            query += " AND ab.id=" + searchRequestDTO.getId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getBedId())) {
            query += " AND ab.bed.id=" + searchRequestDTO.getBedId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getWardId())) {
            query += " AND ab.ward.id=" + searchRequestDTO.getWardId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getUnitId())) {
            query += " AND ab.unit.id=" + searchRequestDTO.getUnitId();
        }

        if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus())) {
            query += " AND ab.status='" + searchRequestDTO.getStatus() + "'";
        }

        query += " ORDER BY ab.id DESC";

        return query;

    }

    public final static String FETCH_ASSIGNED_BED_DETAILS =
            "SELECT" +
                    " b.name as bedName," +
                    " w.name as wardName," +
                    " u.name as unitName," +
                    " ab.status as status," +
                    " ab.remarks as remarks" +
                    " FROM AssignBed ab" +
                    " LEFT JOIN Bed b ON b.id=ab.bed.id" +
                    " LEFT JOIN Ward w ON w.id=ab.ward.id" +
                    " LEFT JOIN Unit u ON u.id=ab.unit.id" +
                    " WHERE ab.id=:id" +
                    " AND ab.status != 'D'";

//    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_ASSIGN_BED =
//            " SELECT" +
//                    " ab.id as value," +
//                    " ab.name as label" +
//                    " FROM AssignBed ab" +
//                    " WHERE ab.status = 'Y'";
//
//    public static final String QUERY_FOR_DROP_DOWN_ASSIGN_BED =
//            " SELECT" +
//                    " ab.id as value," +
//                    " ab.name as label" +
//                    " FROM AssignBed ab" +
//                    " WHERE ab.status != 'D'";

    public static final String CHECK_IF_BEDS_EXISTS =
            " SELECT " +
                    " COUNT(ab.id)" +
                    " FROM" +
                    " AssignBed ab" +
                    " WHERE" +
                    " ab.status!='D'" +
                    " AND" +
                    " ab.bed.id=:bedId" +
                    " AND" +
                    " ab.ward.id=:wardId" +
                    " AND" +
                    " (ab.unit.id IS NULL OR ab.unit.id=:unitId)";


    public static final String FETCH_ASSIGN_BED_BY_ID =
            " SELECT ab FROM AssignBed ab WHERE ab.id =:id";


    public static final String QUERY_FOR_ACTIVE_DROP_DOWN_ASSIGNED_BED_BY_WARD_ID =
            "SELECT " +
                    " ab.id as value," +
                    " b.name as label" +
                    " from" +
                    " AssignBed ab" +
                    " LEFT JOIN Bed b ON b.id=ab.bed.id" +
                    " where" +
                    " ab.ward.id =:wardId" +
                    " AND ab.status = 'Y'";

    public static final String QUERY_FOR_DROP_DOWN_ASSIGNED_BED_BY_WARD_ID =
            "SELECT " +
                    " ab.id as value," +
                    " b.name as label" +
                    " from" +
                    " AssignBed ab" +
                    " LEFT JOIN Bed b ON b.id=ab.bed.id" +
                    " where" +
                    " ab.ward.id =:wardId" +
                    " AND ab.status != 'D'";

}
