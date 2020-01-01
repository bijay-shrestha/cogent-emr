package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.profile.ProfileSearchRequestDTO;
import com.cogent.admin.dto.response.profile.*;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.ProfileRepositoryCustom;
import com.cogent.persistence.model.Profile;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
import static com.cogent.admin.query.ProfileQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.ProfileUtils.parseToProfileDetailResponseDTO;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 7/10/19
 */
@Service
@Transactional(readOnly = true)
public class ProfileRepositoryCustomImpl implements ProfileRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long findProfileCountByName(String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_PROFILE_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Long findProfileCountByIdAndName(Long id, String name) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_PROFILE_COUNT_BY_ID_AND_NAME)
                .setParameter(ID, id)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public List<ProfileMinimalResponseDTO> search(ProfileSearchRequestDTO searchRequestDTO,
                                                  Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_PROFILE.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<ProfileMinimalResponseDTO> results = transformQueryToResultList(query, ProfileMinimalResponseDTO.class);

        if (results.isEmpty()) throw PROFILES_NOT_FOUND.get();
        else {
            results.get(0).setTotalItems(totalItems);
            return results;
        }
    }

    @Override
    public ProfileDetailResponseDTO fetchDetailsById(Long id) {
        return parseToProfileDetailResponseDTO(getProfileResponseDTO(id), getProfileMenuResponseDTO(id));
    }

    private ProfileResponseDTO getProfileResponseDTO(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_PROFILE_DETAILS)
                .setParameter(ID, id);

        try {
            return transformQueryToSingleResult(query, ProfileResponseDTO.class);
        } catch (NoResultException e) {
            throw PROFILE_WITH_GIVEN_ID_NOT_FOUND.apply(id);
        }
    }

    private List<ProfileMenuResponseDTO> getProfileMenuResponseDTO(Long id) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_PROFILE_MENU_DETAILS)
                .setParameter(ID, id);

        return transformQueryToResultList(query, ProfileMenuResponseDTO.class);
    }

    @Override
    public List<ProfileDropdownDTO> fetchActiveProfilesForDropDown() {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_PROFILES_FOR_DROPDOWN);

        List<ProfileDropdownDTO> results = transformQueryToResultList(query, ProfileDropdownDTO.class);

        if (results.isEmpty()) throw PROFILES_NOT_FOUND.get();
        else return results;
    }

    @Override
    public List<ProfileDropdownDTO> fetchProfileBySubDepartmentId(Long subDepartmentId) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_PROFILE_BY_SUB_DEPARTMENT_ID)
                .setParameter(ID, subDepartmentId);

        List<ProfileDropdownDTO> results = transformQueryToResultList(query, ProfileDropdownDTO.class);

        if (results.isEmpty()) throw PROFILES_NOT_FOUND.get();
        else return results;
    }

    private Supplier<NoContentFoundException> PROFILES_NOT_FOUND = () -> new NoContentFoundException(Profile.class);

    private Function<Long, NoContentFoundException> PROFILE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Profile.class, "id", id.toString());
    };
}
