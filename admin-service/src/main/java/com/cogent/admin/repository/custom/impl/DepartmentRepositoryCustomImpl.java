package com.cogent.admin.repository.custom.impl;

import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.response.department.DepartmentDropDownDTO;
import com.cogent.admin.dto.response.department.DepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.department.DepartmentResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.custom.DepartmentRepositoryCustom;
import com.cogent.persistence.model.Department;
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
import static com.cogent.admin.log.constants.DepartmentLog.*;
import static com.cogent.admin.query.DepartmentQuery.*;
import static com.cogent.admin.utils.PageableUtils.addPagination;
import static com.cogent.admin.utils.QueryUtils.*;

/**
 * @author Sauravi
 */

@Repository
@Transactional
@Slf4j
public class DepartmentRepositoryCustomImpl implements DepartmentRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DepartmentResponseDTO fetchDepartmentDetails(Long id) {

        Query query = getQuery.apply(entityManager, FETCH_DEPARTMENT_DETAILS).setParameter(ID, id);

        try {
            DepartmentResponseDTO singleResult = transformQueryToSingleResult(query, DepartmentResponseDTO.class);
            return singleResult;
        } catch (NoResultException e) {
            throw new NoContentFoundException(Department.class, "id", id.toString());
        }

    }

    @Override
    public List<Object[]> fetchDepartmentByNameOrCode(String name, String code) {

        Query query = getQuery.apply(entityManager, FETCH_DEPARTMENT_BY_NAME_AND_CODE)
                .setParameter(NAME, name)
                .setParameter(CODE, code);

        return query.getResultList();

    }

    @Override
    public List<DepartmentMinimalResponseDTO> searchDepartment(DepartmentSearchRequestDTO departmentSearchRequestDTO,
                                                               Pageable pageable) {

        log.info(SEARCH_DEPARTMENT, departmentSearchRequestDTO.getId(), departmentSearchRequestDTO.getName(),
                departmentSearchRequestDTO.getCode());

        Query query = getQuery.apply(entityManager, QUERY_TO_SEARCH_DEPARTMENT.apply(departmentSearchRequestDTO));

        int totalItems = query.getResultList().size();

        addPagination.accept(pageable, query);

        List<DepartmentMinimalResponseDTO> minimalResponseDTOS = transformQueryToResultList(query,
                DepartmentMinimalResponseDTO.class);


        if (minimalResponseDTOS.isEmpty()) throw new NoContentFoundException(Department.class);
        else {
            minimalResponseDTOS.get(0).setTotalItems(totalItems);
            return minimalResponseDTOS;
        }
    }

    @Override
    public Optional<List<DepartmentDropDownDTO>> fetchDropDownList() {

        log.info(FETCH_DEPARTMENT_ID_AND_NAME);

        Query query = getQuery.apply(entityManager, GET_DEPARTMENT_NAME_AND_ID);

        List<DepartmentDropDownDTO> dropDownDTOS = transformQueryToResultList(query,
                DepartmentDropDownDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }

    @Override
    public Optional<List<DepartmentDropDownDTO>> fetchActiveDropDownList() {
        log.info(FETCH_DEPARTMENT_ID_AND_NAME);

        Query query = getQuery.apply(entityManager, GET_ACTIVE_DEPARTMENT_NAME_AND_ID);

        List<DepartmentDropDownDTO> dropDownDTOS = transformQueryToResultList(query,
                DepartmentDropDownDTO.class);

        return dropDownDTOS.isEmpty() ? Optional.empty() : Optional.of(dropDownDTOS);
    }

    @Override
    public List<Object[]> getDepartment() {
        log.info(FETCH_DEPARTMENT_OBJECT_FOR_EXCEL);
        Query query = getQuery.apply(entityManager, CREATE_QUERY_TO_FETCH_DEPARTMENT);
        List<Object[]> object = query.getResultList();
        return object;
    }

    @Override
    public List<Object[]> checkIfDepartmentNameAndCodeExists(Long id, String name, String code) {
        Query query=getQuery.apply(entityManager, CHECK_IF_DEPARTMENT_EXISTS)
                .setParameter(ID,id)
                .setParameter(NAME,name)
                .setParameter(CODE,code);

        return  query.getResultList();
    }
}

