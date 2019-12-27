package com.cogent.admin.repository.custom;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author smriti on 2019-10-22
 */
@Repository
@Qualifier("billTypeRepositoryCustom")
public interface BillTypeRepositoryCustom {

    List<DropDownResponseDTO> fetchActiveBillTypes();
}
