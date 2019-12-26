package com.cogent.adminservice.controller;

import com.cogent.adminservice.dto.response.ProfileMenuResponseDTO;
import com.cogent.adminservice.dto.response.AssignedProfileMenuResponseDTO;
import com.cogent.adminservice.dto.response.AssignedRoleResponseDTO;
import com.cogent.adminservice.dto.response.MenuResponseDTO;
import com.cogent.adminservice.service.AdminService;
import com.cogent.adminservice.service.UserService;
import com.cogent.contextserver.filter.UserContext;
import com.cogent.genericservice.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class GalleryController {

    private UserService userService;
    private AdminService adminService;

    public GalleryController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }


    @GetMapping("/")
    public ResponseEntity<?> index(HttpServletRequest request, HttpServletResponse servletResponse) {

        System.out.println("inside gallery controller-------------------" + request.getHeader("username"));
//        List<ProfileMenu> profileMenu = adminService.getAdminByUsername(request.getHeader("username"));

        List<Long> userMenuIdList = new ArrayList<>();
        List<AssignedRoleResponseDTO> assignedRolesList = new ArrayList<>();
        List<MenuResponseDTO> menuResponseDTOS = new ArrayList<>();

        AssignedProfileMenuResponseDTO assignedProfileMenuResponseDTO =
                adminService.getAssignedRoles(request.getHeader("username"));


//        List<ProfileMenuResponseDTO> withoutDupes = profileMenuList.stream()
//                .distinct()
//                .collect(Collectors.toList());
//
//        System.out.println("List without duplicates: " + withoutDupes);


//        Map<Long, List<ProfileMenuResponseDTO>> byparentId = profileMenuList.stream().collect(
//                groupingBy(ProfileMenuResponseDTO::getParentId));
//
////        Map<Long, List<ProfileMenuResponseDTO>> byuserMenuId = profileMenuList.stream().collect(
////                Collectors.groupingBy(ProfileMenuResponseDTO::getUserMenuId));
//
//
//
//
//        List<ChildMenus> childMenusList=new ArrayList<>();
//        for(ProfileMenuResponseDTO profileMenuResponseDTO:profileMenuList){
//
//            ChildMenus childMenus=new ChildMenus();
//            childMenus.setUserMenuId(profileMenuResponseDTO.getUserMenuId());
//
//
//        }


//
//
//        Map<Long, List<ProfileMenuResponseDTO>> byuserMenuId = profileMenuList.stream().collect(
//                Collectors.groupingBy(ProfileMenuResponseDTO::getParentId));
//
//        List<ChildMenus> childMenusList = new ArrayList<>();
//        List<Long> rolesId = new ArrayList<>();
//
//        List<ProfileMenuResponseDTO> responseDTOS =
//                getDuplicatesMap(profileMenuList).values()
//                        .stream()
//                        .filter(duplicates -> duplicates.size() > 1)
//                        .flatMap(Collection::stream)
//                        .collect(Collectors.toList());
//
//        responseDTOS.forEach(responseDTO -> {
//            rolesId.add(responseDTO.getRoleId());
//        });
//
//        Map<Long, List<ProfileMenuResponseDTO>> byparentId = profileMenuList.stream().collect(
//                groupingBy(ProfileMenuResponseDTO::getParentId));

//        Map<Long, List<ProfileMenuResponseDTO>> byuserMenuId = profileMenuList.stream().collect(
//                Collectors.groupingBy(ProfileMenuResponseDTO::getUserMenuId));


//        for (Map.Entry<Long, List<ProfileMenuResponseDTO>> profile : byparentId.entrySet()) {
//            System.out.println(profile.getKey() + "/" + profile.getValue());
//        }
//
//        HashMap childMenusMap = new HashMap();
//        childMenusMap.put("childMenus", byparentId);
//
//        JSONOBJ jsonobj = new JSONOBJ();
//        jsonobj.setSubDepartmentCode("MED");
//        jsonobj.setChildMenus(childMenusList);


        return ok().body(assignedProfileMenuResponseDTO);
    }

    private static Map<Long, List<ProfileMenuResponseDTO>> getDuplicatesMap
            (List<ProfileMenuResponseDTO> profileMenuResponseDTOS) {
        return profileMenuResponseDTOS.stream()
                .collect(groupingBy(ProfileMenuResponseDTO::getUserMenuId));
    }

    @GetMapping("/home")
    public ResponseEntity<?> home() {

        String userName = UserContext.getUsername();
        System.out.println(userName);

        User user = User.builder()
                .firstName("Rupak")
                .lastName("Chaulagain")
                .email("rupakchaulagain@gmail.com")
                .build();

        userService.save(user);

        return ok().body("Welcome to the Gallery Server " + userName);

    }


    // Function to remove duplicates from an ArrayList
    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }


}
