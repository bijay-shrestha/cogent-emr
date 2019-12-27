package com.cogent.admin.service.impl;

import com.cogent.admin.dto.request.login.UserMenuRequestDTO;
import com.cogent.admin.dto.response.login.AssignedProfileMenuResponseDTO;
import com.cogent.admin.dto.response.login.AssignedRolesResponseDTO;
import com.cogent.admin.dto.response.login.ChildMenusResponseDTO;
import com.cogent.admin.service.UserMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author smriti ON 27/12/2019
 */
@Service
@Transactional
public class UserMenuServiceImpl implements UserMenuService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AssignedProfileMenuResponseDTO fetchAssignedProfileMenuResponseDto(UserMenuRequestDTO userMenuRequestDTO) {

        String SQL = "SELECT" +
                " pm.parent_id as parentId," +                                      //[0]
                " pm.user_menu_id as userMenuId," +                                 //[1]
                " GROUP_CONCAT(pm.role_id) as roleId," +                            //[2]
                " sd.code as subDepartmentCode," +                                  //[3]
                " sd.name as subDepartmentName" +                                   //[4]
                " FROM profile_menu pm" +
                " LEFT JOIN profile p ON p.id =pm.profile_id" +
                " LEFT JOIN admin_profile ap ON ap.profile_id = p.id" +
                " LEFT JOIN admin a ON a.id = ap.admin_id" +
                " LEFT JOIN sub_department sd ON sd.id = p.sub_department_id" +
                " WHERE pm.status = 'Y'" +
                " AND (a.username = :username OR a.email=:email)" +
                " AND sd.code=:code" +
                " GROUP BY pm.parent_id, pm.user_menu_id, pm.profile_id";

        Query query = entityManager.createNativeQuery(SQL)
                .setParameter("username", userMenuRequestDTO.getUsername())
                .setParameter("email", userMenuRequestDTO.getUsername())
                .setParameter("code", userMenuRequestDTO.getSubDepartmentCode());

        List<Object[]> results = query.getResultList();

        final int PARENT_ID_INDEX = 0;
        final int USER_MENU_ID_INDEX = 1;
        final int ROLE_ID_INDEX = 2;
        final int SUB_DEPARTMENT_CODE_INDEX = 3;
        final int SUB_DEPARTMENT_NAME_INDEX = 4;

        String subDepartmentCode = "";
        String subDepartmentName = "";

        List<ChildMenusResponseDTO> childMenusResponseDTOS = new ArrayList<>();

        for (Object[] result : results) {
            subDepartmentCode = result[SUB_DEPARTMENT_CODE_INDEX].toString();
            subDepartmentName = result[SUB_DEPARTMENT_NAME_INDEX].toString();

            List<Long> roleIds = Stream.of(result[ROLE_ID_INDEX].toString().split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            ChildMenusResponseDTO responseDTO = ChildMenusResponseDTO.builder()
                    .parentId(Long.parseLong(result[PARENT_ID_INDEX].toString()))
                    .userMenuId(Long.parseLong(result[USER_MENU_ID_INDEX].toString()))
                    .roleId(roleIds)
                    .build();

            childMenusResponseDTOS.add(responseDTO);
        }

        Map<Long, List<ChildMenusResponseDTO>> groupByParentIdMap =
                childMenusResponseDTOS.stream().collect(Collectors.groupingBy(ChildMenusResponseDTO::getParentId));

        List<AssignedRolesResponseDTO> assignedRolesResponseDTOS = groupByParentIdMap.entrySet()
                .stream()
                .map(map -> new AssignedRolesResponseDTO(map.getKey(), map.getValue()))
                .collect(Collectors.toList());

        AssignedProfileMenuResponseDTO assignedProfileMenuResponseDTO = AssignedProfileMenuResponseDTO.builder()
                .subDepartmentName(subDepartmentName)
                .subDepartmentCode(subDepartmentCode)
                .assignedRolesResponseDTOS(assignedRolesResponseDTOS)
                .build();

        System.out.println(assignedProfileMenuResponseDTO);

        return assignedProfileMenuResponseDTO;
    }
}
