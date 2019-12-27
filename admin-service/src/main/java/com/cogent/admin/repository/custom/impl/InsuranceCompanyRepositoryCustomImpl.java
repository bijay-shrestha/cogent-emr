package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.insurancecompany.InsuranceCompanySearchRequestDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyMinimalResponseDTO;
import com.cogent.admin.dto.response.insurancecompany.InsuranceCompanyResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.InsuranceCompanyRepositoryCustom;
import com.cogent.persistence.model.InsuranceCompany;
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
import static com.cogent.admin.query.InsuranceCompanyQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

@Repository
@Transactional
@Slf4j
public class InsuranceCompanyRepositoryCustomImpl implements InsuranceCompanyRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<DropDownResponseDTO>> fetchActiveDropDownList() {
        Query query = getQuery.apply(entityManager, QUERY_FOR_ACTIVE_DROP_DOWN_INSURANCE_COMPANY);

        List<DropDownResponseDTO> downResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return downResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(downResponseDTOS);
    }

    @Override
    public Optional<List<DropDownResponseDTO>> fetchDropDownList() {
        Query query = getQuery.apply(entityManager, QUERY_FOR_DROP_DOWN_INSURANCE_COMPANY);

        List<DropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                DropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);
    }

    @Override
    public List<InsuranceCompanyMinimalResponseDTO> searchInsuranceCompany(
            InsuranceCompanySearchRequestDTO searchRequestDTO,
            Pageable pageable) {

        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_INSURANCE_COMPANY.apply(searchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<InsuranceCompanyMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                InsuranceCompanyMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(InsuranceCompany.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public InsuranceCompanyResponseDTO fetchInsuranceCompanyDetails(Long id) {
        Query query = getQuery.apply(entityManager, FETCH_INSURANCE_COMPANY_DETAILS)
                .setParameter(ID, id);

        try {
            InsuranceCompanyResponseDTO singleResult = transformQueryToSingleResult(query,
                    InsuranceCompanyResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(InsuranceCompany.class, "id", id.toString());
        }
    }

}
