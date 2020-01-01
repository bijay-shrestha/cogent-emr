package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankMinimalResponseDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.RegisteredBankRepositoryCustom;
import com.cogent.persistence.model.RegisteredBank;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.query.RegisteredBankQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi Thapa 12/10/19
 */
@Repository
@Transactional(readOnly = true)
public class RegisteredBankRepositoryCustomImpl implements RegisteredBankRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> findRegisteredBankByNameOrCode(String name, String code) {
        Query query = createQuery.apply(entityManager, FETCH_REGISTERED_BANK_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<RegisteredBankMinimalResponseDTO> searchRegisteredBank(RegisteredBankSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_REGISTERED_BANK.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<RegisteredBankMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                RegisteredBankMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(RegisteredBank.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public Optional<List<DropDownResponseDTO>> dropDownList() {
        Query query = createQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_REGISTERED_BANK);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> activeDropDownList() {
        Query query = createQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_REGISTERED_BANK);

        List<DropDownResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return minimalResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(minimalResponseDTOS);
    }

    @Override
    public RegisteredBankResponseDTO fetchRegisteredBankDetails(Long id) {
        Query query = createQuery.apply(entityManager, FETCH_REGISTERED_BANK_DETAILS)
                .setParameter(ID, id);
        try {
            return transformQueryToSingleResult(query,
                    RegisteredBankResponseDTO.class);
        } catch (NoResultException e) {
            throw new NoContentFoundException(RegisteredBank.class, "id", id.toString());
        }
    }

    @Override
    public List<Object[]> checkIfRegisteredBankNameAndCodeExists(Long id, String name, String code) {
        Query query= createQuery.apply(entityManager, CHECK_IF_REGISTERED_BANK_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME,name)
                .setParameter(CODE,code);

        return  query.getResultList();
    }
}
