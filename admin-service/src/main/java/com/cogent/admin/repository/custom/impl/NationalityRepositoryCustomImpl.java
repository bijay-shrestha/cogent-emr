package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.nationality.NationalitySearchRequestDTO;
import com.cogent.admin.dto.response.nationality.NationalityDropDownResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityMinimalResponseDTO;
import com.cogent.admin.dto.response.nationality.NationalityResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.NationalityRepositoryCustom;
import com.cogent.persistence.model.Nationality;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.query.NationalityQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi  on 2019-09-20
 */

@Repository
@Transactional
@Slf4j
public class NationalityRepositoryCustomImpl implements NationalityRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<NationalityDropDownResponseDTO>> fetchActiveDropDownList() {
        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_NATIONALITY);

        List<NationalityDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                NationalityDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<NationalityDropDownResponseDTO>> fetchDropDownList() {
        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_NATIONALITY);

        List<NationalityDropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                NationalityDropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public List<NationalityMinimalResponseDTO> searchNationality(
            NationalitySearchRequestDTO searchRequestDTO,
            Pageable pageable) {
        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_NATIONALITY.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<NationalityMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                NationalityMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Nationality.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public NationalityResponseDTO fetchNationalityDetails(Long id) {
        Query query = createQuery.apply(entityManager, FETCH_NATIONALITY_DETAILS)
                .setParameter(ID, id);

        try {
            NationalityResponseDTO singleResult = transformQueryToSingleResult(query, NationalityResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Nationality.class, "id", id.toString());
        }
    }
}
