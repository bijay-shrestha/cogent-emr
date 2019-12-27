package com.cogent.admin.service;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.PaymentTypeRepository;
import com.cogent.admin.service.impl.PaymentTypeServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static com.cogent.admin.dto.paymentmode.PaymentTypeTestUtils.getPaymentTypeDropDownInfo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi Thapa 12/11/19
 */
public class PaymentTypeServiceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    PaymentTypeRepository repository;

    @InjectMocks
    PaymentTypeServiceImpl service;


    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void dropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.dropDownList())
                .willReturn(Optional.empty());

        service.fetchDropDownList();
    }

    @Test
    public void dropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getPaymentTypeDropDownInfo();

        given(repository.dropDownList())
                .willReturn(Optional.of(dropDownInfo));

        service.fetchDropDownList();

        verify(repository).dropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnNoContentFound() {
        thrown.expect(NoContentFoundException.class);

        given(repository.activeDropDownList())
                .willReturn(Optional.empty());

        service.fetchActiveDropDownList();
    }

    @Test
    public void activeDropDownList_ShouldReturnData() {
        List<DropDownResponseDTO> dropDownInfo = getPaymentTypeDropDownInfo();

        given(repository.activeDropDownList())
                .willReturn(Optional.of(dropDownInfo));

        service.fetchActiveDropDownList();

        verify(repository).activeDropDownList();
    }
}
