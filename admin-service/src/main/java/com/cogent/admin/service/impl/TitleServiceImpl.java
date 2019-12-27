package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.request.title.TitleRequestDTO;
import com.cogent.admin.dto.request.title.TitleSearchRequestDTO;
import com.cogent.admin.dto.request.title.TitleUpdateRequestDTO;
import com.cogent.admin.dto.response.title.TitleDropdownDTO;
import com.cogent.admin.dto.response.title.TitleResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.TitleRepository;
import com.cogent.admin.service.TitleService;
import com.cogent.persistence.model.Title;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Function;

import static com.cogent.admin.constants.ErrorMessageConstants.TitleServiceMessages.TITLE_NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.TitleServiceMessages.TITLE_NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.TitleLog.TITLE;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.TitleUtils.*;

@Service
@Transactional
@Slf4j
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;

    public TitleServiceImpl(TitleRepository titleRepository) {
        this.titleRepository = titleRepository;
    }


    @Override
    public void save(TitleRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, TITLE);

        validateName(titleRepository.fetchTitleByName(requestDTO.getName()),
                requestDTO.getName());

        save(convertDTOToTitle(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, TITLE, getDifferenceBetweenTwoTime(startTime));


    }

    private void save(Title title) {
        titleRepository.save(title);
    }

    private void validateName(Long tittleCount, String name) {
        if (tittleCount.intValue() > 0)
            throw new DataDuplicationException(
                    Title.class, TITLE_NAME_DUPLICATION_MESSAGE + name, TITLE_NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    @Override
    public void update(TitleUpdateRequestDTO requestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, TITLE);

        Title title = findById(requestDTO.getId());

        System.out.println(title.getId());

        validateName(titleRepository.findTitleByIdAndName(requestDTO.getId(), requestDTO.getName()),
                requestDTO.getName());

        save(convertToUpdatedTitle(requestDTO, title));

        log.info(UPDATING_PROCESS_COMPLETED, TITLE, getDifferenceBetweenTwoTime(startTime));
    }

    private Title findById(Long id) {
        return titleRepository.findTitleById(id).orElseThrow(() -> TITTLE_WITH_GIVEN_ID_NOT_FOUND.apply(id));
    }

    private Function<Long, NoContentFoundException> TITTLE_WITH_GIVEN_ID_NOT_FOUND = (id) -> {
        throw new NoContentFoundException(Title.class, "id", id.toString());
    };

    @Override
    public void delete(DeleteRequestDTO deleteRequestDTO) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, TITLE);

        Title title = findById(deleteRequestDTO.getId());

        save(convertToDeletedTitle(title, deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, TITLE, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public List<TitleResponseDTO> search(TitleSearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SEARCHING_PROCESS_STARTED, TITLE);

        List<TitleResponseDTO> responseDTOS = titleRepository.search(searchRequestDTO, pageable);

        log.info(SEARCHING_PROCESS_COMPLETED, TITLE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public List<TitleDropdownDTO> fetchTitleForDropDown() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_DROPDOWN, TITLE);

        List<TitleDropdownDTO> responseDTOS = titleRepository.fetchTitleForDropDown();

        log.info(FETCHING_PROCESS_FOR_DROPDOWN_COMPLETED, TITLE, getDifferenceBetweenTwoTime(startTime));

        return responseDTOS;
    }

    @Override
    public Title fetchTitle(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_ENTITY_PROCESS_STARTED, TITLE);

        Title title = titleRepository.findActiveTitleById(id).orElseThrow(() ->
                new NoContentFoundException(Title.class));

        log.info(FETCHING_ENTITY_PROCESS_COMPLETED, TITLE, getDifferenceBetweenTwoTime(startTime));

        return title;
    }


}
