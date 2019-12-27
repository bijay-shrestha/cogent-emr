package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.patientType.PatientTypeSearchRequestDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeMinimalResponseDTO;
import com.cogent.admin.dto.response.patientType.PatientTypeResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.PatientTypeRepositoryCustom;
import com.cogent.persistence.model.PatientType;
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
import static com.cogent.admin.query.PatientTypeQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 2019-09-26
 */
@Repository
@Transactional(readOnly=true)
public class PatientTypeRepositoryCustomImpl implements PatientTypeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchPatientTypeByName(String name) {
        Query query = getQuery.apply(entityManager, QUERY_TO_FIND_PATIENT_TYPE_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long fetchPatientTypeByIdAndName(Long id, String name) {
        Query query = getQuery.apply(entityManager, QUERY_TO_FIND_PATIENT_TYPE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public List<PatientTypeMinimalResponseDTO> search(PatientTypeSearchRequestDTO searchRequestDTO,
                                                      Pageable pageable) {

        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_PATIENT_TYPE(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<PatientTypeMinimalResponseDTO> results = transformQueryToResultList(
                query, PatientTypeMinimalResponseDTO.class);

        if (results.isEmpty()) throw PATIENT_TYPE_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<DropDownResponseDTO> fetchActivePatientTypeForDropdown() {
        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_PATIENT_TYPE);

        List<DropDownResponseDTO> results = transformQueryToResultList(query, DropDownResponseDTO.class);

        if (results.isEmpty()) throw PATIENT_TYPE_NOT_FOUND.get();
        else return results;
    }

    @Override
    public PatientTypeResponseDTO fetchDetailsById(Long id) {
        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_PATIENT_TYPE_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, PatientTypeResponseDTO.class);
        } catch (NoResultException e) {
            throw PATIENT_TYPE_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    private Function<Long, NoContentFoundException> PATIENT_TYPE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(PatientType.class, "id", id.toString());
    };

    private Supplier<NoContentFoundException> PATIENT_TYPE_NOT_FOUND = ()
            -> new NoContentFoundException(PatientType.class);
}
