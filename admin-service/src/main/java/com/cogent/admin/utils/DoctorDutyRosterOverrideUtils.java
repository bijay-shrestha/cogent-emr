package com.cogent.admin.utils;

import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterOverrideRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterOverrideUpdateRequestDTO;
import com.cogent.admin.dto.response.doctorDutyRoster.DoctorDutyRosterStatusResponseDTO;
import com.cogent.persistence.model.DoctorDutyRoster;
import com.cogent.persistence.model.DoctorDutyRosterOverride;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static com.cogent.admin.constants.StatusConstants.NO;
import static com.cogent.admin.utils.DateUtils.convertDateToLocalDate;

/**
 * @author smriti ON 16/12/2019
 */

public class DoctorDutyRosterOverrideUtils {

    public static DoctorDutyRosterOverride parseToDoctorDutyRosterOverride(
            DoctorDutyRosterOverrideRequestDTO requestDTO,
            DoctorDutyRoster doctorDutyRoster,
            DoctorDutyRosterOverride doctorDutyRosterOverride) {

        doctorDutyRosterOverride.setFromDate(requestDTO.getFromDate());
        doctorDutyRosterOverride.setToDate(requestDTO.getToDate());
        doctorDutyRosterOverride.setStartTime(requestDTO.getStartTime());
        doctorDutyRosterOverride.setEndTime(requestDTO.getEndTime());
        doctorDutyRosterOverride.setDayOffStatus(requestDTO.getDayOffStatus());
        doctorDutyRosterOverride.setStatus(requestDTO.getStatus());
        doctorDutyRosterOverride.setRemarks(requestDTO.getRemarks());
        doctorDutyRosterOverride.setDoctorDutyRosterId(doctorDutyRoster);
        return doctorDutyRosterOverride;
    }

    public static DoctorDutyRosterOverride parseToUpdatedDoctorDutyRosterOverride(
            DoctorDutyRosterOverrideUpdateRequestDTO updateRequestDTO,
            DoctorDutyRoster doctorDutyRoster,
            DoctorDutyRosterOverride doctorDutyRosterOverride) {

        doctorDutyRosterOverride.setFromDate(updateRequestDTO.getOverrideFromDate());
        doctorDutyRosterOverride.setToDate(updateRequestDTO.getOverrideToDate());
        doctorDutyRosterOverride.setEndTime(updateRequestDTO.getEndTime());
        doctorDutyRosterOverride.setStartTime(updateRequestDTO.getStartTime());
        doctorDutyRosterOverride.setDayOffStatus(updateRequestDTO.getDayOffStatus());
        doctorDutyRosterOverride.setStatus(updateRequestDTO.getStatus());
        doctorDutyRosterOverride.setRemarks(updateRequestDTO.getRemarks());
        doctorDutyRosterOverride.setDoctorDutyRosterId(doctorDutyRoster);
        return doctorDutyRosterOverride;
    }

    public static void updateDutyRosterOverrideStatus(List<DoctorDutyRosterOverride> doctorDutyRosterOverrides) {
        doctorDutyRosterOverrides.forEach(doctorDutyRosterOverride -> doctorDutyRosterOverride.setStatus(NO));
    }

    public static List<DoctorDutyRosterStatusResponseDTO> parseQueryResultToDoctorDutyRosterStatusResponseDTO
            (List<Object[]> results,
             Date searchFromDate,
             Date searchToDate) {

        LocalDate searchFromLocalDate = convertDateToLocalDate(searchFromDate);
        LocalDate searchToLocalDate = convertDateToLocalDate(searchToDate);

        List<DoctorDutyRosterStatusResponseDTO> doctorDutyRosterStatusResponseDTOS = new ArrayList<>();

        results.forEach(result -> {

            final int FROM_DATE_INDEX = 0;
            final int TO_DATE_INDEX = 1;
            final int START_TIME_INDEX = 2;
            final int END_TIME_INDEX = 3;
            final int DAY_OFF_STATUS_INDEX = 4;
            final int DOCTOR_ID_INDEX = 5;
            final int DOCTOR_NAME_INDEX = 6;
            final int SPECIALIZATION_ID_INDEX = 7;
            final int SPECIALIZATION_NAME_INDEX = 8;
            final int ROSTER_GAP_DURATION_INDEX = 9;

            LocalDate startLocalDate = convertDateToLocalDate((Date) result[FROM_DATE_INDEX]);
            LocalDate endLocalDate = convertDateToLocalDate((Date) result[TO_DATE_INDEX]);

            Stream.iterate(startLocalDate, date -> date.plusDays(1))
                    .limit(ChronoUnit.DAYS.between(startLocalDate, endLocalDate) + 1)
                    .forEach(localDate -> {

                        if (DateUtils.isLocalDateBetweenInclusive(searchFromLocalDate, searchToLocalDate, localDate)) {

                            DoctorDutyRosterStatusResponseDTO responseDTO =
                                    DoctorDutyRosterStatusResponseDTO.builder()
                                            .date(localDate)
                                            .startTime(result[START_TIME_INDEX].toString())
                                            .endTime(result[END_TIME_INDEX].toString())
                                            .rosterGapDuration(Integer.parseInt(result[ROSTER_GAP_DURATION_INDEX].toString()))
                                            .dayOffStatus(result[DAY_OFF_STATUS_INDEX].toString().charAt(0))
                                            .doctorId(Long.parseLong(result[DOCTOR_ID_INDEX].toString()))
                                            .doctorName(result[DOCTOR_NAME_INDEX].toString())
                                            .specializationId(Long.parseLong(result[SPECIALIZATION_ID_INDEX].toString()))
                                            .specializationName(result[SPECIALIZATION_NAME_INDEX].toString())
                                            .build();

                            doctorDutyRosterStatusResponseDTOS.add(responseDTO);
                        }
                    });
        });

        return doctorDutyRosterStatusResponseDTOS;
    }
}
