package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.admin.*;
import com.cogent.admin.dto.response.admin.AdminInfoByUsernameResponseDTO;
import com.cogent.admin.service.AdminService;
import com.cogent.admin.service.StorageService;
import com.cogent.admin.utils.ObjectMapperUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.cogent.admin.constants.StringConstant.BACKWARD_SLASH;
import static com.cogent.admin.constants.SwaggerConstants.AdminConstant;
import static com.cogent.admin.constants.SwaggerConstants.AdminConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.AdminConstants.*;
import static java.net.URI.create;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = API_V1 + BASE_ADMIN)
@Api(BASE_API_VALUE)
public class AdminController {

    private final AdminService adminService;

    private final StorageService storageService;

    public AdminController(AdminService adminService,
                           StorageService storageService) {
        this.adminService = adminService;
        this.storageService = storageService;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@RequestParam(value = "file", required = false) MultipartFile file,
                                  @RequestParam("request") String request) throws IOException {

        AdminRequestDTO adminRequestDTO = ObjectMapperUtils.map(request, AdminRequestDTO.class);
        adminService.save(adminRequestDTO, file);
        return created(create(API_V1 + BASE_ADMIN)).build();
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<List> fetchActiveAdminsForDropdown() {
        return ok(adminService.fetchActiveAdminsForDropdown());
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody AdminSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(adminService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(adminService.fetchDetailsById(id));
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        adminService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(PASSWORD)
    @ApiOperation(AdminConstant.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(@Valid @RequestBody UpdatePasswordRequestDTO requestDTO) {
        adminService.changePassword(requestDTO);
        return ok().build();
    }

    @PutMapping(value = AVATAR, consumes = MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(UPDATE_AVATAR_OPERATION)
    public ResponseEntity<?> updateAvatar(@RequestParam(value = "files", required = false) MultipartFile file,
                                          @RequestParam("adminId") Long adminId) {
        adminService.updateAvatar(file, adminId);
        return ok().build();
    }

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@RequestParam(value = "file", required = false) MultipartFile file,
                                    @RequestParam("request") String request) throws IOException {

        AdminUpdateRequestDTO adminRequestDTO = ObjectMapperUtils.map(request, AdminUpdateRequestDTO.class);
        adminService.update(adminRequestDTO, file);
        return ok().build();
    }

    @GetMapping(FILE)
    public ResponseEntity<Resource> downloadFile(@PathVariable String subDirectoryLocation,
                                                 @PathVariable String filename) {

        Resource file = storageService.loadAsResource(subDirectoryLocation, filename);

        return ok().header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + BACKWARD_SLASH + file.getFilename() + BACKWARD_SLASH)
                .body(file);
    }

    @GetMapping(VERIFY)
    @ApiOperation(VERIFY_ADMIN)
    public ResponseEntity<?> verify(@RequestParam(name = "token") String token) {
        adminService.verifyConfirmationToken(token);
        return ok().build();
    }

    @PostMapping(PASSWORD)
    @ApiOperation(SAVE_PASSWORD_OPERATION)
    public ResponseEntity<?> savePassword(@Valid @RequestBody PasswordRequestDTO requestDTO) {
        adminService.savePassword(requestDTO);
        return ok().build();
    }

    @PutMapping(INFO)
    @ApiOperation(FETCH_LOGGED_IN_ADMIN_INFO)
    public ResponseEntity<?> fetchLoggedInAdminInfo(@Valid @RequestBody AdminInfoRequestDTO requestDTO) {
        return ok(adminService.fetchLoggedInAdminInfo(requestDTO));
    }

    @GetMapping(USERNAME_VARIABLE_BASE)
    @ApiOperation(FETCH_INFO_BY_USERNAME)
    public AdminInfoByUsernameResponseDTO fetchAdminInfoByUsername(@PathVariable("username") String username) {
        return adminService.fetchAdminInfoByUsername(username);
    }
}
