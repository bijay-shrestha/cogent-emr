package com.cogent.admin.controller.v1;

import com.cogent.admin.service.WeekDaysService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cogent.admin.constants.SwaggerConstants.WeekDaysConstant.BASE_API_VALUE;
import static com.cogent.admin.constants.SwaggerConstants.WeekDaysConstant.FETCH_ACTIVE_WEEK_DAYS;
import static com.cogent.admin.constants.WebResourceKeyConstants.API_V1;
import static com.cogent.admin.constants.WebResourceKeyConstants.WeekDaysConstants.BASE_WEEK_DAYS;
import static org.springframework.http.ResponseEntity.ok;

/**
 * @author smriti on 25/11/2019
 */
@RestController
@RequestMapping(API_V1 + BASE_WEEK_DAYS)
@Api(BASE_API_VALUE)
public class WeekDaysController {

    private final WeekDaysService weekDaysService;

    public WeekDaysController(WeekDaysService weekDaysService) {
        this.weekDaysService = weekDaysService;
    }

    @GetMapping
    @ApiOperation(FETCH_ACTIVE_WEEK_DAYS)
    public ResponseEntity<?> fetchActiveWeekDays() {
        return ok(weekDaysService.fetchActiveWeekDays());
    }
}
