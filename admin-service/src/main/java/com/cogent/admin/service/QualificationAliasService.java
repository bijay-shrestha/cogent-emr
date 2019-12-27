package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.persistence.model.QualificationAlias;

import java.util.List;

/**
 * @author smriti on 11/11/2019
 */
public interface QualificationAliasService {

    List<DropDownResponseDTO> fetchActiveQualificationAlias();

    QualificationAlias fetchQualificationAliasById(Long id);
}
