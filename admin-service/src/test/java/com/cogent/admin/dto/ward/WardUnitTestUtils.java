package com.cogent.admin.dto.ward;

import com.cogent.admin.dto.request.ward.WardUnitDeleteRequestDTO;
import com.cogent.admin.dto.unit.UnitTestUtils;
import com.cogent.persistence.model.Ward_Unit;

/**
 * @author Sauravi Thapa 10/21/19
 */
public class WardUnitTestUtils {
    public static WardUnitDeleteRequestDTO getWardUnitDeleteRequestDTO(){
        return WardUnitDeleteRequestDTO.builder()
                .wardId(1L)
                .unitID(1L)
                .status('D')
                .remarks("delete")
                .build();
    }

    public static Ward_Unit getWardUnitInfo(){
        Ward_Unit wardUnit=new Ward_Unit();

        wardUnit.setUnit(UnitTestUtils.getUnitInfo());
        wardUnit.setId(1L);
        wardUnit.setWard(WardTestUtils.getWardInfo());
        wardUnit.setStatus('Y');

        return wardUnit;
    }
}
