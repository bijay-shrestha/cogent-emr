package com.cogent.admin.query;

import com.cogent.admin.dto.request.doctor.DoctorSearchRequestDTO;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author smriti on 2019-09-29
 */
public class DoctorQuery {

    /*SEARCH*/
    public static String QUERY_TO_SEARCH_DOCTOR(DoctorSearchRequestDTO searchRequestDTO) {
        return " SELECT" +
                SELECT_CLAUSE_TO_FETCH_MINIMAL_DOCTOR + "," +
                " tbl1.specialization_name as specializationName," +
                " tbl2.department_name as departmentName" +
                " FROM doctor c" +
                " LEFT JOIN doctor_avatar ca ON c.id = ca.doctor_id" +
                " RIGHT JOIN" +
                " (" +
                QUERY_TO_SEARCH_DOCTOR_SPECIALIZATION.apply(searchRequestDTO) +
                " )tbl1 ON tbl1.doctor_id = c.id" +
                " RIGHT JOIN" +
                " (" +
                QUERY_TO_SEARCH_DOCTOR_DEPARTMENT.apply(searchRequestDTO) +
                " )tbl2 ON tbl2.doctor_id = c.id" +
                GET_WHERE_CLAUSE_FOR_SEARCH_DOCTOR.apply(searchRequestDTO);
    }

    private static final String SELECT_CLAUSE_TO_FETCH_MINIMAL_DOCTOR =
            " c.id as doctorId," +                                              //[0]
                    " c.name as doctorName," +                                  //[1]
                    " c.mobile_number as mobileNumber," +                      //[2]
                    " c.status as status," +                                   //[3]
                    " ca.file_uri as avatarUri," +                             //[4]
                    " ca.is_default_image as isAvatarDefault," +                //[5]
                    " c.code as code";                                         //[6]

    private static Function<DoctorSearchRequestDTO, String> QUERY_TO_SEARCH_DOCTOR_SPECIALIZATION =
            (searchRequestDTO) -> {
                String query = " SELECT" +
                        " cs.doctor_id as doctor_id," +                               //[0]
                        " GROUP_CONCAT(s.name) as specialization_name" +             //[1]
                        " FROM" +
                        " doctor_specialization cs" +
                        " LEFT JOIN specialization s ON s.id = cs.specialization_id" +
                        " WHERE" +
                        " s.status = 'Y'" +
                        " AND cs.status ='Y'";

                if (!Objects.isNull(searchRequestDTO)) {
                    if (!Objects.isNull(searchRequestDTO.getSpecializationId()))
                        query += " AND s.id IN (" + searchRequestDTO.getSpecializationId() + ")";
                }
                return query + " GROUP BY cs.doctor_id";
            };

    private static Function<DoctorSearchRequestDTO, String> QUERY_TO_SEARCH_DOCTOR_DEPARTMENT =
            (searchRequestDTO) -> {
                String query = " SELECT" +
                        " cd.doctor_id as doctor_id," +                                //[0]
                        " GROUP_CONCAT(d.name) as department_name" +                   //[1]
                        " FROM" +
                        " doctor_department cd" +
                        " LEFT JOIN department d ON d.id = cd.department_id" +
                        " WHERE" +
                        " d.status = 'Y'" +
                        " AND cd.status ='Y'";

                if (!Objects.isNull(searchRequestDTO)) {
                    if (!Objects.isNull(searchRequestDTO.getDepartmentId()))
                        query += " AND d.id IN (" + searchRequestDTO.getDepartmentId() + ")";
                }
                return query + " GROUP BY cd.doctor_id";
            };

    private static Function<DoctorSearchRequestDTO, String> GET_WHERE_CLAUSE_FOR_SEARCH_DOCTOR =
            (searchRequestDTO) -> {

                String whereClause = " WHERE c.status!='D'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getStatus()))
                    whereClause += " AND c.status='" + searchRequestDTO.getStatus() + "'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getName()))
                    whereClause += " AND c.name LIKE '%" + searchRequestDTO.getName() + "%'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getCode()))
                    whereClause += " AND c.code LIKE '%" + searchRequestDTO.getCode() + "%'";

                if (!ObjectUtils.isEmpty(searchRequestDTO.getMobileNumber()))
                    whereClause += " AND c.mobile_number LIKE '%" + searchRequestDTO.getMobileNumber() + "%'";

                whereClause += " ORDER BY c.id DESC";

                return whereClause;
            };

    /*DROPDOWN*/
    public static final String QUERY_TO_FETCH_DOCTOR_FOR_DROPDOWN =
            " SELECT" +
                    " c.id as value," +                                      //[0]
                    " c.name as label," +                                    //[1]
                    " ca.fileUri as fileUri," +                             //[2]
                    " ca.isDefaultImage as isDefaultImage" +                //[3]
                    " FROM" +
                    " Doctor c" +
                    " LEFT JOIN DoctorAvatar ca ON c.id = ca.doctorId" +
                    " WHERE c.status ='Y'";

