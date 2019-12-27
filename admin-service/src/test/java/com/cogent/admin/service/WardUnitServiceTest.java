package com.cogent.admin.service;

import com.cogent.admin.repository.WardUnitRepository;
import com.cogent.admin.service.impl.WardUnitServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

/**
 * @author Sauravi Thapa 10/21/19
 */
public class WardUnitServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    WardUnitRepository repository;

    @InjectMocks
    WardUnitServiceImpl service;

    @Before
    public void createMock() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void delete_ShoulReturnNoContentFound() {
//        thrown.expect(NoContentFoundException.class);
//
//        given(repository.fetchWardUnitByUnitAndWardId(1L, 1L)).willReturn(Optional.empty());
//
//        service.delete(getWardUnitDeleteRequestDTO());
//    }
//
//    @Test
//    public void delete_ShoulDelete() {
//
//        given(repository.fetchWardUnitByUnitAndWardId(1L, 1L)).willReturn(Optional.of(getWardUnitInfo()));
//
//        Ward_Unit wardUnit = deleteWardUnit.apply(service.fetchWardUnitByUnitAndWardId(1L, 1L), getWardUnitDeleteRequestDTO());
//
//        service.delete(getWardUnitDeleteRequestDTO());
//
//        assertNotNull(wardUnit);
//
//        verify(repository).fetchWardUnitByUnitAndWardId(1L,1L);
//    }


}
