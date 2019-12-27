package com.cogent.admin.utils;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterUpdateRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorWeekDaysDutyRosterRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorWeekDaysDutyRosterUpdateRequestDTO;
import com.cogent.admin.dto.response.doctorDutyRoster.*;
import com.cogent.admin.feign.dto.request.appointment.AppointmentCountRequestDTO;
import com.cogent.persistence.model.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.cogent.admin.constants.StringConstant.COMMA_SEPARATED;
import static com.cogent.admin.constants.StringConstant.HYPHEN;
import static com.cogent.admin.utils.DateUtils.convertDateToLocalDate;

/**
 * @author smriti on 27/11/2019
 */
public class DoctorDutyRosterUtils {

    public static DoctorDutyRoster parseToDoctorDutyRoster(DoctorDutyRosterRequestDTO requestDTO,
                                                           Doctor doctor,
                                                           Specialization specialization,
                                                           DoctorType doctorType) {

        DoctorDutyRoster doctorDutyRoster = new DoctorDutyRoster();
        doctorDutyRoster.setFromDate(requestDTO.getFromDate());
        doctorDutyRoster.setToDate(requestDTO.getToDate());
        doctorDutyRoster.setRosterGapDuration(requestDTO.getRosterGapDuration());
        doctorDutyRoster.setStatus(requestDTO.getStatus());
        doctorDutyRoster.setHasOverrideDutyRoster(requestDTO.getHasOverrideDutyRoster());
        parseToDoctorDutyRoster(doctorDutyRoster, doctor, specialization, doctorType);
        return doctorDutyRoster;
    }

    private static void parseToDoctorDutyRoster(DoctorDutyRoster doctorDutyRoster,
                                                Doctor doctor,
                                                Specialization specialization,
                                                DoctorType doctorType) {
        doctorDutyRoster.setDoctorId(doctor);
        doctorDutyRoster.setDoctorTypeId(doctorType);
        doctorDutyRoster.setSpecializationId(specialization);
    }


    public static DoctorWeekDaysDutyRoster parseToDoctorWeekDaysDutyRoster(DoctorWeekDaysDutyRosterRequestDTO requestDTO,
                                                                           DoctorDutyRoster doctorDutyRoster,
                                                                           WeekDays weekDays,
                                                                           DoctorWeekDaysDutyRoster weekDaysDutyRoster) {

        weekDaysDutyRoster.setStartTime(requestDTO.getStartTime());
        weekDaysDutyRoster.setEndTime(requestDTO.getEndTime());
        weekDaysDutyRoster.setDayOffStatus(requestDTO.getDayOffStatus());
        weekDaysDutyRoster.setDoctorDutyRosterId(doctorDutyRoster);
        weekDaysDutyRoster.setWeekDaysId(weekDays);
        return weekDaysDutyRoster;
    }

    public static void parseToUpdatedDoctorDutyRoster(DoctorDutyRoster doctorDutyRoster,
                                                      DoctorDutyRosterUpdateRequestDTO updateRequestDTO) {

        doctorDutyRoster.setRosterGapDuration(updateRequestDTO.getRosterGapDuration());
        doctorDutyRoster.setStatus(updateRequestDTO.getStatus());
        doctorDutyRoster.setRemarks(updateRequestDTO.getRemarks());
        doctorDutyRoster.setHasOverrideDutyRoster(updateRequestDTO.getHasOverrideDutyRoster());
    }

    public static DoctorWeekDaysDutyRoster parseToUpdatedDoctorWeekDaysDutyRoster(
            DoctorWeekDaysDutyRosterUpdateRequestDTO updateRequestDTO,
            DoctorDutyRoster doctorDutyRoster,
            WeekDays weekDays) {

        DoctorWeekDaysDutyRoster weekDaysDutyRoster = new DoctorWeekDaysDutyRoster();
        weekDaysDutyRoster.setId(updateRequestDTO.getDoctorWeekDaysDutyRosterId());
        weekDaysDutyRoster.setStartTime(updateRequestDTO.getStartTime());
        weekDaysDutyRoster.setEndTime(updateRequestDTO.getEndTime());
        weekDaysDutyRoster.setDayOffStatus(updateRequestDTO.getDayOffStatus());
        weekDaysDutyRoster.setDoctorDutyRosterId(doctorDutyRoster);
        weekDaysDutyRoster.setWeekDaysId(weekDays);
        return weekDaysDutyRoster;
    }