    /*DETAIL MODAL*/
    private static final String QUERY_TO_FETCH_DOCTOR_SCHEDULE_TYPE_FOR_DETAIL =
            " SELECT" +
                    " cs.doctor_id as doctor_id," +                              //[0]
                    " GROUP_CONCAT(ct.name) as doctor_type_name" +             //[1]
                    " FROM" +
                    " doctor_schedule_type cs" +
                    " LEFT JOIN doctor_type ct ON ct.id = cs.doctor_type_id" +
                    " WHERE" +
                    " cs.status = 'Y'" +
                    " AND ct.status ='Y'" +
                    " GROUP BY cs.doctor_id";

    private static final String QUERY_TO_FETCH_DOCTOR_QUALIFICATION_FOR_DETAIL =
            " SELECT" +
                    " cq.doctor_id as doctor_id," +                                  //[0]
                    " GROUP_CONCAT(q.name) as qualification_name" +                 //[1]
                    " FROM" +
                    " doctor_qualification cq" +
                    " LEFT JOIN qualification q ON q.id = cq.qualification_id" +
                    " WHERE" +
                    " cq.status = 'Y'" +
                    " AND q.status ='Y'" +
                    " GROUP BY cq.Doctor_id";

    private static final String SELECT_CLAUSE_TO_FETCH_DOCTOR_DETAILS =
            " c.nepali_date_of_birth as nepaliDateOfBirth," +                                  //[0]
                    " c.email as email," +                                                    //[1]
                    " c.nmc_number as nmcNumber," +                                            //[2]
                    " c.nepali_joined_date as nepaliJoinedDate," +                             //[3]
                    " c.nepali_resignation_date as nepaliResignationDate," +                   //[4]
                    " c.remarks as remarks, " +
                    " cos.file_uri as signatureUri," +
                    " cos.is_default_image as isSignatureDefault";

    public static final String QUERY_TO_FETCH_DOCTOR_DETAILS =
            " SELECT" +
                    SELECT_CLAUSE_TO_FETCH_MINIMAL_DOCTOR + "," +
                    SELECT_CLAUSE_TO_FETCH_DOCTOR_DETAILS + "," +
                    " g.name as genderName," +
                    " cy.name as countryName," +
                    " tbl1.specialization_name as specializationName," +
                    " tbl2.department_name as departmentName," +
                    " tbl3.doctor_type_name as doctorTypeName," +
                    " tbl4.qualification_name as qualificationName" +
                    " FROM doctor c" +
                    " LEFT JOIN doctor_avatar ca ON c.id = ca.doctor_id" +
                    " LEFT JOIN doctor_signature cos ON c.id = cos.doctor_id" +
                    " LEFT JOIN gender g ON g.id = c.gender" +
                    " LEFT JOIN country cy ON cy.id = c.country" +
                    " RIGHT JOIN" +
                    " (" +
                    QUERY_TO_SEARCH_DOCTOR_SPECIALIZATION.apply(null) +
                    " )tbl1 ON tbl1.doctor_id = c.id" +
                    " RIGHT JOIN" +
                    " (" +
                    QUERY_TO_SEARCH_DOCTOR_DEPARTMENT.apply(null) +
                    " )tbl2 ON tbl2.doctor_id = c.id" +
                    " RIGHT JOIN" +
                    " (" +
                    QUERY_TO_FETCH_DOCTOR_SCHEDULE_TYPE_FOR_DETAIL +
                    " )tbl3 ON tbl3.doctor_id = c.id" +
                    " RIGHT JOIN" +
                    " (" +
                    QUERY_TO_FETCH_DOCTOR_QUALIFICATION_FOR_DETAIL +
                    " )tbl4 ON tbl4.doctor_id = c.id" +
                    " WHERE c.status != 'D'" +
                    " AND c.id = :id";

    /*UPDATE MODAL*/
    private static final String QUERY_TO_FETCH_DOCTOR_SPECIALIZATION_FOR_UPDATE =
            " SELECT" +
                    " cs.doctor_id as doctor_id," +                                  //[0]
                    " GROUP_CONCAT(cs.id) as doctor_specialization_id," +            //[1]
                    " GROUP_CONCAT(s.id) as specialization_id" +                    //[2]
                    " FROM" +
                    " doctor_specialization cs" +
                    " LEFT JOIN specialization s ON s.id = cs.specialization_id" +
                    " WHERE" +
                    " s.status = 'Y'" +
                    " AND cs.status ='Y'" +
                    " GROUP BY cs.Doctor_id";

