package com.cogent.admin.repository;

import com.cogent.admin.AssignedProfileMenuResponseDTO;
import com.cogent.admin.AssignedRolesResponseDTO;
import com.cogent.admin.ChildMenusResponseDTO;
import com.cogent.persistence.model.Profile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ProfileRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    ProfileRepository profileRepository;

    @Test
    public void testFindProfileById() {
        Optional<Profile> profile = profileRepository.findProfileById(1L);
        assertNotNull(profile);
    }

    @Test
    public void testFindProfileBySubDepartmentId() {
        Profile profile = profileRepository.findProfileBySubDepartmentId(1L);
        assertNotNull(profile);
    }

    @Test
    public void fetchAssignedProfileMenuResponse() {

        String SQL = " SELECT" +
                " pm.parentId as parentId," +                                       //[0]
                " pm.userMenuId as userMenuId," +                                   //[1]
                " pm.roleId as roleId," +                                           //[2]
                " sd.code as subDepartmentCode, " +                                 //[3]
                " sd.name as subDepartmentName" +                                   //[4]
                " FROM ProfileMenu pm" +
                " LEFT JOIN Profile p ON p.id =pm.profile.id" +
                " LEFT JOIN AdminProfile ap ON ap.profileId = p.id" +
                " LEFT JOIN Admin a ON a.id = ap.adminId" +
                " LEFT JOIN SubDepartment  sd ON sd.id = p.subDepartment.id " +
                " WHERE pm.status = 'Y'" +
                " AND a.username = :username OR a.email=:email";

        Query query = testEntityManager.getEntityManager().createQuery(SQL)
                .setParameter("username", "smriti")
                .setParameter("email", "smriti");

        List<Object[]> results = query.getResultList();

        String subDepartmentName = "";

        String subDepartmentCode = "";

        List<ChildMenusResponseDTO> childMenusResponseDTOS = new ArrayList<>();

        for (Object[] result : results) {

            Long parentId = Long.parseLong(result[0].toString());
            Long userMenuId = Long.parseLong(result[1].toString());
            Long roleId = Long.parseLong(result[2].toString());

            ChildMenusResponseDTO isProfileMenuMatched = childMenusResponseDTOS.stream()
                    .filter(childMenusResponseDTO ->
                            ((childMenusResponseDTO.getParentId().equals(parentId))
                                    &&
                                    (childMenusResponseDTO.getUserMenuId().equals(userMenuId)))
                    )
                    .findAny().orElse(null);

            if (!Objects.isNull(isProfileMenuMatched)) {

                List<Long> roleIds = new ArrayList<>(isProfileMenuMatched.getRoleId());
                roleIds.add(roleId);
                isProfileMenuMatched.setRoleId(roleIds);
            } else {
                ChildMenusResponseDTO responseDTO = ChildMenusResponseDTO.builder()
                        .parentId(parentId)
                        .userMenuId(userMenuId)
                        .roleId(Arrays.asList(roleId))
                        .build();

                childMenusResponseDTOS.add(responseDTO);
            }

            subDepartmentCode = result[3].toString();
            subDepartmentName = result[4].toString();

        }

        List<AssignedRolesResponseDTO> assignedRolesResponseDTOS = new ArrayList<>();

        Map<Long, List<ChildMenusResponseDTO>> groupByParentId =
                childMenusResponseDTOS.stream().collect(Collectors.groupingBy(ChildMenusResponseDTO::getParentId));

        for (Map.Entry<Long, List<ChildMenusResponseDTO>> entry : groupByParentId.entrySet()) {

            AssignedRolesResponseDTO assignedRolesResponseDTO =
                    AssignedRolesResponseDTO.builder()
                            .parentId(entry.getKey())
                            .childMenus(entry.getValue())
                            .build();
            assignedRolesResponseDTOS.add(assignedRolesResponseDTO);
        }

        AssignedProfileMenuResponseDTO responseDTO = AssignedProfileMenuResponseDTO.builder()
                .subDepartmentName(subDepartmentName)
                .subDepartmentCode(subDepartmentCode)
                .assignedRolesResponseDTOS(assignedRolesResponseDTOS)
                .build();

        System.out.println(assignedRolesResponseDTOS);
    }

    @Test
    public void fetchAssignedProfileMenuResponseNative() {

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
                " GROUP BY pm.parent_id, pm.user_menu_id, pm.profile_id";

        Query query = testEntityManager.getEntityManager().createNativeQuery(SQL)
                .setParameter("username", "smriti")
                .setParameter("email", "smriti");

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


    }
}
