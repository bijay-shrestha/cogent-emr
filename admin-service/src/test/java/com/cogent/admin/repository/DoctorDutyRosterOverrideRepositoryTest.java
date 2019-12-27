package com.cogent.admin.repository;

import com.cogent.admin.constants.QueryConstants;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterStatusRequestDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.dto.doctorDutyRoster.DoctorDutyRosterRequestUtils.getDoctorDutyRosterStatusRequestDTO;
import static com.cogent.admin.utils.QueryUtil.getNativeQuery;
import static com.cogent.admin.utils.QueryUtil.getQuery;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DoctorDutyRosterOverrideRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void validateDoctorDutyRosterOverrideCount() {

        String sql = " SELECT COUNT(d.id)" +
                " FROM DoctorDutyRosterOverride d" +
                " LEFT JOIN DoctorDutyRoster dr ON dr.id = d.doctorDutyRosterId.id" +
                " WHERE dr.status != 'D'" +
                " AND d.status = 'Y'" +
                " AND dr.doctorId.id=:doctorId" +
                " AND dr.specializationId.id= :specializationId" +
                " AND d.toDate >=:fromDate" +
                " AND d.fromDate <=:toDate";

        Query query = getQuery.apply(testEntityManager, sql)
                .setParameter(DOCTOR_ID, 1L)
                .setParameter(SPECIALIZATION_ID, 1L)
                .setParameter(FROM_DATE, new Date())
                .setParameter(TO_DATE, new Date());

        Long result = (Long) query.getSingleResult();

        System.out.println(result);
    }

    @Test
    public void fetchDetailsById() {

        String sql = "SELECT" +
                " d.id as doctorDutyRosterOverrideId," +
                " d.fromDate as fromDate," +
                " d.toDate as toDate," +
                " d.startTime as startTime," +
                " d.endTime as endTime," +
                " d.dayOffStatus as dayOffStatus" +
                " FROM DoctorDutyRosterOverride d" +
                " WHERE" +
                " d.doctorDutyRosterId.status!= 'D'" +
                " AND d.status = 'Y'" +
                " AND d.doctorDutyRosterId.id = 4";

        Query query = getQuery.apply(testEntityManager, sql);

        List results = query.getResultList();

        System.out.println(results);
    }

    @Test
    public void fetchDoctorDutyRoster() {
        String SQL = "SELECT d.startTime as startTime," +
                " d.endTime as endTime," +
                " d.dayOffStatus as dayOffStatus" +
                " FROM DoctorDutyRosterOverride d" +
                " LEFT JOIN DoctorDutyRoster dd ON dd.id = d.doctorDutyRosterId.id" +
                " WHERE" +
                " d.status = 'Y'" +
                " AND dd.status = 'Y'" +
                " AND :date BETWEEN d.fromDate AND d.toDate" +
                " AND dd.doctorId.id = :doctorId" +
                " AND dd.specializationId.id = :specializationId";

        Query query = getQuery.apply(testEntityManager, SQL)
                .setParameter(QueryConstants.DATE, new Date())
                .setParameter(DOCTOR_ID, 1L)
                .setParameter(SPECIALIZATION_ID, 1L);

        Object result = query.getSingleResult();

        System.out.println(result);
    }

    @Test
    public void fetchDoctorDutyRosterOverrideStatus() {

        DoctorDutyRosterStatusRequestDTO requestDTO = getDoctorDutyRosterStatusRequestDTO();

        String SQL = "SELECT" +
                " d.fromDate," +                                            //[0]
                " d.toDate," +                                              //[1]
                " DATE_FORMAT(d.startTime, '%H:%i') as startTime," +        //[2]
                " DATE_FORMAT(d.endTime, '%H:%i') as endTime," +            //[3]
                " d.dayOffStatus," +                                        //[4]
                " dd.doctorId.id as doctorId," +                            //[5]
                " dd.doctorId.name as doctorName," +                        //[6]
                " dd.specializationId.id as specializationId," +            //[7]
                " dd.specializationId.name as specializationName," +        //[8]
                " dd.rosterGapDuration as rosterGapDuration" +              //[9]
                " FROM DoctorDutyRosterOverride d" +
                " LEFT JOIN DoctorDutyRoster dd ON dd.id = d.doctorDutyRosterId.id" +
                " WHERE" +
                " d.status = 'Y'" +
                " AND dd.status = 'Y'" +
                " AND dd.toDate >=:fromDate" +
                " AND dd.fromDate <=:toDate";

        if (!Objects.isNull(requestDTO.getDoctorId()))
            SQL += " AND dd.doctorId.id = :doctorId";

        if (!Objects.isNull(requestDTO.getSpecializationId()))
            SQL += " AND dd.specializationId.id = :specializationId";

        Query query = getQuery.apply(testEntityManager, SQL)
                .setParameter(FROM_DATE, requestDTO.getFromDate())
                .setParameter(TO_DATE, requestDTO.getToDate());

        if (!Objects.isNull(requestDTO.getDoctorId()))
            query.setParameter(DOCTOR_ID, requestDTO.getDoctorId());

        if (!Objects.isNull(requestDTO.getSpecializationId()))
            query.setParameter(SPECIALIZATION_ID, requestDTO.getSpecializationId());

        List<Object[]> results = query.getResultList();
    }

    @Test
    public void fetchDoctorDutyRosterStatus() {

        DoctorDutyRosterStatusRequestDTO requestDTO = getDoctorDutyRosterStatusRequestDTO();

        String SQL = "SELECT" +
                " d.from_date," +                                       //[0]
                " d.to_date," +                                         //[1]
                " GROUP_CONCAT((CONCAT(" +
                " DATE_FORMAT(dw.start_time, '%H:%i')," +
                " '-'," +
                " DATE_FORMAT(dw.end_time, '%H:%i'), " +
                " '-'," +
                " dw.day_off_status," +
                " '-'," +
                " w.name))) as doctorTimeDetails," +                      //[2]
                " dr.id as doctorId, " +                                  //[3]
                " dr.name as doctorName," +                               //[4]
                " s.id as specializationId," +                            //[5]
                " s.name as specializationName," +                        //[6]
                " d.roster_gap_duration as rosterGapDuration" +           //[7]
                " FROM doctor_duty_roster d" +
                " LEFT JOIN doctor_week_days_duty_roster dw ON d.id = dw.doctor_duty_roster_id" +
                " LEFT JOIN week_days w ON w.id = dw.week_days_id" +
                " LEFT JOIN doctor dr ON dr.id = d.doctor_id" +
                " LEFT JOIN specialization s ON s.id = d.specialization_id" +
                " WHERE d.status = 'Y'" +
                " AND d.to_date >=:fromDate" +
                " AND d.from_date <=:toDate";

        if (!Objects.isNull(requestDTO.getDoctorId()))
            SQL += " AND dr.id = :doctorId";

        if (!Objects.isNull(requestDTO.getSpecializationId()))
            SQL += " AND s.id = :specializationId";

        SQL += " GROUP BY d.id";

        LocalDate fromLocalDate = new java.sql.Date(requestDTO.getFromDate().getTime()).toLocalDate();
        LocalDate toLocalDate = new java.sql.Date(requestDTO.getToDate().getTime()).toLocalDate();

        Query query = getNativeQuery.apply(testEntityManager, SQL)
                .setParameter(FROM_DATE, requestDTO.getFromDate())
                .setParameter(TO_DATE, requestDTO.getToDate());

        if (!Objects.isNull(requestDTO.getDoctorId()))
            query.setParameter(DOCTOR_ID, requestDTO.getDoctorId());

        if (!Objects.isNull(requestDTO.getSpecializationId()))
            query.setParameter(SPECIALIZATION_ID, requestDTO.getSpecializationId());

        List<Object[]> results = query.getResultList();
    }
}

