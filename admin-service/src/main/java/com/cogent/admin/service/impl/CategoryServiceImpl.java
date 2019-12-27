package com.cogent.admin.service.impl;

import com.cogent.admin.dto.commons.DeleteRequestDTO;
import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.category.CategoryRequestDTO;
import com.cogent.admin.dto.request.category.CategorySearchRequestDTO;
import com.cogent.admin.dto.request.category.CategoryUpdateRequestDTO;
import com.cogent.admin.dto.response.category.CategoryMinimalResponseDTO;
import com.cogent.admin.dto.response.category.CategoryResponseDTO;
import com.cogent.admin.exception.DataDuplicationException;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.CategoryRepository;
import com.cogent.admin.service.CategoryService;
import com.cogent.persistence.model.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_DEBUG_MESSAGE;
import static com.cogent.admin.constants.ErrorMessageConstants.NAME_DUPLICATION_MESSAGE;
import static com.cogent.admin.log.CommonLogConstant.*;
import static com.cogent.admin.log.constants.CategoryLog.CATEGORY;
import static com.cogent.admin.utils.CategoryUtils.*;
import static com.cogent.admin.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.admin.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.admin.utils.NameAndCodeValidationUtils.validatetDuplicityByNameOrCode;

/**
 * @author Sauravi Thapa 10/24/19
 */

@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void createCategory(CategoryRequestDTO requestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, CATEGORY);

        validatetDuplicityByNameOrCode(categoryRepository.fetchCategoryByNameOrCode(
                requestDTO.getName(), requestDTO.getCode()),
                requestDTO.getName(), requestDTO.getCode(), Category.class);

        save(convertToCategoryInfo(requestDTO));

        log.info(SAVING_PROCESS_COMPLETED, CATEGORY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void deleteCategory(DeleteRequestDTO deleteRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(DELETING_PROCESS_STARTED, CATEGORY);

        save(delete.apply(fetchCategoryById(deleteRequestDTO.getId()), deleteRequestDTO));

        log.info(DELETING_PROCESS_COMPLETED, CATEGORY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public void updateCategory(CategoryUpdateRequestDTO updateRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(UPDATING_PROCESS_STARTED, CATEGORY);

        Category toBeUpdatedcategory = fetchCategoryById(updateRequestDTO.getId());

        validatetDuplicityByNameOrCode(categoryRepository.checkCategoryNameAndCodeIfExist(
                updateRequestDTO.getId(),updateRequestDTO.getName(), updateRequestDTO.getCode()),
                updateRequestDTO.getName(), updateRequestDTO.getCode(), Category.class);

        save(update(toBeUpdatedcategory, updateRequestDTO));

        log.info(UPDATING_PROCESS_COMPLETED, CATEGORY, getDifferenceBetweenTwoTime(startTime));
    }

    @Override
    public CategoryResponseDTO fetchCategoryDetails(Long id) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_DETAIL_PROCESS_STARTED, CATEGORY);

        CategoryResponseDTO responseDTO = categoryRepository.fetchCategoryDetails(id);

        log.info(FETCHING_DETAIL_PROCESS_COMPLETED, CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return responseDTO;
    }

    @Override
    public List<DropDownResponseDTO> fetchActiveDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, CATEGORY);


        List<DropDownResponseDTO> dropDownResponseDTOS = categoryRepository.activeDropDownList()
                .orElseThrow(() -> new NoContentFoundException(Category.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<DropDownResponseDTO> fetchDropDownList() {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_PROCESS_STARTED_FOR_ACTIVE_DROPDOWN, CATEGORY);


        List<DropDownResponseDTO> dropDownResponseDTOS = categoryRepository.dropDownList()
                .orElseThrow(() -> new NoContentFoundException(Category.class));

        log.info(FETCHING_PROCESS_FOR_ACTIVE_DROPDOWN_COMPLETED, CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return dropDownResponseDTOS;
    }

    @Override
    public List<CategoryMinimalResponseDTO> searchCategory(CategorySearchRequestDTO searchRequestDTO, Pageable pageable) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_MINIMAL_PROCESS_STARTED, CATEGORY);


        List<CategoryMinimalResponseDTO> minimalResponseDTOS = categoryRepository
                .searchCategory(searchRequestDTO, pageable);

        log.info(FETCHING_MINIMAL_PROCESS_COMPLETED, CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return minimalResponseDTOS;
    }

    @Override
    public Category fetchActiveCategoryEntity(Long id) {

        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(FETCHING_ENTITY_PROCESS_STARTED, CATEGORY);

        Category category=categoryRepository.fetchActiveCategoryEntityById(id);

        log.info(FETCHING_ENTITY_PROCESS_COMPLETED, CATEGORY, getDifferenceBetweenTwoTime(startTime));

        return category;
    }

    public Category fetchCategoryById(Long id) {
        return categoryRepository.fetchCategoryById(id).orElseThrow(() ->
                new NoContentFoundException(Category.class, "id", id.toString()));
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

}
