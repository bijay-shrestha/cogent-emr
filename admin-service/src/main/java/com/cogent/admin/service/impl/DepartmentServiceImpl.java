package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentSearchRequestDTO;
import com.cogent.admin.dto.request.department.DepartmentUpdateRequestDTO;
import com.cogent.admin.dto.response.department.DepartmentDropDownDTO;
import com.cogent.admin.dto.response.department.DepartmentMinimalResponseDTO;
import com.cogent.admin.dto.response.department.DepartmentResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.DepartmentRepository;
import com.cogent.admin.repository.SubDepartmentRepository;
import com.cogent.admin.service.DepartmentService;
import com.cogent.admin.service.SubDepartmentService;
import com.cogent.admin.utils.DepartmentUtils;
import com.cogent.persistence.model.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.cogent.admin.constants.excel.DepartmentExcelConstant.HEADERS;
import static com.cogent.admin.constants.excel.DepartmentExcelConstant.WORKBOOK_NAME;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.DepartmentLog.DEPARTMENT;
import static com.cogent.admin.utils.CommonConverterUtils.ConvertToDeleteRequestDTO;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.DepartmentUtils.updateDepartment;
import static com.cogent.admin.utils.ExcelComposer.createExcel;
import static com.cogent.admin.utils.ExcelComposer.downloadFile;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static com.cogent.admin.utils.StringUtil.toUpperCase;
import static com.cogent.admin.utils.SubDepartmentUtils.parseToUpdateListOfSubDepartment;
import static com.cogent.admin.utils.SubDepartmentUtils.subDepartmentDeleteRequestDTO;

/**
 * @author Sauravi
 */

@Slf4j
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    private SubDepartmentRepository subDepartmentRepository;

    private SubDepartmentService subDepartmentService;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 SubDepartmentRepository subDepartmentRepository,
                                 SubDepartmentService subDepartmentService) {
        this.departmentRepository = departmentRepository;
        this.subDepartmentRepository = subDepartmentRepository;
        this.subDepartmentService = subDepartmentService;
    }

    @Override
    public void createDepartment(DepartmentRequestDTO departmentRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, DEPARTMENT);

        validatetDuplicityByNameOrCode(departmentRepository.fetchDepartmentByNameOrCode(departmentRequestDTO.getName(),
                departmentRequestDTO.getCode()), departmentRequestDTO.getName(),
                departmentRequestDTO.getCode(), Department.class);

        saveDepartment(DepartmentUtils.parseToDepartment(departmentRequestDTO));

        log.info(SAVING_PROCESS_COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

    }


    @Override
    public DepartmentResponseDTO fetchDepartmentDetails(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, DEPARTMENT);

        DepartmentResponseDTO departmentResponseDTO = departmentRepository
                .fetchDepartmentDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return departmentResponseDTO;
    }

    @Override
    public void deleteDepartment(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, DEPARTMENT);

        saveDepartment(updateDepartment.apply(fetchDepartmentById(deleteRequestDTO.getId()), deleteRequestDTO));

        subDepartmentToDelete(subDepartmentDeleteRequestDTO.apply(deleteRequestDTO,
                subDepartmentRepository.fetchSubDepartmentToDeleteByDepartmentId(deleteRequestDTO.getId())));

        log.info(DELETING_PROCESS_COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void updateDepartment(DepartmentUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, DEPARTMENT);

        Department departmentToUpdate = fetchDepartmentById(requestDTO.getId());


        validatetDuplicityByNameOrCode(departmentRepository.checkIfDepartmentNameAndCodeExists(requestDTO.getId(), requestDTO.getName(),
                requestDTO.getCode()), requestDTO.getName(),
                requestDTO.getCode(), Department.class);

        Department department = updateDepartment.apply(departmentToUpdate
                , ConvertToDeleteRequestDTO(requestDTO));

        department.setName(toUpperCase(requestDTO.getName()));

        department.setCode(toUpperCase(requestDTO.getCode()));

        saveSubDepartmentToUpdate(requestDTO);

        saveDepartment(department);

        log.info(UPDATING_PROCESS_COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

    }


    @Override
    public List<DepartmentDropDownDTO> dropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, DEPARTMENT);

        List<DepartmentDropDownDTO> departmentDropDownDTOS = departmentRepository.fetchDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Department.class));

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return departmentDropDownDTOS;

    }

    @Override
    public List<DepartmentDropDownDTO> activeDropDownList() {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, DEPARTMENT);

        List<DepartmentDropDownDTO> departmentDropDownDTOS = departmentRepository.fetchActiveDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Department.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return departmentDropDownDTOS;
    }

    @Override
    public List<DepartmentMinimalResponseDTO> searchDepartment(
            DepartmentSearchRequestDTO departmentSearchRequestDTO,
            Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, DEPARTMENT);

        List<DepartmentMinimalResponseDTO> departmentMinimalResponseDTO = departmentRepository.searchDepartment
                (departmentSearchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        return departmentMinimalResponseDTO;

    }

    @Override
    public void createExcelInByteArray(HttpServletResponse response) throws IOException {

        log.info(CREATING_EXCEL_IN_BYTE_ARRAY_STARTED, DEPARTMENT);
        long startTime = System.currentTimeMillis();

        ByteArrayOutputStream out = createExcel(HEADERS, WORKBOOK_NAME, departmentRepository.getDepartment());
        log.info(CREATING_EXCEL_IN_BYTE_ARRAY__COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

        downloadFile(response, out);
        log.info(DOWNLOADING_PROCESS_COMPLETED, DEPARTMENT, getDifferenceBetweenTwoTime(startTime));

    }

    public Department fetchDepartmentById(Long id) {
        return departmentRepository.findDepartmentById(id).orElseThrow(() ->
                new NoContentFoundException(Department.class, "id", id.toString()));
    }

    public void saveSubDepartmentToUpdate(DepartmentUpdateRequestDTO departmentUpdateRequestDTO) {
        checkAndUpdateProfile(subDepartmentRepository.fetchSubDepartmentIdByDepartmentId(
                departmentUpdateRequestDTO.getId()), departmentUpdateRequestDTO);

        subDepartmentRepository.saveAll(parseToUpdateListOfSubDepartment
                .apply(subDepartmentRepository
                        .fetchSubDepartmentByDepartmentId(
                                departmentUpdateRequestDTO.getId()), departmentUpdateRequestDTO));
    }

    public void subDepartmentToDelete(List<DeleteRequestDTO> deleteRequestDTOS) {
        deleteRequestDTOS.forEach(deleteRequestDTO -> {
            subDepartmentService.deleteSubDepartment(deleteRequestDTO);
        });
    }

    public void checkAndUpdateProfile(List<Long> subDepartmentIds, DepartmentUpdateRequestDTO departmentUpdateRequestDTO) {
        subDepartmentIds.forEach(subDepartmentId -> {
            subDepartmentService.checkAndUpdateProfile(subDepartmentId, departmentUpdateRequestDTO.getRemarks(),
                    departmentUpdateRequestDTO.getStatus());
        });
    }

    public DeleteRequestDTO ConvertToDeleteRequestDTO(DepartmentUpdateRequestDTO departmentUpdateRequestDTO) {
        return ConvertToDeleteRequestDTO
                .apply(departmentUpdateRequestDTO.getRemarks(),
                        departmentUpdateRequestDTO.getStatus());
    }

    public void saveDepartment(Department department) {
        departmentRepository.save(department);
    }

}
