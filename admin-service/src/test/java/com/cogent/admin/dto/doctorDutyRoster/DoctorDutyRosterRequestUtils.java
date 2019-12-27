package com.cogent.admin.dto.doctorDutyRoster;

import com.cogent.admin.dto.request.doctorDutyRoster.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author smriti on 26/11/2019
 */
public class DoctorDutyRosterRequestUtils {

    public static DoctorDutyRosterRequestDTO getDoctorDutyRosterRequestDTO() {

        return DoctorDutyRosterRequestDTO.builder()
                .doctorId(1L)
                .doctorTypeId(1L)
                .specializationId(1L)
                .rosterGapDuration(15)
                .fromDate(new Date())
                .toDate(new Date())
                .status('Y')
                .hasOverrideDutyRoster('Y')
                .doctorWeekDaysDutyRosterRequestDTOS(getDoctorWeekDaysRosterRequestDTOList())
                .doctorDutyRosterOverrideRequestDTOS(getDoctorDutyRosterOverrideRequestDTOS())
                .build();
    }

    private static List<DoctorWeekDaysDutyRosterRequestDTO> getDoctorWeekDaysRosterRequestDTOList() {
        return Arrays.asList(
                DoctorWeekDaysDutyRosterRequestDTO.builder()
                        .weekDaysId(1L)
                        .endTime(Calendar.getInstance().getTime())
                        .startTime(Calendar.getInstance().getTime())
                        .dayOffStatus('N')
                        .build(),
                DoctorWeekDaysDutyRosterRequestDTO.builder()
                        .weekDaysId(2L)
                        .endTime(Calendar.getInstance().getTime())
                        .startTime(Calendar.getInstance().getTime())
                        .dayOffStatus('N')
                        .build()
        );
    }

    private static DoctorWeekDaysDutyRosterRequestDTO getDoctorWeekDaysRosterRequestDTO() {
        return DoctorWeekDaysDutyRosterRequestDTO.builder()
                .weekDaysId(1L)
                .endTime(Calendar.getInstance().getTime())
                .startTime(Calendar.getInstance().getTime())
                .dayOffStatus('N')
                .build();
    }

    private static List<DoctorDutyRosterOverrideRequestDTO> getDoctorDutyRosterOverrideRequestDTOS() {
        return Arrays.asList(
                DoctorDutyRosterOverrideRequestDTO.builder()
                        .fromDate(new Date())
                        .toDate(new Date())
                        .endTime(Calendar.getInstance().getTime())
                        .startTime(Calendar.getInstance().getTime())
                        .dayOffStatus('N')
                        .status('Y')
                        .build()
        );
    }

    private static DoctorDutyRosterOverrideRequestDTO getDoctorDutyRosterOverrideRequestDTO() {
        return DoctorDutyRosterOverrideRequestDTO.builder()
                .fromDate(new Date())
                .toDate(new Date())
                .endTime(Calendar.getInstance().getTime())
                .startTime(Calendar.getInstance().getTime())
                .dayOffStatus('N')
                .status('Y')
                .build();
    }

    public static DoctorDutyRosterUpdateRequestDTO getDoctorDutyRosterUpdateRequestDTO() {
        return DoctorDutyRosterUpdateRequestDTO.builder()
                .doctorDutyRosterId(1L)
                .rosterGapDuration(10)
                .status('Y')
                .remarks("update")
                .hasOverrideDutyRoster('Y')
                .weekDaysDutyRosterUpdateRequestDTOS(getDoctorWeekDaysDutyRosterUpdateRequestDTOS())
                .build();
    }

    private static List<DoctorWeekDaysDutyRosterUpdateRequestDTO> getDoctorWeekDaysDutyRosterUpdateRequestDTOS() {
        return Arrays.asList(
                DoctorWeekDaysDutyRosterUpdateRequestDTO.builder()
                        .doctorWeekDaysDutyRosterId(1L)
                        .weekDaysId(1L)
                        .endTime(Calendar.getInstance().getTime())
                        .startTime(Calendar.getInstance().getTime())
                        .dayOffStatus('N')
                        .build());
    }

    private static List<DoctorDutyRosterOverrideUpdateRequestDTO> getDoctorDutyRosterOverrrideUpdateRequestDTOS() {
        return Arrays.asList(
                DoctorDutyRosterOverrideUpdateRequestDTO.builder()
                        .doctorDutyRosterOverrideId(1L)
                        .overrideFromDate(new Date())
                        .overrideToDate(new Date())
                        .endTime(Calendar.getInstance().getTime())
                        .startTime(Calendar.getInstance().getTime())
                        .dayOffStatus('N')
                        .status('Y')
                        .build());
    }

    public static DoctorDutyRosterSearchRequestDTO getDoctorDutyRosterSearchRequestDTO() {
        return DoctorDutyRosterSearchRequestDTO.builder()
                .doctorId(1L)
                .specializationId(1L)
                .fromDate(null)
                .toDate(null)
                .build();
    }

    public static DoctorDutyRosterStatusRequestDTO getDoctorDutyRosterStatusRequestDTO() {

        java.sql.Date fromDate = java.sql.Date.valueOf("2019-12-01");
        java.sql.Date toDate = java.sql.Date.valueOf("2019-12-29");

        return DoctorDutyRosterStatusRequestDTO.builder()
                .fromDate(fromDate)
                .toDate(toDate)
                .doctorId(null)
                .specializationId(null)
                .build();
    }
}
