package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.appointmentMode.AppointmentModeSearchRequestDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentMode.AppointmentModeResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.AppointmentModeRepositoryCustom;
import com.cogent.persistence.model.AppointmentMode;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.query.AppointmentModeQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 2019-10-10
 */
@Repository
@Transactional(readOnly = true)
public class AppointmentModeRepositoryCustomImpl implements AppointmentModeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchAppointmentModeByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_APPOINTMENT_MODE_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long fetchAppointmentModeByIdAndName(Long id, String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_APPOINTMENT_MODE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public List<AppointmentModeMinimalResponseDTO> search(AppointmentModeSearchRequestDTO searchRequestDTO,
                                                          Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_APPOINTMENT_MODE(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<AppointmentModeMinimalResponseDTO> results = transformQueryToResultList(
                query, AppointmentModeMinimalResponseDTO.class);

        if (results.isEmpty()) throw APPOINTMENT_MODE_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveAppointmentModeForDropdown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_APPOINTMENT_MODE);

        List<DropDownResponseDTO> results = transformQueryToResultList(query, DropDownResponseDTO.class);

        if (results.isEmpty()) throw APPOINTMENT_MODE_NOT_FOUND.get();
        else return results;
    }

    @Override
    public AppointmentModeResponseDTO fetchDetailsById(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_APPOINTMENT_MODE_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, AppointmentModeResponseDTO.class);
        } catch (NoResultException e) {
            throw APPOINTMENT_MODE_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    private Function<Long, NoContentFoundException> APPOINTMENT_MODE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(AppointmentMode.class, "id", id.toString());
    };

    private Supplier<NoContentFoundException> APPOINTMENT_MODE_NOT_FOUND = ()
            -> new NoContentFoundException(AppointmentMode.class);
}
