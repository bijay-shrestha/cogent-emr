package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.appointmentType.AppointmentTypeSearchRequestDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.appointmentType.AppointmentTypeResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.AppointmentTypeRepositoryCustom;
import com.cogent.persistence.model.AppointmentType;
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
import static com.cogent.admin.query.AppointmentTypeQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 2019-09-26
 */
@Repository
@Transactional(readOnly = true)
public class AppointmentTypeRepositoryCustomImpl implements AppointmentTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchAppointmentTypeByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_APPOINTMENT_TYPE_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long fetchAppointmentTypeByIdAndName(Long id, String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_APPOINTMENT_TYPE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public List<AppointmentTypeMinimalResponseDTO> search(AppointmentTypeSearchRequestDTO searchRequestDTO,
                                                          Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_APPOINTMENT_TYPE(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<AppointmentTypeMinimalResponseDTO> results = transformQueryToResultList(
                query, AppointmentTypeMinimalResponseDTO.class);

        if (results.isEmpty()) throw APPOINTMENT_TYPE_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<DropDownResponseDTO> fetchAppointmentTypeForDropdown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_APPOINTMENT_TYPE);

        List<DropDownResponseDTO> results = transformQueryToResultList(query, DropDownResponseDTO.class);

        if (results.isEmpty()) throw APPOINTMENT_TYPE_NOT_FOUND.get();
        else return results;
    }

    @Override
    public AppointmentTypeResponseDTO fetchDetailsById(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_APPOINTMENT_TYPE_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, AppointmentTypeResponseDTO.class);
        } catch (NoResultException e) {
            throw APPOINTMENT_TYPE_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    private Function<Long, NoContentFoundException> APPOINTMENT_TYPE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(AppointmentType.class, "id", id.toString());
    };

    private Supplier<NoContentFoundException> APPOINTMENT_TYPE_NOT_FOUND = ()
            -> new NoContentFoundException(AppointmentType.class);
}
