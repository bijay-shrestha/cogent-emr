package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleSearchRequestDTO;
import com.cogent.admin.dto.request.applicationModules.ApplicationModuleUpdateRequestDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDetailResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleDropdownResponseDTO;
import com.cogent.admin.dto.response.applicationModules.ApplicationModuleMinimalResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.ApplicationModuleRepository;
import com.cogent.admin.service.ApplicationModuleService;
import com.cogent.admin.service.SubDepartmentService;
import com.cogent.persistence.model.ApplicationModule;
import com.cogent.persistence.model.SubDepartment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.ApplicationModuleLog.APPLICATION_MODULE;
import static com.cogent.admin.utils.ApplicationModuleUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;

/**
 * @author smriti ON 24/12/2019
 */
@Service
@Transactional
@Slf4j
public class ApplicationModuleServiceImpl implements ApplicationModuleService {

    private final ApplicationModuleRepository applicationModuleRepository;

    private final SubDepartmentService subDepartmentService;

    public ApplicationModuleServiceImpl(ApplicationModuleRepository applicationModuleRepository,
                                        SubDepartmentService subDepartmentService) {
        this.applicationModuleRepository = applicationModuleRepository;
        this.subDepartmentService = subDepartmentService;
    }

    @Override
    public void save(ApplicationModuleRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, APPLICATION_MODULE);

        validateName(applicationModuleRepository.fetchApplicationModuleByName(requestDTO.getName()),
                requestDTO.getName());

        SubDepartment subDepartment = subDepartmentService.findById(requestDTO.getSubDepartmentId());

        save(parseToApplicationModule(requestDTO, subDepartment));

        log.info(SAVING_PROCESS_COMPLETED, APPLICATION_MODULE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void update(ApplicationModuleUpdateRequestDTO updateRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, APPLICATION_MODULE);

        ApplicationModule applicationModule = findApplicationModuleById(updateRequestDTO.getId());

        validateName(applicationModuleRepository.fetchApplicationModuleByIdAndName(updateRequestDTO.getId(),
                updateRequestDTO.getName()), updateRequestDTO.getName());

        SubDepartment subDepartment = subDepartmentService.findById(updateRequestDTO.getSubDepartmentId());

        parseToUpdatedApplicationModule(applicationModule, updateRequestDTO, subDepartment);

        log.info(UPDATING_PROCESS_COMPLETED, APPLICATION_MODULE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, APPLICATION_MODULE);

        ApplicationModule applicationModule = findApplicationModuleById(deleteRequestDTO.getId());

        convertToDeletedApplicationModule(applicationModule, deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, APPLICATION_MODULE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<ApplicationModuleMinimalResponseDTO> search(ApplicationModuleSearchRequestDTO searchRequestDTO,
                                                            Pageable pageable) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, APPLICATION_MODULE);

        List<ApplicationModuleMinimalResponseDTO> responseDTOS =
                applicationModuleRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, APPLICATION_MODULE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public ApplicationModuleDetailResponseDTO fetchDetailsById(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, APPLICATION_MODULE);

        ApplicationModuleDetailResponseDTO responseDTO = applicationModuleRepository.fetchDetailsById(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, APPLICATION_MODULE, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public List<ApplicationModuleDropdownResponseDTO> fetchActiveApplicationModuleForDropDown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, APPLICATION_MODULE);

        List<ApplicationModuleDropdownResponseDTO> responseDTOS =
                applicationModuleRepository.fetchActiveApplicationModuleForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, APPLICATION_MODULE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    private void validateName(Long applicationModuleCount, String name) {
        if (applicationModuleCount.intValue() > 0)
            throw new DataDuplicationException(ApplicationModule.class, NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private void save(ApplicationModule applicationModule) {
        applicationModuleRepository.save(applicationModule);
    }

    private ApplicationModule findApplicationModuleById(Long applicationModuleId) {
        return applicationModuleRepository.findApplicationModuleById(applicationModuleId)
                .orElseThrow(() -> APPLICATION_MODULE_WITH_GIVEN_ID_NOT_FOUND.apply(applicationModuleId));
    }

    private Function<Long, NoContentFoundException> APPLICATION_MODULE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(ApplicationModule.class, "id", id.toString());
    };


}
