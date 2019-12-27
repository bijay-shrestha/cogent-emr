package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.subDepartment.SubDepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentDropDownDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.subdepartment.SubDepartmentResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DepartmentRepository;
import com.cogent.admin.repository.ProfileRepository;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.admin.service.SubDepartmentService;
import com.cogent.persistence.model.Department;
import com.cogent.persistence.model.Profile;
import com.cogent.persistence.model.SubDepartment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.cogent.admin.constants.excel.SubDepartmentExcelConstant.HEADERS;
import static com.cogent.admin.constants.excel.SubDepartmentExcelConstant.WORKBOOK_NAME;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.SubDepartmentLog.*;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.ExcelComposer.createExcel;
import static com.cogent.admin.utils.ExcelComposer.downloadFile;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static com.cogent.admin.utils.StringUtil.toUpperCase;
import static com.cogent.admin.utils.SubDepartmentUtils.parseToSubDepartment;
import static com.cogent.admin.utils.SubDepartmentUtils.updateSubDepartment;

@Slf4j
@Service
@Transactional
public class SubDepartmentServiceImpl implements SubDepartmentService {

    private final SubDepartmentRepository subDepartmentRepository;

    private final DepartmentRepository departmentRepository;

    private final ProfileRepository profileRepository;

    public SubDepartmentServiceImpl(SubDepartmentRepository subDepartmentRepository,
                                    DepartmentRepository departmentRepository,
                                    ProfileRepository profileRepository) {
        this.subDepartmentRepository = subDepartmentRepository;
        this.departmentRepository = departmentRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public void createSubDepartment(SubDepartmentRequestDTO subDepartmentRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, SUB_DEPARTMENT);

        validatetDuplicityByNameOrCode(subDepartmentRepository.fetchSubDepartmentByNameAndCode(
                subDepartmentRequestDTO.getName(), subDepartmentRequestDTO.getCode()),
                subDepartmentRequestDTO.getName(), subDepartmentRequestDTO.getCode(), SubDepartment.class);

        SubDepartment subDepartment = parseToSubDepartment.apply(subDepartmentRequestDTO,
                checkActiveDepartment(subDepartmentRequestDTO.getDepartmentId()));

        saveSubDepartment(subDepartment);

        log.info(SAVING_PROCESS_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

    }


    @Override
    public void deleteSubDepartment(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, SUB_DEPARTMENT);

        SubDepartment subDepartment = updateSubDepartment.apply(
                fetchSubDepartmentById(deleteRequestDTO.getId()), deleteRequestDTO);

        checkAndUpdateProfile(deleteRequestDTO.getId(), deleteRequestDTO.getRemarks(),
                deleteRequestDTO.getStatus());

        saveSubDepartment(subDepartment);

        log.info(DELETING_PROCESS_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));


    }

    @Override
    public void updateSubDepartment(SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, SUB_DEPARTMENT);

        SubDepartment subDepartmentToUpdate = fetchSubDepartmentById(subDepartmentUpdateRequestDTO.getId());

        validatetDuplicityByNameOrCode(subDepartmentRepository.checkSubDepartmentNameAndCodeIfExist(
                subDepartmentUpdateRequestDTO.getId(), subDepartmentUpdateRequestDTO.getName(),
                subDepartmentUpdateRequestDTO.getCode()),
                subDepartmentUpdateRequestDTO.getName(), subDepartmentUpdateRequestDTO.getCode(), SubDepartment.class);

        SubDepartment subDepartment = updateSubDepartment.apply(subDepartmentToUpdate,
                ConvertToDeleteRequestDTO(subDepartmentUpdateRequestDTO));

        subDepartment.setName(toUpperCase(subDepartmentUpdateRequestDTO.getName()));

        subDepartment.setCode(toUpperCase(subDepartmentUpdateRequestDTO.getCode()));

        subDepartment.setDepartment(checkActiveDepartment(subDepartmentUpdateRequestDTO.getDepartmentId()));

        checkAndUpdateProfile(subDepartmentUpdateRequestDTO.getId(), subDepartmentUpdateRequestDTO.getRemarks(),
                subDepartmentUpdateRequestDTO.getStatus());

        saveSubDepartment(subDepartment);

        log.info(UPDATING_PROCESS_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public List<SubDepartmentMinimalResponseDTO> searchSubDepartment(
            SubDepartmentSearchRequestDTO subDepartmentSearchRequestDTO, Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, SUB_DEPARTMENT);

        List<SubDepartmentMinimalResponseDTO> subDepartmentMinimalResponseDTOS =
                subDepartmentRepository.searchSubDepartment(subDepartmentSearchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return subDepartmentMinimalResponseDTOS;

    }


    @Override
    public List<SubDepartmentDropDownDTO> dropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, SUB_DEPARTMENT);

        List<SubDepartmentDropDownDTO> dropDownDTOS = subDepartmentRepository.fetchDropDownList()
                .orElseThrow(() -> new NoContentFoundException(SubDepartment.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return dropDownDTOS;

    }

    @Override
    public List<SubDepartmentDropDownDTO> activeDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, SUB_DEPARTMENT);

        List<SubDepartmentDropDownDTO> dropDownDTOS = subDepartmentRepository.fetchActiveDropDownList()
                .orElseThrow(() -> new NoContentFoundException(SubDepartment.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return dropDownDTOS;

    }

    @Override
    public List<SubDepartmentDropDownDTO> activeDropDownListByDepartmentId(Long departmentId) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN_BY_DEPARTMENT_ID, SUB_DEPARTMENT);

        List<SubDepartmentDropDownDTO> dropDownDTOS = subDepartmentRepository.fetchActiveDropDownListByDepartmentId(departmentId)
                .orElseThrow(() -> new NoContentFoundException(SubDepartment.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_BY_DEPARTMENT_ID_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return dropDownDTOS;
    }


    @Override
    public void createExcelInByteArray(HttpServletResponse response) throws IOException {

        log.info(CREATING_EXCEL_IN_BYTE_ARRAY_STARTED, SUB_DEPARTMENT);
        long startTime = System.currentTimeMillis();

        ByteArrayOutputStream out = createExcel(HEADERS, WORKBOOK_NAME, subDepartmentRepository.getSubDepartment());
        log.info(CREATING_EXCEL_IN_BYTE_ARRAY__COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        downloadFile(response, out);
        log.info(DOWNLOADING_PROCESS_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public SubDepartment findById(Long id) {
        return subDepartmentRepository.findActiveSubDepartmentById(id)
                .orElseThrow(() -> new NoContentFoundException(SubDepartment.class, "id", id.toString()));
    }

    @Override
    public void checkAndUpdateProfile(Long subDepartmentId, String remarks, Character status) {

        Profile profile = profileRepository.findProfileBySubDepartmentId(subDepartmentId);

        if (!Objects.isNull(profile)) {
            profile.setRemarks(remarks);
            profile.setStatus(status);
            profileRepository.save(profile);
        }
    }


    @Override
    public SubDepartmentResponseDTO fetchSubDepartmentDetailsById(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, SUB_DEPARTMENT);

        SubDepartmentResponseDTO subDepartmentResponseDTO = fetchSubDepartmentForDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, SUB_DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return subDepartmentResponseDTO;

    }

    public Department checkActiveDepartment(Long id) {
        return departmentRepository.findActiveDepartmentById(id).orElseThrow(() ->
                new NoContentFoundException(SubDepartment.class, "id", id.toString()));
    }


    public void saveSubDepartment(SubDepartment subDepartment) {
        subDepartmentRepository.save(subDepartment);
    }

    public SubDepartment fetchSubDepartmentById(Long id) {
        return subDepartmentRepository.fetchSubDepartmentById(id).orElseThrow(() ->
                new NoContentFoundException(SubDepartment.class, "id", id.toString()));

    }

    public SubDepartmentResponseDTO fetchSubDepartmentForDetails(Long id) {
        SubDepartmentResponseDTO subDepartmentResponseDTO = subDepartmentRepository
                .fetchSubDepartmentDetail(id).orElseThrow(() ->
                        new NoContentFoundException(SubDepartment.class, "id", id.toString()));
        return subDepartmentResponseDTO;
    }

    public DeleteRequestDTO ConvertToDeleteRequestDTO(SubDepartmentUpdateRequestDTO subDepartmentUpdateRequestDTO) {
        return ConvertToDeleteRequestDTO
                .apply(subDepartmentUpdateRequestDTO.getRemarks(),
                        subDepartmentUpdateRequestDTO.getStatus());
    }

}
