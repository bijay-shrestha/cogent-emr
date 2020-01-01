package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.hospital.HospitalSearchRequestDTO;
import com.cogent.admin.dto.response.hospital.HospitalDropdownDTO;
import com.cogent.admin.dto.response.hospital.HospitalResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.HospitalRepositoryCustom;
import com.cogent.persistence.model.Hospital;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.function.Supplier;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.query.HospitalQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.createQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;

/**
 * @author Rupak
 */
@Repository
@Transactional(readOnly = true)
public class HospitalRepositoryCustomImpl implements HospitalRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchHospitalByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_HOSPITAL_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Hospital findHospital(Long id) {
        return null;
    }

    @Override
    public Long findHospitalByIdAndName(Long id, String name) {

        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_HOSPITAL_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();


    }

    @Override
    public List<HospitalResponseDTO> search(HospitalSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_HOSPITAL(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<HospitalResponseDTO> results = transformQueryToResultList(
                query, HospitalResponseDTO.class);

        if (results.isEmpty()) throw HOSPITAL_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public List<HospitalDropdownDTO> fetchActiveHospitalForDropDown() {
        return null;
    }

    @Override
    public HospitalResponseDTO fetchDetailsById(Long id) {
        return null;
    }

    @Override
    public Hospital fetchHospitalById(Long id) {
        return null;
    }

    @Override
    public List<HospitalDropdownDTO> fetchHospitalForDropDown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_HOSPITAL_FOR_DROPDOWN);

        List<HospitalDropdownDTO> results = transformQueryToResultList(query, HospitalDropdownDTO.class);

        if (results.isEmpty()) throw HOSPITAL_NOT_FOUND.get();
        else return results;

    }

    private Supplier<NoContentFoundException> HOSPITAL_NOT_FOUND = () -> new NoContentFoundException(Hospital.class);
}
