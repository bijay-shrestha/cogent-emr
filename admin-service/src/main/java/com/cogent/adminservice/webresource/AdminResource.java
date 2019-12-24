package com.cogent.adminservice.webresource;

import com.cogent.adminservice.service.AdminService;
import com.cogent.adminservice.service.UserService;
import com.cogent.contextserver.filter.UserContext;
import com.cogent.persistence.model.ProfileMenu;
import com.cogent.genericservice.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminResource {

   private final UserService userService;
   private final AdminService adminService;

    public AdminResource(UserService userService, AdminService adminService) {
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/")
    public ResponseEntity<?> index(HttpServletRequest request, HttpServletResponse servletResponse) {

        System.out.println("inside gallery controller-------------------" + request.getHeader("username"));
        List<ProfileMenu> profileMenu = adminService.getAdminByUsername(request.getHeader("username"));

        return ok().body(profileMenu);
    }

    @GetMapping("/register")
    public ResponseEntity<?> home() {

        String userName = UserContext.getUsername();
        System.out.println(userName);

        User user = User.builder()
                .firstName("Bijay")
                .lastName("Shrestha")
                .email("8ijay.shrestha@gmail.com")
                .build();

        userService.save(user);

        return ok().body("Welcome to the Adminå Server Resoure" + userName);

    }
}
