package com.cogent.adminservice.controller;

import com.cogent.adminservice.dto.response.AssignedProfileMenuResponseDTO;
import com.cogent.adminservice.service.AdminService;
import com.cogent.adminservice.service.UserService;
import com.cogent.contextserver.filter.UserContext;
import com.cogent.contextserver.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import static org.springframework.http.ResponseEntity.ok;

@RestController("/gallery")
public class GalleryController {

    private UserService userService;
    private AdminService adminService;

    public GalleryController(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }


    @GetMapping("/test")
    public ResponseEntity<?> index(HttpServletRequest request, HttpServletResponse servletResponse) {

        System.out.println("inside gallery controller-------------------" + request.getHeader("username"));
        AssignedProfileMenuResponseDTO assignedProfileMenuResponseDTO =
                adminService.getAssignedRoles(request.getHeader("username"));
        return ok().body(assignedProfileMenuResponseDTO);
    }

    @GetMapping("/home")
    public ResponseEntity<?> home() {

        String userName = UserContext.getUsername();
        System.out.println(userName);

        String username;
        Object principal =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = User.builder()
                .firstName("Rupak")
                .lastName("Chaulagain")
                .email("rupakchaulagain@gmail.com")
                .username(userName)
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
