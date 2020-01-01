package com.cogent.admin.service.impl;

import com.cogent.admin.dto.request.userMenu.UserMenuRequestDTO;
import com.cogent.admin.dto.response.userMenu.AssignedProfileResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.service.UserMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.constants.QueryConstants.*;
import static com.cogent.admin.query.UserMenuQuery.QUERY_TO_FETCH_ASSIGNED_PROFILE_RESPONSE;
import static com.cogent.admin.utils.UserMenuUtils.parseToAssignedProfileMenuResponseDTO;

/**
 * @author smriti ON 27/12/2019
 */
@Service
@Transactional
public class UserMenuServiceImpl implements UserMenuService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AssignedProfileResponseDTO fetchAssignedProfileMenuResponseDto(UserMenuRequestDTO userMenuRequestDTO) {

        Query query = entityManager.createNativeQuery(QUERY_TO_FETCH_ASSIGNED_PROFILE_RESPONSE)
                .setParameter(USERNAME, userMenuRequestDTO.getUsername())
                .setParameter(EMAIL, userMenuRequestDTO.getUsername())
                .setParameter(CODE, userMenuRequestDTO.getSubDepartmentCode());

        List<Object[]> results = query.getResultList();

        if (results.isEmpty())
            throw new NoContentFoundException("NO USER MENUS");

        return parseToAssignedProfileMenuResponseDTO(results);
    }
}
