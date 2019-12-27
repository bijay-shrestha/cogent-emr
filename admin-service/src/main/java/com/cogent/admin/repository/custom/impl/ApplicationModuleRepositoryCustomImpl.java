package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.applicationModules.ApplicationModuleSearchRequestDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDetailResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDropdownResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleMinimalResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.ApplicationModuleRepositoryCustom;
import com.cogent.persistence.model.AdminCategory;
import com.cogent.persistence.model.ApplicationModule;
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
import static com.cogent.admin.query.ApplicationModuleQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti ON 24/12/2019
 */
@Repository
@Transactional(readOnly = true)
public class ApplicationModuleRepositoryCustomImpl implements ApplicationModuleRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchApplicationModuleByName(String name) {
        Query query = getQuery.apply(entityManager, QUERY_TO_FIND_APPOINTMENT_MODULE_COUNT_BY_NAME)
                .setParameter(NAME, name);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long fetchApplicationModuleByIdAndName(Long id, String name) {
        Query query = getQuery.apply(entityManager, QUERY_TO_FIND_APPOINTMENT_MODULE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<ApplicationModuleMinimalResponseDTO> search(ApplicationModuleSearchRequestDTO searchRequestDTO,
                                                            Pageable pageable) {

        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_APPLICATION_MODULE(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<ApplicationModuleMinimalResponseDTO> results = transformQueryToResultList(
                query, ApplicationModuleMinimalResponseDTO.class);

        if (results.isEmpty()) throw APPLICATION_MODULE_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public ApplicationModuleDetailResponseDTO fetchDetailsById(Long id) {

        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_APPLICATION_MODULE_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query, ApplicationModuleDetailResponseDTO.class);
        } catch (NoResultException e) {
            throw APPLICATION_MODULE_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    @Override
    public List<ApplicationModuleDropdownResponseDTO> fetchActiveApplicationModuleForDropDown() {

        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_APPLICATION_MODULE_FOR_DROPDOWN);

        List<ApplicationModuleDropdownResponseDTO> results =
                transformQueryToResultList(query, ApplicationModuleDropdownResponseDTO.class);

        if (results.isEmpty()) throw APPLICATION_MODULE_NOT_FOUND.get();
        else return results;
    }

    private Supplier<NoContentFoundException> APPLICATION_MODULE_NOT_FOUND = () ->
            new NoContentFoundException(ApplicationModule.class);

    private Function<Long, NoContentFoundException> APPLICATION_MODULE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(AdminCategory.class, "id", id.toString());
    };
}
