package com.cogent.admin.repository;

import com.cogent.admin.constants.QueryConstants;
import com.cogent.admin.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;

/**
 * @author smriti on 27/11/2019
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DoctorDutyRosterRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void validateDoctorDutyRosterCount() {
        String sql = " SELECT COUNT(d.id)" +
                " FROM DoctorDutyRoster d" +
                " WHERE d.status != 'D'" +
                " AND d.doctorId.id=:doctorId" +
                " AND d.specializationId.id= :specializationId";

        Query query = getQuery.apply(testEntityManager, sql)
                .setParameter(DOCTOR_ID, 1L)
                .setParameter(SPECIALIZATION_ID, 1L);

        Long result = (Long) query.getSingleResult();

        System.out.println(result);
    }

    @Test
    public void searchDoctorDutyRoster() {
        String sql = " SELECT" +
                " ddr.id as id," +
                " d.name as doctorName," +
                " s.name as specializationName," +
                " dt.name as doctorTypeName," +
                " ddr.rosterGapDuration as rosterGapDuration," +
                " ddr.fromDate as fromDate," +
                " ddr.toDate as toDate," +
                " ddr.status as status" +
                " FROM DoctorDutyRoster ddr" +
                " LEFT JOIN Doctor d ON ddr.doctorId.id = d.id" +
                " LEFT JOIN Specialization s ON ddr.specializationId.id = s.id" +
                " LEFT JOIN DoctorType dt ON ddr.doctorTypeId.id = dt.id" +
                " WHERE ddr.status != 'D'";

        Query query = getQuery.apply(testEntityManager, sql);

        List results = query.getResultList();

        System.out.println(results);
    }

    @Test
    public void fetchDoctorWeekDaysDutyRoster() {
        String sql = " SELECT" +
                " dw.id as id," +
                " dw.startTime as startTime," +
                " dw.endTime as endTime," +
                " dw.dayOffStatus as dayOffStatus," +
                " dw.weekDaysId.id as weekDaysId," +
                " dw.weekDaysId.name " +
                " FROM DoctorWeekDaysDutyRoster dw" +
                " WHERE dw.doctorDutyRosterId.id = 1";

        Query query = getQuery.apply(testEntityManager, sql);

        List results = query.getResultList();

        System.out.println(results);
    }

    @Test
    public void fetchDoctorDutyRosterDetailsForDetailModal() {

        String sql = " SELECT" +
                " ddr.id as id," +                                                  //[0]
                " d.id as doctorId," +                                               //[1]
                " d.name as doctorName," +                                          //[2]
                " s.id as specializationId," +                                      //[3]
                " s.name as specializationName," +                                  //[4]
                " dt.id as doctorTypeId," +                                          //[5]
                " dt.name as doctorTypeName," +                                     //[6]
                " ddr.rosterGapDuration as rosterGapDuration," +                    //[7]
                " ddr.fromDate as fromDate," +                                      //[8]
                " ddr.toDate as toDate," +                                          //[9]
                " ddr.status as status," +                                          //[10]
                " ddr.remarks as remarks" +                                         //[11]
                " FROM DoctorDutyRoster ddr" +
                " LEFT JOIN Doctor d ON ddr.doctorId.id = d.id" +
                " LEFT JOIN Specialization s ON ddr.specializationId.id = s.id" +
                " LEFT JOIN DoctorType dt ON ddr.doctorTypeId.id = dt.id" +
                " WHERE ddr.status !='D'" +
                " AND ddr.id = 1";

        Query query = getQuery.apply(testEntityManager, sql);

        List results = query.getResultList();

        System.out.println(results);
    }

    @Test
    public void getDayFromDate() {

        String sql = "SELECT fromDate FROM DoctorDutyRoster WHERE id =  1";

        Query query = getQuery.apply(testEntityManager, sql);

        Date date = (Date) query.getSingleResult();

        LocalDate localDate = DateUtils.convertDateToLocalDate(date);

        int week = localDate.getDayOfWeek().getValue();

        System.out.println(localDate.getDayOfWeek());

        DateFormat dateFormat = new SimpleDateFormat("EE");
        String day = dateFormat.format(date);
        System.out.println(day);
    }


    @Test
    public void fetchDoctorDutyRoster() throws ParseException {

        String str = "2019-12-01";
        java.sql.Date date = java.sql.Date.valueOf(str);//converting string into sql date.

        String day = DateUtils.getDayCodeFromDate(date);

        String sql = " SELECT" +
                " dw.startTime as startTime," +
                " dw.endTime as endTime," +
                " dw.dayOffStatus as dayOffStatus" +
                " FROM DoctorDutyRoster d" +
                " LEFT JOIN DoctorWeekDaysDutyRoster dw ON dw.doctorDutyRosterId.id = d.id" +
                " LEFT JOIN WeekDays w ON w.id = dw.weekDaysId.id" +
                " WHERE" +
                " d.status = 'Y'" +
                " AND :date BETWEEN d.fromDate AND d.toDate" +
                " AND d.doctorId.id = :doctorId" +
                " AND d.specializationId.id = :specializationId" +
                " AND w.code = :code";

        Query query = getQuery.apply(testEntityManager, sql)
                .setParameter(QueryConstants.DATE, date)
                .setParameter(DOCTOR_ID, 1L)
                .setParameter(SPECIALIZATION_ID, 2L)
                .setParameter(CODE, day);

        List result = query.getResultList();

        System.out.println(result);
    }


}
