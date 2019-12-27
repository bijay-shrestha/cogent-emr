package com.cogent.admin.controller.v1;

import com.cogent.admin.service.PaymentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.SwaggerConstants.PaymentTypeConstant.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.*;
import static com.cogent.admin.constants.WebResourceKeyConstants.PaymentTypeConstants.BASE_PAYMENT_TYPE;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author Sauravi Thapa 12/11/19
 */

@RestController
@RequestMapping(API_V1 + BASE_PAYMENT_TYPE)
@Api(value = BASE_PAYMENT_TYPE_VALUE)
public class PaymentTypeController {

    private final PaymentTypeService service;

    public PaymentTypeController(PaymentTypeService service) {
        this.service = service;
    }

    @GetMapping(DROPDOWN)
    @ApiOperation(FETCH_PAYMENT_TYPET_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> dropDown() {
        return ok(service.fetchDropDownList());
    }

    @GetMapping(DROPDOWN + ACTIVE)
    @ApiOperation(FETCH_ACTIVE_PAYMENT_TYPE_FOR_DROP_DOWN_OPERATION)
    public ResponseEntity<?> activeDropDown() {
        return ok(service.fetchActiveDropDownList());
    }
}
