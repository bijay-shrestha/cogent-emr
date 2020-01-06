package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentDropDownDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.SubDepartmentRepositoryCustom;
import com.cogent.persistence.model.Ethnicity;
import com.cogent.persistence.model.SubDepartment;
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

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.log.constants.SubDepartmentLog.*;
import static com.cogent.admin.query.SubDepartmentQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

@Slf4j
@Repository
@Transactional
public class SubDepartmentRepositoryCustomImpl implements SubDepartmentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> fetchSubDepartmentByNameAndCode(String name, String code) {

        Query query = createQuery.apply(entityManager, FETCH_SUB_DEPARTMENT_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();

    }

    @Override
    public List<Object[]> checkSubDepartmentNameAndCodeIfExist(Long id, String name, String code) {
        Query query = createQuery.apply(entityManager, CHECK_SUB_DEPARTMENT_NAME_AND_CODE_IF_EXIST)
                .setParameter(ID, id)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();
    }

    @Override
    public List<SubDepartment> fetchSubDepartmentByDepartmentId(Long departmentId) {

        log.info(FETCH_SUB_DEPARTMENT_BY_DEPARTMENT_ID, departmentId);

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_SUB_DEPARTMENT_BY_DEPARTMENT_ID)
                .setParameter(ID, departmentId);

        List<SubDepartment> subDepartments = transformQueryToResultList(query, SubDepartment.class);

        return subDepartments;

    }

    @Override
    public List<DeleteRequestDTO> fetchSubDepartmentToDeleteByDepartmentId(Long departmentId) {

        log.info(FETCH_SUB_DEPARTMENT_BY_DEPARTMENT_ID, departmentId);

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_SUB_DEPARTMENT_TO_DELETE_BY_DEPARTMENT_ID)
                .setParameter(ID, departmentId);

        List<DeleteRequestDTO> subDepartments = transformQueryToResultList(query, DeleteRequestDTO.class);

        return subDepartments;

    }

    @Override
    public Optional<SubDepartment> fetchSubDepartmentById(Long id) {

        log.info(FETCH_SUB_DEPARTMENT_BY__ID, id);

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_SUB_DEPARTMENT_BY_ID)
                .setParameter(ID, id);
        try {
            SubDepartment subDepartment = transformQueryToSingleResult(query, SubDepartment.class);
            return Optional.ofNullable(subDepartment);
        } catch (NoResultException e) {
            throw new NoContentFoundException(SubDepartment.class, "id", id.toString());
        }

    }

    @Override
    public Optional<SubDepartment> fetchActiveSubDepartmentByIdAndDepartmentId(Long id, Long departmentId) {
        Query query = createQuery.apply(entityManager, QUERY_TO_FIND_ACTIVE_SUB_DEPARTMENT_BY_ID_AND_DEPARTMENT_ID)
                .setParameter(ID, id)
                .setParameter(DEPARTMENT_ID, departmentId);
        try {
            return Optional.ofNullable(transformQueryToSingleResult(query, SubDepartment.class));
        } catch (NoResultException e) {
            throw new NoContentFoundException(SubDepartment.class, "id", id.toString());
        }
    }

    @Override
    public Optional<SubDepartmentResponseDTO> fetchSubDepartmentDetail(Long id) {
        log.info(FETCH_SUB_DEPARTMENT_DETAILS_BY__ID, SUB_DEPARTMENT, id);

        Query query = createQuery.apply(entityManager, QUERY_TO_FETCH_SUB_DEPARTMENT_DETAILS_BY_ID)
                .setParameter(ID, id);

        try {
            SubDepartmentResponseDTO subDepartmentResponseDTO = transformQueryToSingleResult(query,
                    SubDepartmentResponseDTO.class);
            return Optional.of(subDepartmentResponseDTO);
        } catch (
                NoResultException e) {
            throw new NoContentFoundException(Ethnicity.class, "id", id.toString());
        }
    }

    @Override
    public List<SubDepartmentMinimalResponseDTO> searchSubDepartment(
            SubDepartmentSearchRequestDTO subDepartmentSearchRequestDTO, Pageable pageable) {
        log.info(SEARCH_SUB_DEPARTMENT, subDepartmentSearchRequestDTO);

        Query query = createQuery.apply(entityManager, QUERY_TO_SEARCH_SUB_DEPARTMENT
                .apply(subDepartmentSearchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<SubDepartmentMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                SubDepartmentMinimalResponseDTO.class);

        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(SubDepartment.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public Optional<List<SubDepartmentDropDownDTO>> fetchDropDownList() {

        log.info(FETCH_SUB_DEPARTMENT_ID_AND_NAME);

        Query query = createQuery.apply(entityManager, GET_SUB_DEPARTMENT_DROP_DOWN_LIST);

        List<SubDepartmentDropDownDTO> dropDownDTOS = transformQueryToResultList(query, SubDepartmentDropDownDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.ofNullable(dropDownDTOS);

    }

    @Override
    public Optional<List<SubDepartmentDropDownDTO>> fetchActiveDropDownList() {

        log.info(FETCH_SUB_DEPARTMENT_ID_AND_NAME);

        Query query = createQuery.apply(entityManager, GET_SUB_DEPARTMENT_ACTIVE_DROP_DOWN_LIST);

        List<SubDepartmentDropDownDTO> dropDownDTOS = transformQueryToResultList(query, SubDepartmentDropDownDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.ofNullable(dropDownDTOS);

    }

    @Override
    public Optional<List<SubDepartmentDropDownDTO>> fetchActiveDropDownListByDepartmentId(Long departmentId) {

        Query query = createQuery.apply(entityManager, GET_SUB_DEPARTMENT_ACTIVE_DROP_DOWN_LIST_BY_DEPARTMENT_ID)
                .setParameter(DEPARTMENT_ID, departmentId);

        List<SubDepartmentDropDownDTO> dropDownDTOS = transformQueryToResultList(query, SubDepartmentDropDownDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }


    @Override
    public List<Object[]> getSubDepartment() {

        log.info(FETCH_SUB_DEPARTMENT_OBJECT_FOR_EXCEL);

        Query query = createQuery.apply(entityManager, CREATE_QUERY_TO_FETCH_SUB_DEPARTMENT);

        return query.getResultList();

    }

}
