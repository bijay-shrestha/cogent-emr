package com.cogent.admin.customRepository;

import com.cogent.admin.repository.WardUnitRepository;
import com.cogent.persistence.model.Ward_Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * @author Sauravi Thapa 11/10/19
 */

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class WardUnitRepositoryCustomTest {

    @Autowired
    WardUnitRepository wardUnitRepository;


    @Test
    public void fetchWardUnitByWardId(){
        List<Ward_Unit> wardUnits=wardUnitRepository.fetchWardUnitByWardId(1L);

        assertNotNull(wardUnits);
    }

    @Test
    public void fetchWardUnitByUnitAndWardId(){
        Ward_Unit wardUnit=wardUnitRepository.fetchWardUnitByUnitAndWardId(1L,1L);

        assertNotNull(wardUnit);
    }


}
