package com.cogent.admin.service;

import com.cogent.admin.exception.NoContentFoundException;
import com.cogent.admin.repository.BillTypeRepository;
import com.cogent.admin.service.impl.BillTypeServiceImpl;
import com.cogent.persistence.model.BillType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.cogent.admin.dto.billType.BillTypeResponseUtils.fetchActiveBillTypes;
import static com.cogent.admin.dto.billType.BillTypeResponseUtils.getBillType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.BDDMockito.given;

/**
 * @author smriti on 2019-10-22
 */
public class BillTypeServiceTest {
    @InjectMocks
    private BillTypeServiceImpl billTypeService;

    @Mock
    private BillTypeRepository billTypeRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void fetchActiveBillType_ShouldThrowException() {

        given(billTypeRepository.fetchActiveBillTypes()).willThrow(NoContentFoundException.class);

        thrown.expect(NoContentFoundException.class);

        billTypeService.fetchActiveBillType();
    }

    @Test
    public void Should_Successfully_Return_ActiveBillType() {
        given(billTypeRepository.fetchActiveBillTypes()).willReturn(fetchActiveBillTypes());

        billTypeService.fetchActiveBillType();

        assertThat(billTypeService.fetchActiveBillType(), hasSize(fetchActiveBillTypes().size()));

        assertThat(billTypeService.fetchActiveBillType(), samePropertyValuesAs(fetchActiveBillTypes()));
    }

    @Test(expected = NoContentFoundException.class)
    public void fetchBillTypeById_ShouldThrowException() {
        Long id = 1L;

        given(billTypeRepository.fetchActiveBillTypeById(id))
                .willThrow(NoContentFoundException.class);

        billTypeService.fetchBillTypeById(id);
    }

    @Test
    public void Should_Return_ActiveBillTypeById() {
        Long id = 1L;
        BillType billType = getBillType();

        given(billTypeRepository.fetchActiveBillTypeById(id))
                .willReturn(Optional.of(billType));

        Assert.assertThat(billTypeService.fetchBillTypeById(id), samePropertyValuesAs(getBillType()));
    }

}
