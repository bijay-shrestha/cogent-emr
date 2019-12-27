package com.cogent.admin.controller.v1;

import com.cogent.admin.service.BillTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.SwaggerConstants.BillTypeConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.BillTypeConstant.FETCH_ACTIVE_BILL_TYPE;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.BillTypeConstants.BASE_BILL_TYPE;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 2019-10-22
 */
@RestController
@RequestMapping(API_V1 + BASE_BILL_TYPE)
@Api(BASE_API_VALUE)
public class BillTypeController {

    private final BillTypeService billTypeService;

    public BillTypeController(BillTypeService billTypeService) {
        this.billTypeService = billTypeService;
    }

    @GetMapping
    @ApiOperation(FETCH_ACTIVE_BILL_TYPE)
    public ResponseEntity<?> fetchActiveBillTypes() {
        return ok(billTypeService.fetchActiveBillType());
    }
}
