package com.cogent.admin.controller.v1;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankUpdateRequestDTO;
import com.cogent.admin.service.impl.RegisteredBankServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static com.cogent.admin.constants.SwaggerConstants.RegisteredBankConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.RegisterdBankConstants.BASE_REGISTERED_BANK;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 12/10/19
 */

@RestController
@RequestMapping(API_V1 + BASE_REGISTERED_BANK)
@Api(BASE_REGISTERED_BANK_API_VALUE)
public class RegisteredBankController {

    private final RegisteredBankServiceImpl service;

    public RegisteredBankController(RegisteredBankServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @ApiOperation(SAVE_REGISTERED_BANK_OPERATION)
    public ResponseEntity<?> saveRegisteredBank(@Valid @RequestBody RegisteredBankRequestDTO requestDTO) {
        service.createRegisteredBank(requestDTO);
        return created(URI.create(API_V1 + BASE_REGISTERED_BANK)).build();
    }

    @DeleteMapping
    @ApiOperation(DELETE_REGISTERED_BANK_OPERATION)
    public ResponseEntity<?> deleteRegisteredBank(@Valid @RequestBody DeleteRequestDTO deleteRequestDTO) {
        service.deleteRegisteredBank(deleteRequestDTO);
        return ok().build();
    }

    @PutMapping
    @ApiOperation(UPDATE_REGISTERED_BANK_OPERATION)
    public ResponseEntity<?> updateRegisteredBank(@Valid @RequestBody RegisteredBankUpdateRequestDTO updateRequestDTO) {
        service.updateRegisteredBank(updateRequestDTO);
        return ok().build();
    }

    @PutMapping(SEARCH)
    @ApiOperation(SEARCH_REGISTERED_BANK_OPERATION)
    public ResponseEntity<?> searchRegisteredBank(@RequestBody RegisteredBankSearchRequestDTO searchRequestDTO,
                                                  @RequestParam("page") int page,
                                                  @RequestParam("size") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ok(service.searchRegisteredBank(searchRequestDTO, pageable));
    }

    @GetMapping(DETAILS + ID_PATH_VARIABLE_BASE)
    @ApiOperation(REGISTERED_BANK_DETAIL_OPERATION)
    public ResponseEntity<?> fetchRegisteredBankDetails(@PathVariable Long id) {
        return ok(service.fetchRegisteredBankDetails(id));
    }


    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_REGISTERED_BANK_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(service.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_REGISTERED_BANK_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(service.fetchActiveDropDownList());
    }
}