    public static void convertToDeletedDoctorDutyRoster(DoctorDutyRoster doctorDutyRoster,
                                                        DeleteRequestDTO deleteRequestDTO) {
        doctorDutyRoster.setStatus(deleteRequestDTO.getStatus());
        doctorDutyRoster.setRemarks(deleteRequestDTO.getRemarks());
    }

    public static DoctorDutyRosterDetailResponseDTO parseToDoctorDutyRosterDetailResponseDTO(
            DoctorDutyRosterResponseDTO dutyRosterResponseDTO,
            List<DoctorWeekDaysDutyRosterResponseDTO> weekDaysDutyRosterResponseDTOS,
            List<DoctorDutyRosterOverrideResponseDTO> doctorDutyRosterOverrideResponseDTOS) {

        return DoctorDutyRosterDetailResponseDTO.builder()
                .doctorDutyRosterResponseDTO(dutyRosterResponseDTO)
                .weekDaysDutyRosterResponseDTOS(weekDaysDutyRosterResponseDTOS)
                .doctorDutyRosterOverrideResponseDTOS(doctorDutyRosterOverrideResponseDTOS)
                .build();
    }

    public static List<DoctorDutyRosterStatusResponseDTO> parseQueryResultToDoctorDutyRosterStatusResponseDTOS(
            List<Object[]> queryResults,
            Date searchFromDate,
            Date searchToDate) {

        List<DoctorDutyRosterStatusResponseDTO> doctorDutyRosterStatusResponseDTOS = new ArrayList<>();

        LocalDate searchFromLocalDate = convertDateToLocalDate(searchFromDate);
        LocalDate searchToLocalDate = convertDateToLocalDate(searchToDate);

        queryResults.forEach(result -> {

            final int START_DATE_INDEX = 0;
            final int END_DATE_INDEX = 1;
            final int DOCTOR_TIME_DETAILS_INDEX = 2;
            final int DOCTOR_ID_INDEX = 3;
            final int DOCTOR_NAME_INDEX = 4;
            final int SPECIALIZATION_ID_INDEX = 5;
            final int SPECIALIZATION_NAME_INDEX = 6;
            final int ROSTER_GAP_DURATION_INDEX = 7;

            LocalDate startLocalDate = convertDateToLocalDate((Date) result[START_DATE_INDEX]);
            LocalDate endLocalDate = convertDateToLocalDate((Date) result[END_DATE_INDEX]);

            List<String> timeDetails = Arrays.asList(result[DOCTOR_TIME_DETAILS_INDEX].toString()
                    .split(COMMA_SEPARATED));

            Stream.iterate(startLocalDate, date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1)
                    .forEach(localDate -> {

                        if (DateUtils.isLocalDateBetweenInclusive(searchFromLocalDate, searchToLocalDate, localDate)) {

                            String dayOfWeek = localDate.getDayOfWeek().toString();

                            String weekMatched = timeDetails.stream()
                                    .filter(str -> str.contains(dayOfWeek))
                                    .findAny().orElse(null);

                            if (!Objects.isNull(weekMatched)) {

                                /*START TIME - END TIME - DAY OFF STATUS - WEEK NAME*/
                                String[] weekMatchedSplit = weekMatched.split(HYPHEN);

                                DoctorDutyRosterStatusResponseDTO responseDTO = DoctorDutyRosterStatusResponseDTO.builder()
                                        .date(localDate)
                                        .startTime(weekMatchedSplit[0])
                                        .endTime(weekMatchedSplit[1])
                                        .dayOffStatus(weekMatchedSplit[2].charAt(0))
                                        .doctorId(Long.parseLong(result[DOCTOR_ID_INDEX].toString()))
                                        .doctorName(result[DOCTOR_NAME_INDEX].toString())
                                        .specializationId(Long.parseLong(result[SPECIALIZATION_ID_INDEX].toString()))
                                        .specializationName(result[SPECIALIZATION_NAME_INDEX].toString())
                                        .rosterGapDuration(Integer.parseInt(result[ROSTER_GAP_DURATION_INDEX].toString()))
                                        .build();

                                doctorDutyRosterStatusResponseDTOS.add(responseDTO);
                            }
                        }
                    });
        });

        return doctorDutyRosterStatusResponseDTOS;
    }

