package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorSearchRequestDTO;
import com.cogent.admin.dto.request.doctor.DoctorUpdateRequestDTO;
import com.cogent.admin.service.DoctorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static com.cogent.admin.constants.SwaggerConstants.DoctorConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.DoctorConstants.*;
import static com.cogent.admin.utils.ObjectMapperUtils.map;
import static java.net.URI.create;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-09-29
 */
@RestController
@RequestMapping(value = API_V1 + BASE_DOCTOR)
@Api(BASE_API_VALUE)
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(SAVE_OPERATION)
    public ResponseEntity<?> save(@RequestPart(value = "avatar", required = false) MultipartFile avatar,
                                  @RequestPart(value = "signature", required = false) MultipartFile signature,
                                  @RequestParam("request") String request) throws IOException {

        DoctorRequestDTO requestDTO = map(request, DoctorRequestDTO.class);
        doctorService.save(requestDTO, avatar, signature);
        return created(create(API_V1 + BASE_DOCTOR)).build();
    }

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(UPDATE_OPERATION)
    public ResponseEntity<?> update(@RequestPart(value = "file", required = false) MultipartFile avatar,
                                    @RequestPart(value = "file", required = false) MultipartFile signature,
                                    @RequestParam("request") String request) throws IOException {

        DoctorUpdateRequestDTO updateRequestDTO = map(request, DoctorUpdateRequestDTO.class);
        doctorService.update(updateRequestDTO, avatar, signature);
        return ok().build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_OPERATION)
    public ResponseEntity<?> delete(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        doctorService.delete(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_OPERATION)
    public ResponseEntity<?> search(@RequestBody DoctorSearchRequestDTO searchRequestDTO,
                                    @RequestParam("page") int page,
                                    @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ok().body(doctorService.search(searchRequestDTO, pageable));
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchDoctorForDropDown() {
        return ok(doctorService.fetchDoctorForDropdown());
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsById(@PathVariable("id") Long id) {
        return ok(doctorService.fetchDetailsById(id));
    }

    @GetMapping(UPDATE_DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(DETAILS_OPERATION)
    public ResponseEntity<?> fetchDetailsForUpdate(@PathVariable("id") Long id) {
        return ok(doctorService.fetchDetailsForUpdate(id));
    }

    @GetMapping(SPECIALIZATION_ID_PATH_VARIABLE_BASE)
    @ApiOperation(FETCH_DETAILS_FOR_DROPDOWN)
    public ResponseEntity<?> fetchDoctorBySpecializationId(@PathVariable("specializationId") Long specializationId) {
        return ok(doctorService.fetchDoctorBySpecializationId(specializationId));
    }

}
