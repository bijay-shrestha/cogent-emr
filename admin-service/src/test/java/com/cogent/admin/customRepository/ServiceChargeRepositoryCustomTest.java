package com.cogent.admin.customRepository;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.servicecharge.ServiceChargeSearchRequestDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeDropDownResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeMinimalResponseDTO;
import com.cogent.admin.dto.response.servicecharge.ServiceChargeResponseDTO;
import com.cogent.admin.repository.ServiceChargeRepository;
import com.cogent.persistence.model.ServiceCharge;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * @author Sauravi Thapa 11/18/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class ServiceChargeRepositoryCustomTest {

    @Autowired
    ServiceChargeRepository repository;


    @Test
    public void fetchServiceChargeCountByServiceIdAndBillingModeId() {
        BigInteger count = repository.fetchServiceChargeCountByServiceIdAndBillingModeId(0L, 0L);

        assertTrue(count == BigInteger.valueOf(0));
    }

    @Test
    public void CheckIfServiceExists(){
        BigInteger count = repository.CheckIfServiceExists(0L, 0L,0L);

        assertTrue(count == BigInteger.valueOf(0));
    }

    @Test
    public void searchServiceCharge() {

        Pageable pageable = PageRequest.of(1, 10);

        ServiceChargeSearchRequestDTO searchRequestDTO = new ServiceChargeSearchRequestDTO();

        List<ServiceChargeMinimalResponseDTO> minimalResponseDTOS = repository
                .searchServiceCharge(searchRequestDTO, pageable);

        assertNotNull(minimalResponseDTOS);
    }

    @Test
    public void fetchServiceChargeDetail() {
        ServiceChargeResponseDTO responseDTO = repository
                .fetchServiceChargeDetailById(2L);

        assertNotNull(responseDTO);
    }

    @Test
    public void fetchDropDownList() {
        Optional<List<ServiceChargeDropDownResponseDTO>> dropDownList = repository
                .fetchDropDownList();

        assertNotNull(dropDownList);
    }

    @Test
    public void fetchActiveDropDownList() {
        Optional<List<ServiceChargeDropDownResponseDTO>> dropDownList = repository
                .fetchActiveDropDownList();

        assertNotNull(dropDownList);
    }

    @Test
    public void fetchDropDownListByDepartmentId() {
        Optional<List<DropDownResponseDTO>> dropDownList = repository
                .fetchDropDownListBydepartmentId(1L);

        assertNotNull(dropDownList);
    }

    @Test
    public void fetchActiveDropDownListByDepartmentId() {
        Optional<List<DropDownResponseDTO>> dropDownList = repository
                .fetchActiveDropDownListBydepartmentId(1L);

        assertNotNull(dropDownList);
    }
    @Test
    public void fetchLisByBillingModeIdAndServiceId() {
        List<ServiceCharge> serviceCharges = repository
                .fetchLisByBillingModeIdAndServiceId(3L,1L);

        assertNotNull(serviceCharges);
    }


}


