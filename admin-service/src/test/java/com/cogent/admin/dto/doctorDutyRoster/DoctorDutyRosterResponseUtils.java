package com.cogent.admin.dto.doctorDutyRoster;

import com.cogent.admin.dto.response.doctorDutyRoster.DoctorDutyRosterDetailResponseDTO;
import com.cogent.admin.dto.response.doctorDutyRoster.DoctorDutyRosterMinimalResponseDTO;
import com.cogent.admin.dto.response.doctorDutyRoster.DoctorDutyRosterResponseDTO;
import com.cogent.admin.dto.response.doctorDutyRoster.DoctorWeekDaysDutyRosterResponseDTO;
import com.cogent.persistence.model.DoctorDutyRoster;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.cogent.admin.dto.doctor.DoctorResponseUtils.getDoctor;
import static com.cogent.admin.dto.doctorType.DoctorTypeResponseUtils.fetchDoctorType;
import static com.cogent.admin.dto.specialization.SpecializationResponseUtils.getSpecialization;

/**
 * @author smriti on 26/11/2019
 */
public class DoctorDutyRosterResponseUtils {

    public static DoctorDutyRoster fetchDoctorDutyRoster() {
        return new DoctorDutyRoster(1L, getDoctor(), getSpecialization(), fetchDoctorType(),
                15, new Date(), new Date(), 'Y', 'Y', null);
    }

    public static List<DoctorDutyRosterMinimalResponseDTO> fetchDoctorDutyRosterMinimalResponseDTO() {
        return Arrays.asList(
                DoctorDutyRosterMinimalResponseDTO.builder()
                        .id(1L)
                        .doctorName("DR.SANJEEV UPRETI")
                        .specializationName("SURGEON")
                        .doctorTypeName("PRINCIPLE DOCTOR")
                        .rosterGapDuration(15)
                        .fromDate(new Date())
                        .toDate(new Date())
                        .status('Y')
                        .build());
    }

    public static DoctorDutyRosterDetailResponseDTO fetchDoctorDutyRosterDetailResponseDTO() {
        return DoctorDutyRosterDetailResponseDTO.builder()
                .doctorDutyRosterResponseDTO(fetchDoctorDutyRosterResponseDTO())
                .weekDaysDutyRosterResponseDTOS(fetchDoctorWeekDaysDutyRosterResponseDTO())
                .build();
    }

    private static DoctorDutyRosterResponseDTO fetchDoctorDutyRosterResponseDTO() {
        return DoctorDutyRosterResponseDTO.builder()
                .id(1L)
                .doctorName("DR.SANJEEV UPRETI")
                .specializationName("SURGEON")
                .doctorTypeName("PRINCIPLE DOCTOR")
                .rosterGapDuration(15)
                .fromDate(new Date())
                .toDate(new Date())
                .status('Y')
                .remarks(null)
                .build();
    }

    private static List<DoctorWeekDaysDutyRosterResponseDTO> fetchDoctorWeekDaysDutyRosterResponseDTO() {

        return Arrays.asList(
                DoctorWeekDaysDutyRosterResponseDTO.builder()
                        .doctorWeekDaysDutyRosterId(1L)
                        .startTime(Calendar.getInstance().getTime())
                        .endTime(Calendar.getInstance().getTime())
                        .weekDaysId(1L)
                        .weekDaysName("SUNDAY")
                        .dayOffStatus('N')
                        .build(),
                DoctorWeekDaysDutyRosterResponseDTO.builder()
                        .doctorWeekDaysDutyRosterId(2L)
                        .startTime(Calendar.getInstance().getTime())
                        .endTime(Calendar.getInstance().getTime())
                        .weekDaysId(2L)
                        .weekDaysName("MONDAY")
                        .dayOffStatus('N')
                        .build()
        );
    }
}
