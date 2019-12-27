package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategorySearchRequestDTO;
import com.cogent.admin.dto.request.adminCategory.AdminCategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryDropdownDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.adminCategory.AdminCategoryResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.AdminCategoryRepository;
import com.cogent.admin.service.AdminCategoryService;
import com.cogent.persistence.model.AdminCategory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.cogent.admin.constants.ErrorMessageConstants.AdminCategoryServiceMessage.*;
import static com.cogent.admin.constants.excel.AdminCategoryExcelConstants.HEADERS;
import static com.cogent.admin.constants.excel.AdminCategoryExcelConstants.WORKBOOK_NAME;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.AdminCategoryLog.ADMIN_CATEGORY;
import static com.cogent.admin.utils.AdminCategoryUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.ExcelComposer.createExcel;
import static com.cogent.admin.utils.ExcelComposer.downloadFile;
import static java.lang.reflect.Array.get;

/**
 * @author smriti on 2019-08-11
 */
@Service
@Transactional
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final AdminCategoryRepository adminCategoryRepository;

    public AdminCategoryServiceImpl(AdminCategoryRepository adminCategoryRepository) {
        this.adminCategoryRepository = adminCategoryRepository;
    }

    @Override
    public void save(AdminCategoryRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, ADMIN_CATEGORY);

        List adminCategoryByNameOrCode = adminCategoryRepository.findAdminCategoryByNameOrCode
                (requestDTO.getName(), requestDTO.getCode());

        validateAdminCategoryDuplicity(adminCategoryByNameOrCode, requestDTO.getName(), requestDTO.getCode());

        save(convertDTOToAdminCategory(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, ADMIN_CATEGORY, getDifferenceBetweenTwoTime(startTime));
    }

    public void validateAdminCategoryDuplicity(List<Object[]> adminCategoryByNameOrCode,
                                               String requestName,
                                               String requestCode) {
        final int NAME = 0;
        final int CODE = 1;

        adminCategoryByNameOrCode.forEach(adminCategory -> {
            boolean isNameExists = requestName.equalsIgnoreCase((String) get(adminCategory, NAME));

            boolean isCodeExists = requestCode.equalsIgnoreCase((String) get(adminCategory, CODE));

            if (isNameExists && isCodeExists)
                throw NAME_AND_CODE_DUPLICATION.get();

            validateName(isNameExists, requestName);
            validateCode(isCodeExists, requestCode);
        });
    }

    private void validateName(boolean isNameExists, String name) {
        if (isNameExists)
            throw new DataDuplicationException(AdminCategory.class,
                    NAME_DUPLICATION_MESSAGE + name, NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private void validateCode(boolean isCodeExists, String code) {
        if (isCodeExists)
            throw new DataDuplicationException(AdminCategory.class,
                    CODE_DUPLICATION_MESSAGE + code, CODE_DUPLICATION_DEBUG_MESSAGE + code);
    }

    @Override
    public void update(AdminCategoryUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, ADMIN_CATEGORY);

        AdminCategory adminCategory = findAdminCategoryById(requestDTO.getId());

        List adminCategoryByIdAndNameOrCode = adminCategoryRepository.findAdminCategoryByIdAndNameOrCode
                (requestDTO.getId(), requestDTO.getName(), requestDTO.getCode());

        validateAdminCategoryDuplicity(adminCategoryByIdAndNameOrCode, requestDTO.getName(), requestDTO.getCode());

        save(convertToUpdatedAdminCategory(requestDTO, adminCategory));

        log.info(UPDATING_PROCESS_COMPLETED, ADMIN_CATEGORY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, ADMIN_CATEGORY);

        AdminCategory adminCategory = findAdminCategoryById(deleteRequestDTO.getId());

        save(convertToDeletedAdminCategory(adminCategory, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, ADMIN_CATEGORY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<AdminCategoryMinimalResponseDTO> search(AdminCategorySearchRequestDTO searchRequestDTO,
                                                        Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, ADMIN_CATEGORY);

        List<AdminCategoryMinimalResponseDTO> responseDTOS =
                adminCategoryRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, ADMIN_CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<AdminCategoryDropdownDTO> fetchActiveAdminCategoryForDropDown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, ADMIN_CATEGORY);

        List<AdminCategoryDropdownDTO> responseDTOS = adminCategoryRepository.fetchActiveAdminCategoryForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, ADMIN_CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public AdminCategoryResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, ADMIN_CATEGORY);

        AdminCategoryResponseDTO responseDTO = adminCategoryRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, ADMIN_CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public AdminCategory fetchActiveAdminCategoryById(Long id) {
        return adminCategoryRepository.findActiveAdminCategoryById(id).orElseThrow(() ->
                ADMIN_CATEGORY_WITH_GIVEN_ID_NOT_FOUND.apply(id));
    }

    @Override
    public void createExcelInByteArray(HttpServletResponse response) throws IOException {
        log.info(CREATING_EXCEL_IN_BYTE_ARRAY_STARTED, ADMIN_CATEGORY);
        long startTime = System.currentTimeMillis();

        ByteArrayOutputStream out = createExcel(HEADERS, WORKBOOK_NAME, adminCategoryRepository.getAdminCategoryForExcel());
        log.info(CREATING_EXCEL_IN_BYTE_ARRAY__COMPLETED, ADMIN_CATEGORY, getDifferenceBetweenTwoTime(startTime));

        downloadFile(response, out);
        log.info(DOWNLOADING_PROCESS_COMPLETED, ADMIN_CATEGORY, getDifferenceBetweenTwoTime(startTime));
    }

    public AdminCategory findAdminCategoryById(Long adminCategoryId) {
        return adminCategoryRepository.findAdminCategoryById(adminCategoryId)
                .orElseThrow(() -> ADMIN_CATEGORY_WITH_GIVEN_ID_NOT_FOUND.apply(adminCategoryId));
    }

    private void save(AdminCategory adminCategory) {
        adminCategoryRepository.save(adminCategory);
    }

    private Supplier<DataDuplicationException> NAME_AND_CODE_DUPLICATION = () ->
            new DataDuplicationException(AdminCategory.class, NAME_AND_CODE_DUPLICATION_MESSAGE,
                    NAME_AND_CODE_DUPLICATION_DEBUG_MESSAGE);

    private Function<Long, NoContentFoundException> ADMIN_CATEGORY_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(AdminCategory.class, "id", id.toString());
    };
}
