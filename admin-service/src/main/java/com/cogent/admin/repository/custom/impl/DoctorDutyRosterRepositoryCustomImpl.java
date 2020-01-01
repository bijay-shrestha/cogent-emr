package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterSearchRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterStatusRequestDTO;
import com.cogent.admin.dto.request.doctorDutyRoster.DoctorDutyRosterTimeRequestDTO;
import com.cogent.admin.dto.response.doctorDutyRoster.*;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.DoctorDutyRosterRepositoryCustom;
import com.cogent.admin.utils.DoctorDutyRosterUtils;
import com.cogent.persistence.model.DoctorDutyRoster;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.constants.StatusConstants.YES;
import static com.cogent.admin.query.DoctorDutyRosterOverrideQuery.QUERY_TO_FETCH_DOCTOR_DUTY_ROSTER_OVERRIDE_DETAILS;
import static com.cogent.admin.query.DoctorDutyRosterQuery.*;
import static com.cogent.admin.utils.DateUtils.getDayCodeFromDate;
import static com.cogent.admin.utils.DateUtils.utilDateToSqlDate;
import static com.cogent.admin.utils.DoctorDutyRosterUtils.parseToDoctorDutyRosterDetailResponseDTO;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 26/11/2019
 */
@Repository
@Transactional(readOnly = true)
public class DoctorDutyRosterRepositoryCustomImpl implements DoctorDutyRosterRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long validateDoctorDutyRosterCount(Long doctorId,
                                              Long specializationId,
                                              Date fromDate,
                                              Date toDate) {

        Query query = createQuery.apply(entityManager, VALIDATE_DOCTOR_DUTY_ROSTER_COUNT)
                .setParameter(DOCTOR_ID, doctorId)
                .setParameter(SPECIALIZATION_ID, specializationId)
                .setParameter(FROM_DATE, utilDateToSqlDate(fromDate))
                .setParameter(TO_DATE, utilDateToSqlDate(toDate));

        return (Long) query.getSingleResult();
    }

    @Override
    public List<DoctorDutyRosterMinimalResponseDTO> search(DoctorDutyRosterSearchRequestDTO searchRequestDTO,
                                                           Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_DOCTOR_DUTY_ROSTER(searchRequestDTO))
                .setParameter(FROM_DATE, searchRequestDTO.getFromDate())
                .setParameter(TO_DATE, searchRequestDTO.getToDate());

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<DoctorDutyRosterMinimalResponseDTO> results = transformQueryToResultList(
                query, DoctorDutyRosterMinimalResponseDTO.class);

        if (results.isEmpty()) throw DOCTOR_DUTY_ROSTER_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public DoctorDutyRosterDetailResponseDTO fetchDetailsById(Long doctorDutyRosterId) {

        DoctorDutyRosterResponseDTO doctorDutyRosterResponseDTO =
                fetchDoctorDutyRosterDetails(doctorDutyRosterId);

        List<DoctorWeekDaysDutyRosterResponseDTO> weekDaysDutyRosterResponseDTO =
                fetchDoctorWeekDaysDutyRosterResponseDTO(doctorDutyRosterId);

        List<DoctorDutyRosterOverrideResponseDTO> overrideResponseDTOS =
                doctorDutyRosterResponseDTO.getHasOverrideDutyRoster().equals(YES)
                        ? fetchDoctorDutyRosterOverrideResponseDTO(doctorDutyRosterId) : new ArrayList<>();

        return parseToDoctorDutyRosterDetailResponseDTO(
                doctorDutyRosterResponseDTO, weekDaysDutyRosterResponseDTO, overrideResponseDTOS);
    }

    @Override
    public DoctorDutyRosterTimeResponseDTO fetchDoctorDutyRosterTime(
            DoctorDutyRosterTimeRequestDTO requestDTO) {

        Date date = utilDateToSqlDate(requestDTO.getDate());
        String code = getDayCodeFromDate(date);

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_DOCTOR_DUTY_ROSTER_TIME)
                .setParameter(DATE, date)
                .setParameter(DOCTOR_ID, requestDTO.getDoctorId())
                .setParameter(SPECIALIZATION_ID, requestDTO.getSpecializationId())
                .setParameter(CODE, getDayCodeFromDate(date));

        try {
            return transformQueryToSingleResult(query, DoctorDutyRosterTimeResponseDTO.class);
        } catch (NoResultException e) {
            throw DOCTOR_DUTY_ROSTER_NOT_FOUND.get();
        }
    }

    @Override
    public List<DoctorDutyRosterStatusResponseDTO> fetchDoctorDutyRosterStatus(
            DoctorDutyRosterStatusRequestDTO requestDTO) {

        Query query = createNativeQuery.apply(entityManager, QUERY_TO_FETCH_DOCTOR_DUTY_ROSTER_STATUS(requestDTO))
                .setParameter(FROM_DATE, utilDateToSqlDate(requestDTO.getFromDate()))
                .setParameter(TO_DATE, utilDateToSqlDate(requestDTO.getToDate()));

        if (!Objects.isNull(requestDTO.getDoctorId()))
            query.setParameter(DOCTOR_ID, requestDTO.getDoctorId());

        if (!Objects.isNull(requestDTO.getSpecializationId()))
            query.setParameter(SPECIALIZATION_ID, requestDTO.getSpecializationId());

        List<Object[]> results = query.getResultList();

        return DoctorDutyRosterUtils.parseQueryResultToDoctorDutyRosterStatusResponseDTOS(
                results, requestDTO.getFromDate(), requestDTO.getToDate());
    }

    private DoctorDutyRosterResponseDTO fetchDoctorDutyRosterDetails(Long doctorDutyRosterId) {

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_DOCTOR_DUTY_ROSTER_DETAILS)
                .setParameter(ID, doctorDutyRosterId);
        try {
            return transformQueryToSingleResult(query, DoctorDutyRosterResponseDTO.class);
        } catch (NoResultException e) {
            throw DOCTOR_DUTY_ROSTER_WITH_GIVEN_ID_NOT_FOUND.apply(doctorDutyRosterId);
        }
    }

    private List<DoctorWeekDaysDutyRosterResponseDTO> fetchDoctorWeekDaysDutyRosterResponseDTO(
            Long doctorDutyRosterId) {

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_DOCTOR_WEEK_DAYS_DUTY_ROSTER)
                .setParameter(ID, doctorDutyRosterId);

        return transformQueryToResultList(query, DoctorWeekDaysDutyRosterResponseDTO.class);
    }

    private List<DoctorDutyRosterOverrideResponseDTO> fetchDoctorDutyRosterOverrideResponseDTO(
            Long doctorDutyRosterId) {

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_DOCTOR_DUTY_ROSTER_OVERRIDE_DETAILS)
                .setParameter(ID, doctorDutyRosterId);

        return transformQueryToResultList(query, DoctorDutyRosterOverrideResponseDTO.class);
    }

    private Supplier<NoContentFoundException> DOCTOR_DUTY_ROSTER_NOT_FOUND = () ->
            new NoContentFoundException(DoctorDutyRoster.class);

    private Function<Long, NoContentFoundException> DOCTOR_DUTY_ROSTER_WITH_GIVEN_ID_NOT_FOUND =
            (doctorDutyRosterId) -> {
                throw new NoContentFoundException
                        (DoctorDutyRoster.class, "doctorDutyRosterId", doctorDutyRosterId.toString());
            };
}
