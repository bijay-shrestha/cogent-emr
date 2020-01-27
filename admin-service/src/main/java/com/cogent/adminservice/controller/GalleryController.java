package com.cogent.adminservice.controller;

import com.cogent.adminservice.dto.response.AssignedProfileMenuResponseDTO;
import com.cogent.adminservice.filter.UserContext;
import com.cogent.adminservice.filter.UserContextHolder;
import com.cogent.adminservice.service.AdminService;
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

//import com.cogent.persistence.model.Admin;
//import com.cogent.persistence.model.Profile;

@RestController
@RequestMapping("/gallery")
@Slf4j
public class GalleryController {

    private UserService userService;
    private AdminService adminService;


    @Autowired
    private HttpServletRequest request;

    public GalleryController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/super")
    public String testing(){
        return request.getHeader("username");
    }

    @GetMapping("/test")
    public ResponseEntity<?> index(HttpServletRequest request, HttpServletResponse servletResponse) {

        log.info("REQUEST HEADER AS {} AND USER-CONTEXT USERNAME AS {}",
                request.getHeader("AUTHENTICATED_USER"),
                UserContext.getUsername());
        UserContext userContext = UserContextHolder.getContext();
        AssignedProfileMenuResponseDTO assignedProfileMenuResponseDTO =
                adminService.getAssignedRoles(request.getHeader("username"));
        return ok().body(assignedProfileMenuResponseDTO);
    }

    @GetMapping("/user")
    public ResponseEntity<?> home() {

        String username = UserContext.getUsername();
        Admin user = Admin.builder()
                .id(1L)
                .email("bijay.shrestha@f1soft.com")
                .password("$2a$10$wJOicd./zCyF7b0KBRAyIOPi/VI1DD4aLrubJz5JO2Vq2UurkTQ1.")
                .username(username).profile(new Profile(1L))
                .build();

        userService.save(user);

        return ok().body("Welcome to the Gallery Server " + user);
//        return ok().body("helo world");

    }

    @GetMapping("/hello")
    public String getGallery(){

        log.info("wakaa waakkkaa {}", UserContext.getUsername());
        return  "Bijay :: is running ...";
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
