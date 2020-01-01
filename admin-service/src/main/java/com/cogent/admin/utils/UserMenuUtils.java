package com.cogent.admin.utils;

import com.cogent.admin.dto.response.userMenu.AssignedProfileResponseDTO;
import com.cogent.admin.dto.response.userMenu.AssignedRolesResponseDTO;
import com.cogent.admin.dto.response.userMenu.ChildMenusResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.cogent.admin.constants.StringConstant.COMMA_SEPARATED;

/**
 * @author smriti ON 01/01/2020
 */

public class UserMenuUtils {

    public static AssignedProfileResponseDTO parseToAssignedProfileMenuResponseDTO(List<Object[]> results) {

        List<ChildMenusResponseDTO> childMenusResponseDTOS = parseToChildMenusResponseDTOS(results);

        List<AssignedRolesResponseDTO> assignedRolesResponseDTOS =
                parseToAssignedRolesResponseDTOS(childMenusResponseDTOS);

        return parseToAssignedProfileMenuResponseDTO(assignedRolesResponseDTOS, results);
    }

    private static List<ChildMenusResponseDTO> parseToChildMenusResponseDTOS(List<Object[]> results) {

        final int PARENT_ID_INDEX = 0;
        final int USER_MENU_ID_INDEX = 1;
        final int ROLE_ID_INDEX = 2;

        List<ChildMenusResponseDTO> childMenusResponseDTOS = new ArrayList<>();

        results.forEach(result -> {
            List<Long> roleIds = Stream.of(result[ROLE_ID_INDEX].toString()
                    .split(COMMA_SEPARATED))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            ChildMenusResponseDTO responseDTO = ChildMenusResponseDTO.builder()
                    .parentId(Long.parseLong(result[PARENT_ID_INDEX].toString()))
                    .userMenuId(Long.parseLong(result[USER_MENU_ID_INDEX].toString()))
                    .roleId(roleIds)
                    .build();

            childMenusResponseDTOS.add(responseDTO);
        });

        return childMenusResponseDTOS;
    }

    private static List<AssignedRolesResponseDTO> parseToAssignedRolesResponseDTOS
            (List<ChildMenusResponseDTO> childMenusResponseDTOS) {

        Map<Long, List<ChildMenusResponseDTO>> groupByParentIdMap =
                childMenusResponseDTOS.stream().collect(Collectors.groupingBy(ChildMenusResponseDTO::getParentId));

        return groupByParentIdMap.entrySet()
                .stream()
                .map(map -> new AssignedRolesResponseDTO(map.getKey(), map.getValue()))
                .collect(Collectors.toList());
    }

    private static AssignedProfileResponseDTO parseToAssignedProfileMenuResponseDTO
            (List<AssignedRolesResponseDTO> assignedRolesResponseDTOS,
             List<Object[]> queryResults) {

        /*BECAUSE SUB-DEPARTMENT DETAILS REMAIN THE SAME*/
        final int SUB_DEPARTMENT_CODE_INDEX = 3;
        final int SUB_DEPARTMENT_NAME_INDEX = 4;

        Object[] firstObject = queryResults.get(0);
        String subDepartmentName = firstObject[SUB_DEPARTMENT_NAME_INDEX].toString();
        String subDepartmentCode = firstObject[SUB_DEPARTMENT_CODE_INDEX].toString();

        return AssignedProfileResponseDTO.builder()
                .subDepartmentName(subDepartmentName)
                .subDepartmentCode(subDepartmentCode)
                .assignedRolesResponseDTOS(assignedRolesResponseDTOS)
                .build();
    }
}