    public static List<DoctorDutyRosterStatusResponseDTO> mergeOverrideAndActualDoctorDutyRoster(
            List<DoctorDutyRosterStatusResponseDTO> doctorDutyRosterOverrideStatus,
            List<DoctorDutyRosterStatusResponseDTO> doctorDutyRosterStatus) {

        /*COUNT <1 WILL RETURN UNMATCHED VALUES AND COUNT > 0 WILL RETURN MATCHED VALUES*/
        /*FETCH ONLY THOSE DOCTOR DUTY ROSTER EXCEPT OVERRIDE DUTY ROSTER
        BY COMPARING DATE, DOCTOR ID AND SPECIALIZATION ID*/
        List<DoctorDutyRosterStatusResponseDTO> unmatchedList = doctorDutyRosterStatus.stream()
                .filter(rosterStatus -> (doctorDutyRosterOverrideStatus.stream()
                        .filter(overrideStatus -> (overrideStatus.getDate().equals(rosterStatus.getDate()))
                                && (overrideStatus.getDoctorId().equals(rosterStatus.getDoctorId()))
                                && (overrideStatus.getSpecializationId().equals(rosterStatus.getSpecializationId())))
                        .count()) < 1)
                .collect(Collectors.toList());

        /*MERGE DUTY ROSTER LIST (UNMATCHED LIST) WITH REMAINING OVERRIDE DUTY ROSTER LIST  */
        doctorDutyRosterOverrideStatus.addAll(unmatchedList);

        /*SORT BY DATE*/
        doctorDutyRosterOverrideStatus.sort(Comparator.comparing(DoctorDutyRosterStatusResponseDTO::getDate));

        return doctorDutyRosterOverrideStatus;
    }

    public static AppointmentCountRequestDTO parseToAppointmentCountRequestTO(
            Date fromDate, Date toDate, Long doctorId, Long specializationId) {

        AppointmentCountRequestDTO appointmentCountRequestDTO = new AppointmentCountRequestDTO();
        appointmentCountRequestDTO.setFromDate(fromDate);
        appointmentCountRequestDTO.setToDate(toDate);
        appointmentCountRequestDTO.setDoctorId(doctorId);
        appointmentCountRequestDTO.setSpecializationId(specializationId);
        return appointmentCountRequestDTO;
    }

    public static List<DoctorWeekDaysDutyRosterUpdateRequestDTO> filterOriginalAndUpdatedWeekDaysRoster(
            List<DoctorWeekDaysDutyRosterUpdateRequestDTO> updateRequestDTO,
            List<DoctorWeekDaysDutyRoster> weekDaysDutyRosters) {

        return updateRequestDTO
                .stream()
                .filter(weekDaysDutyRosterUpdateRequestDTO -> (weekDaysDutyRosters
                        .stream()
                        .filter(originalRoster -> (
                                (weekDaysDutyRosterUpdateRequestDTO.getStartTime()
                                        .equals(originalRoster.getStartTime())
                                        && (weekDaysDutyRosterUpdateRequestDTO.getEndTime()
                                        .equals(originalRoster.getEndTime()))
                                        && (weekDaysDutyRosterUpdateRequestDTO.getDayOffStatus()
                                        .equals(originalRoster.getDayOffStatus()))
                                        && (weekDaysDutyRosterUpdateRequestDTO.getWeekDaysId()
                                        .equals(originalRoster.getWeekDaysId().getId())))))
                        .count()) < 1)
                .collect(Collectors.toList());
    }
}
