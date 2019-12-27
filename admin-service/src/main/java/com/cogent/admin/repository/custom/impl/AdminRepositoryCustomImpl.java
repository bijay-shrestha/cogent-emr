package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.admin.AdminSearchRequestDTO;
import com.cogent.admin.dto.request.admin.AdminUpdateRequestDTO;
import com.cogent.admin.dto.response.admin.*;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.AdminRepositoryCustom;
import com.cogent.persistence.model.Admin;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cogent.admin.constants.ErrorMessageConstants.AdminServiceMessages;
import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.constants.StatusConstants.YES;
import static com.cogent.admin.query.AdminQuery.*;
import static com.cogent.admin.utils.AdminUtils.parseToAdminDetailResponseDTO;
import static com.cogent.admin.utils.AdminUtils.parseToAdminResponse;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author smriti on 7/21/19
 */
@Service
@Transactional(readOnly = true)
public class AdminRepositoryCustomImpl implements AdminRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List fetchAdminForValidation(String username, String email, String mobileNumber) {

        Query query = getQuery.apply(entityManager, QUERY_TO_FIND_ADMIN_FOR_VALIDATION)
                .setParameter(USERNAME, username)
                .setParameter(EMAIL, email)
                .setParameter(MOBILE_NUMBER, mobileNumber);

        return query.getResultList();
    }

    @Override
    public List fetchActiveAdminsForDropDown() {

        Query query = getQuery.apply(entityManager, QUERY_TO_FETCH_ACTIVE_ADMIN_FOR_DROPDOWN);

        List list = transformQueryToResultList(query, AdminDropdownDTO.class);

        if (list.isEmpty()) throw NO_ADMIN_FOUND.get();
        else return list;
    }

    @Override
    public List search(AdminSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = createNativeQuery.apply(entityManager, QUERY_TO_SEARCH_ADMIN(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<AdminMinimalResponseDTO> result =
                transformNativeQueryToResultList(query, AdminMinimalResponseDTO.class);

        if (ObjectUtils.isEmpty(result)) throw NO_ADMIN_FOUND.get();
        else {
            result.get(0).setTotalItems(totalItems);
            return result;
        }
    }

    @Override
    public AdminDetailResponseDTO fetchDetailsById(Long id) {
        AdminResponseDTO adminResponseDTO = getAdminResponseDTO(id);

        return parseToAdminDetailResponseDTO(adminResponseDTO,
                adminResponseDTO.getHasMacBinding().equals(YES) ? getMacAddressInfo(id) : new ArrayList<>());
    }

    @Override
    public List fetchAdmin(AdminUpdateRequestDTO updateRequestDTO) {
        Query query = getQuery.apply(entityManager, QUERY_TO_FIND_ADMIN_EXCEPT_CURRENT_ADMIN)
                .setParameter(ID, updateRequestDTO.getId())
                .setParameter(EMAIL, updateRequestDTO.getEmail())
                .setParameter(MOBILE_NUMBER, updateRequestDTO.getMobileNumber());

        return query.getResultList();
    }

    @Override
    public Admin fetchAdminByUsernameOrEmail(String username) {
        try {
            return entityManager.createQuery(QUERY_TO_FETCH_ADMIN_BY_USERNAME_OR_EMAIL, Admin.class)
                    .setParameter(USERNAME, username)
                    .setParameter(EMAIL, username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw ADMIN_NOT_FOUND.apply(username);
        }
    }

    public AdminResponseDTO getAdminResponseDTO(Long id) {
        Query query = createNativeQuery.apply(entityManager, QUERY_TO_FETCH_ADMIN_DETAIL)
                .setParameter(ID, id);

        List<Object[]> results = query.getResultList();

        if (results.isEmpty())
            throw ADMIN_WITH_GIVEN_ID_NOT_FOUND.apply(id);

        return parseToAdminResponse.apply(results.get(0));
    }

    public List<MacAddressInfoResponseDTO> getMacAddressInfo(Long id) {
        Query query = getQuery.apply(entityManager, QUERY_FO_FETCH_MAC_ADDRESS_INFO)
                .setParameter(ID, id);

        return transformQueryToResultList(query, MacAddressInfoResponseDTO.class);
    }

    private Supplier<NoContentFoundException> NO_ADMIN_FOUND = () -> new NoContentFoundException(Admin.class);

    private Function<Long, NoContentFoundException> ADMIN_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Admin.class, "id", id.toString());
    };

    private Function<String, NoContentFoundException> ADMIN_NOT_FOUND = (username) -> {
        throw new NoContentFoundException(Admin.class.getSimpleName() + " '" + username + "' "
                + AdminServiceMessages.ADMIN_NOT_FOUND, "username/email", username);
    };
}

