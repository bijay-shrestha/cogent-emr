package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankUpdateRequestDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankMinimalResponseDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.RegisteredBankRepository;
import com.cogent.admin.service.RegisteredBankService;
import com.cogent.persistence.model.RegisteredBank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.RegisteredBankMessages.REGISTERED_BANK_SWIFT_CODE_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.RegisteredBankMessages.REGISTERED_BANK_SWIFT_CODE_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.CommonLogConstant.DELETING_PROCESS_COMPLETED;
import static com.cogent.admin.log.constants.RegisteredBankLog.REGISTERED_BANK;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;
import static com.cogent.admin.utils.RegisteredBankUtils.*;

/**
 * @author Sauravi Thapa 12/10/19
 */

@Service
@Transactional
@Slf4j
public class RegisteredBankServiceImpl implements RegisteredBankService {

    private final RegisteredBankRepository repository;

    public RegisteredBankServiceImpl(RegisteredBankRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createRegisteredBank(RegisteredBankRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, REGISTERED_BANK);

        validatetDuplicityByNameOrCode(repository.findRegisteredBankByNameOrCode
                        (requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(), requestDTO.getCode(), RegisteredBank.class);

        validateSwiftCode(requestDTO.getSwiftCode());

        save(parseToRegisteredBank(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, REGISTERED_BANK, getDifferenceBetweenTwoTime(startTime));

    }

    @Override
    public void deleteRegisteredBank(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, REGISTERED_BANK);

        deleteRegisteredBank.apply(fetchRegisteredBankById(deleteRequestDTO.getId()), deleteRequestDTO);

        log.info(DELETING_PROCESS_COMPLETED, REGISTERED_BANK, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void updateRegisteredBank(RegisteredBankUpdateRequestDTO updateRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, REGISTERED_BANK);

        RegisteredBank registeredBankToBeUpdated = fetchRegisteredBankById(updateRequestDTO.getId());

        validatetDuplicityByNameOrCode(repository.checkIfRegisteredBankNameAndCodeExists(
                updateRequestDTO.getId(), updateRequestDTO.getName(), updateRequestDTO.getCode()),
                updateRequestDTO.getName(), updateRequestDTO.getCode(), RegisteredBank.class);

        validateSwiftCodeToUpdate(updateRequestDTO.getId(),updateRequestDTO.getSwiftCode());

        updateRegisteredBank.apply(registeredBankToBeUpdated, updateRequestDTO);

        log.info(UPDATING_PROCESS_COMPLETED, REGISTERED_BANK, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<RegisteredBankMinimalResponseDTO> searchRegisteredBank(RegisteredBankSearchRequestDTO searchRequestDTO,
                                                                       Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, REGISTERED_BANK);

        List<RegisteredBankMinimalResponseDTO> minimalResponseDTOS = repository.searchRegisteredBank(searchRequestDTO,
                pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, REGISTERED_BANK, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;
    }

    @Override
    public RegisteredBankResponseDTO fetchRegisteredBankDetails(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, REGISTERED_BANK);

        RegisteredBankResponseDTO responseDTO = repository.fetchRegisteredBankDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, REGISTERED_BANK, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, REGISTERED_BANK);


        List<DropDownResponseDTO> dropDownResponseDTOS = repository.activeDropDownList()
                .orElseThrow(() -> new NoContentFoundException(RegisteredBank.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, REGISTERED_BANK, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, REGISTERED_BANK);


        List<DropDownResponseDTO> dropDownResponseDTOS = repository.dropDownList()
                .orElseThrow(() -> new NoContentFoundException(RegisteredBank.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, REGISTERED_BANK, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    public RegisteredBank fetchRegisteredBankById(Long id) {
        return repository.fetchRegisteredBankById(id).orElseThrow(() ->
                new NoContentFoundException(RegisteredBank.class, "id", id.toString()));
    }

    public void validateSwiftCode( String swiftCode) {
        if (repository.checkIfSwiftCodeExists(swiftCode) != 0L)
            throw new DataDuplicationException(RegisteredBank.class, REGISTERED_BANK_SWIFT_CODE_DUPLICATION_MESSAGE
                    + swiftCode,
                    REGISTERED_BANK_SWIFT_CODE_DUPLICATION_DEBUG_MESSAGE + swiftCode);
    }

    public void validateSwiftCodeToUpdate( Long id,String swiftCode) {
        if (repository.checkIfSwiftCodeExistsToUpdate(id,swiftCode) != 0L)
            throw new DataDuplicationException(RegisteredBank.class, REGISTERED_BANK_SWIFT_CODE_DUPLICATION_MESSAGE
                    + swiftCode,
                    REGISTERED_BANK_SWIFT_CODE_DUPLICATION_DEBUG_MESSAGE + swiftCode);
    }

    public void save(RegisteredBank registeredBank) {
        repository.save(registeredBank);
    }
}
