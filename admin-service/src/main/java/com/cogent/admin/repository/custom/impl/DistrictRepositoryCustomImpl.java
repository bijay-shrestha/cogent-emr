package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.district.DistrictSearchRequestDTO;
import com.cogent.admin.dto.response.district.DistrictDropDownResponseDTO;
import com.cogent.admin.dto.response.district.DistrictMinimalResponseDTO;
import com.cogent.admin.dto.response.district.DistrictResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.DistrictRepositoryCustom;
import com.cogent.persistence.model.District;
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
import static com.cogent.admin.constants.QueryConstants.NAME;
import static com.cogent.admin.query.DistrictQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;


@Repository
@Transactional
@Slf4j
public class DistrictRepositoryCustomImpl implements DistrictRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long fetchDistrictCountByName(String name) {

        Query query = createQuery.apply(entityManager, FETCH_DISTRICT_COUNT_BY_NAME)
                .setParameter(NAME, name);

        return (Long) query.getSingleResult();
    }

    @Override
    public Optional<List<DistrictDropDownResponseDTO>> fetchDropDownList() {

        Query query = createQuery.apply(entityManager, FETCH_DROP_DOWN_LIST);

        List<DistrictDropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                DistrictDropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);
    }

    @Override
    public Optional<List<DistrictDropDownResponseDTO>> fetchActiveDropDownList() {

        Query query = createQuery.apply(entityManager, FETCH_ACTIVE_DROP_DOWN_LIST);

        List<DistrictDropDownResponseDTO> dropDownResponseDTOS = transformQueryToResultList(query,
                DistrictDropDownResponseDTO.class);

        return dropDownResponseDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownResponseDTOS);
    }

    @Override
    public DistrictResponseDTO fetchDistrictDetails(Long id) {

        Query query = createQuery.apply(entityManager, FETCH_DETAIL_DISTRICT_DATA)
                .setParameter(ID, id);

        try {
            return transformQueryToSingleResult(query, DistrictResponseDTO.class);
        } catch (NoResultException e) {
            throw new NoContentFoundException(District.class, "id", id.toString());
        }
    }

    @Override
    public List<DistrictMinimalResponseDTO> searchDistrict(
            DistrictSearchRequestDTO districtSearchRequestDTO, Pageable pageable) {

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_DISTRICT.apply(districtSearchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<DistrictMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DistrictMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(District.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public Optional<District> fetchDistrictById(Long id) {

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_DISTRICT_BY_ID)
                .setParameter(ID, id);
        try {
            District subDepartment = transformQueryToSingleResult(query, District.class);
            return Optional.ofNullable(subDepartment);
        } catch (NoResultException e) {
            throw new NoContentFoundException(District.class, "id", id.toString());
        }
    }


    @Override
    public List<District> fetchDistrictByProvincesId(Long provincesId) {

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_DISTRICT_BY_PROVINCES_ID)
                .setParameter(ID, provincesId);

        List<District> districtList = transformQueryToResultList(query, District.class);

        return districtList;
    }

    @Override
    public List<DeleteRequestDTO> fetchDistrictToDeleteByProvinceId(Long provinceId) {

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_DISTRICT_TO_DELETE_BY_PROVINCE_ID)
                .setParameter(ID, provinceId);

        List<DeleteRequestDTO> disticts = transformQueryToResultList(query, DeleteRequestDTO.class);

        return disticts;
    }
}
