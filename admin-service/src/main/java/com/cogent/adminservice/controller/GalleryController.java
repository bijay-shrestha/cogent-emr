package com.cogent.adminservice.controller;

import com.cogent.adminservice.dto.response.AssignedProfileMenuResponseDTO;
import com.cogent.adminservice.filter.UserContext;
import com.cogent.adminservice.filter.UserContextHolder;
import com.cogent.adminservice.service.AdminService;
import com.cogent.adminservice.service.ProfileService;
import com.cogent.adminservice.service.UserService;
import com.cogent.persistence.model.Admin;
import com.cogent.persistence.model.Profile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/gallery")
@Slf4j
public class GalleryController {

    private UserService userService;
    private AdminService adminService;
    @Autowired
    private HttpServletRequest request;

    private final ProfileService profileService;

    public GalleryController(UserService userService,
                             AdminService adminService,
                             ProfileService profileService) {
        this.userService = userService;
        this.adminService = adminService;
        this.profileService = profileService;
    }

    @GetMapping("/super")
    public String testing(){
        return request.getHeader("username");
    }

    @GetMapping("/test")
    public ResponseEntity<?> index(HttpServletRequest request, HttpServletResponse servletResponse) {

        log.info("REQUEST HEADER AS {} AND USER-CONTEXT USERNAME AS {}",
                request.getHeader(UserContext.USERNAME),
                UserContext.getUsername());
        UserContext userContext = UserContextHolder.getContext();
        AssignedProfileMenuResponseDTO assignedProfileMenuResponseDTO =
                adminService.getAssignedRoles(request.getHeader("username"));
        return ok().body(assignedProfileMenuResponseDTO);
    }

    @GetMapping("/update")
    public ResponseEntity<?> home() {

        Admin admin = adminService.findAdminById(3L);
        admin.setEmail("dhanusha@gmail.com");

        userService.saveAndFlush(admin);

        return ok().body("Email successfully updated for ..." + admin.getUsername());
    }

    @GetMapping("/create")
    public ResponseEntity<?> createAdmin() {

        Profile profile = profileService.findProfileById(1L);

        Admin admin = Admin.builder()
                .email("dhanusha.roka@f1soft.com")
                .password("$2a$10$wJOicd./zCyF7b0KBRAyIOPi/VI1DD4aLrubJz5JO2Vq2UurkTQ1.")
                .username("dhanusha")
                .profile(profile)
                .build();

        userService.saveAndFlush(admin);

        return ok().body("Admin created successfully " + admin.getUsername());

    }

    @GetMapping("/hello")
    public String getGallery(){
        return  UserContext.getUsername() + " is running perfectly ...";
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