    private static final String QUERY_TO_FETCH_DOCTOR_DEPARTMENT_FOR_UPDATE =
            " SELECT" +
                    " cd.doctor_id as doctor_id," +                              //[0]
                    " GROUP_CONCAT(cd.id) as doctor_department_id," +           //[1]
                    " GROUP_CONCAT(d.id) as department_id" +                    //[2]
                    " FROM" +
                    " doctor_department cd" +
                    " LEFT JOIN department d ON d.id = cd.department_id" +
                    " WHERE" +
                    " d.status = 'Y'" +
                    " AND cd.status ='Y'" +
                    " GROUP BY cd.doctor_id";

    private static final String QUERY_TO_FETCH_DOCTOR_SCHEDULE_TYPE_FOR_UPDATE =
            " SELECT" +
                    " cs.doctor_id as doctor_id," +                               //[0]
                    " GROUP_CONCAT(cs.id) as doctor_schedule_type_id," +        //[1]
                    " GROUP_CONCAT(ct.id) as doctor_type_id" +                  //[2]
                    " FROM" +
                    " doctor_schedule_type cs" +
                    " LEFT JOIN doctor_type ct ON ct.id = cs.doctor_type_id" +
                    " WHERE" +
                    " cs.status = 'Y'" +
                    " AND ct.status ='Y'" +
                    " GROUP BY cs.doctor_id";

    private static final String QUERY_TO_FETCH_DOCTOR_QUALIFICATION_FOR_UPDATE =
            " SELECT" +
                    " cq.doctor_id as doctor_id," +                                 //[0]
                    " GROUP_CONCAT(cq.id) as doctor_qualification_id," +           //[1]
                    " GROUP_CONCAT(q.id) as qualification_id" +                   //[2]
                    " FROM" +
                    " doctor_qualification cq" +
                    " LEFT JOIN qualification q ON q.id = cq.qualification_id" +
                    " WHERE" +
                    " cq.status = 'Y'" +
                    " AND q.status ='Y'" +
                    " GROUP BY cq.doctor_id";

    public static final String QUERY_TO_FETCH_DOCTOR_DETAILS_FOR_UPDATE =
            " SELECT" +
                    SELECT_CLAUSE_TO_FETCH_MINIMAL_DOCTOR + "," +
                    SELECT_CLAUSE_TO_FETCH_DOCTOR_DETAILS + "," +
                    " g.id as genderId," +
                    " cy.id as countryId," +
                    " tbl1.doctor_specialization_id as doctorSpecializationId," +
                    " tbl1.specialization_id as specializationId," +
                    " tbl2.doctor_department_id as doctorDepartmentId," +
                    " tbl2.department_id as departmentId," +
                    " tbl3.doctor_schedule_type_id as doctorScheduleTypeId," +
                    " tbl3.doctor_type_id as doctorTypeId," +
                    " tbl4.doctor_qualification_id as doctorQualificationId," +
                    " tbl4.qualification_id as qualificationId" +
                    " FROM doctor c" +
                    " LEFT JOIN doctor_avatar ca ON c.id = ca.doctor_id" +
                    " LEFT JOIN gender g ON g.id = c.gender" +
                    " LEFT JOIN country cy ON cy.id = c.country" +
                    " LEFT JOIN doctor_signature cos ON c.id = cos.doctor_id" +
                    " RIGHT JOIN" +
                    " (" +
                    QUERY_TO_FETCH_DOCTOR_SPECIALIZATION_FOR_UPDATE +
                    " )tbl1 ON tbl1.doctor_id = c.id" +
                    " RIGHT JOIN" +
                    " (" +
                    QUERY_TO_FETCH_DOCTOR_DEPARTMENT_FOR_UPDATE +
                    " )tbl2 ON tbl2.doctor_id = c.id" +
                    " RIGHT JOIN" +
                    " (" +
                    QUERY_TO_FETCH_DOCTOR_SCHEDULE_TYPE_FOR_UPDATE +
                    " )tbl3 ON tbl3.doctor_id = c.id" +
                    " RIGHT JOIN" +
                    " (" +
                    QUERY_TO_FETCH_DOCTOR_QUALIFICATION_FOR_UPDATE +
                    " )tbl4 ON tbl4.doctor_id = c.id" +
                    " WHERE c.status != 'D'" +
                    " AND c.id = :id";

    public static String QUERY_TO_FETCH_DOCTOR_BY_SPECIALIZATION_ID =
            "SELECT" +
                    " c.id as value," +                                      //[0]
                    " c.name as label," +                                    //[1]
                    " ca.fileUri as fileUri," +                             //[2]
                    " ca.isDefaultImage as isDefaultImage" +                //[3]
                    " FROM DoctorSpecialization cs" +
                    " LEFT JOIN Doctor c ON c.id = cs.doctorId" +
                    " LEFT JOIN DoctorAvatar ca ON c.id = ca.doctorId" +
                    " WHERE cs.specializationId = :id" +
                    " AND cs.status = 'Y'" +
                    " AND c.status = 'Y'";

}