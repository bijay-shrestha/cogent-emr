package com.cogent.admin.repository;

import com.cogent.admin.dto.response.doctor.DoctorDetailResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorDropdownDTO;
import com.cogent.admin.dto.response.doctor.DoctorMinimalResponseDTO;
import com.cogent.admin.dto.response.doctor.DoctorUpdateResponseDTO;
import org.hibernate.transform.Transformers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Query;
import java.util.List;

import static com.cogent.admin.constants.QueryConstants.ID;
import static com.cogent.admin.query.DoctorQuery.*;
import static com.cogent.admin.utils.QueryUtil.getQuery;
import static com.cogent.admin.utils.QueryUtils.transformQueryToResultList;
import static com.cogent.admin.utils.QueryUtils.transformQueryToSingleResult;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author smriti on 2019-09-30
 */
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class DoctorRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void fetchActiveDoctorById() {
        String sql = "SELECT c FROM Doctor c WHERE c.status='Y' AND c.id = :id";

        Object query = getQuery.apply(testEntityManager, sql)
                .setParameter(ID, 1L).getSingleResult();

        assertNotNull(query);
    }

    @Test
    public void searchDoctor() {

        String sql = " SELECT c.id as doctorId, " +
                "c.name as doctorName, " +
                "c.mobile_number as mobileNumber, " +
                "c.status as status, " +
                "ca.file_uri as fileUri, " +
                "ca.is_default_image as isDefaultImage," +
                " tbl1.specialization_name as specializationName, " +
                "tbl2.department_name as departmentName " +
                "FROM Doctor c " +
                "LEFT JOIN doctor_avatar ca ON c.id = ca.doctor_id " +
                "LEFT JOIN " +
                "( SELECT " +
                "cs.doctor_id as doctor_id, " +
                "GROUP_CONCAT(cs.id) as doctor_specialization_id, " +
                "GROUP_CONCAT(s.name) as specialization_name, " +
                "GROUP_CONCAT(s.id) as specialization_id " +
                "FROM doctor_specialization cs " +
                "LEFT JOIN specialization s ON s.id = cs.specialization_id " +
                "WHERE " +
                "s.status = 'Y' " +
                "AND cs.status ='Y' " +
                "AND s.id IN (0) GROUP BY cs.doctor_id )tbl1 ON tbl1.doctor_id = c.id " +
                "LEFT JOIN " +
                "( " +
                "SELECT cd.doctor_id as doctor_id, " +
                "GROUP_CONCAT(cd.id) as doctor_department_id, " +
                "GROUP_CONCAT(d.name) as department_name, " +
                "GROUP_CONCAT(d.id) as department_id " +
                "FROM doctor_department cd " +
                "LEFT JOIN department d ON d.id = cd.department_id " +
                "WHERE d.status = 'Y' AND cd.status ='Y' " +
                "GROUP BY cd.doctor_id )tbl2 ON tbl2.doctor_id = c.id " +
                "WHERE " +
                "c.status!='D' " +
                "ORDER BY c.id DESC";

        List query = testEntityManager.getEntityManager().createNativeQuery(sql)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(Transformers.aliasToBean(DoctorMinimalResponseDTO.class))
                .getResultList();

        assertNotNull(query);
    }

    @Test
    public void fetchDoctorForDropDown() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_DOCTOR_FOR_DROPDOWN);

        assertFalse(query.getResultList().isEmpty());
    }

    @Test
    public void fetchDoctorDetails() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_DOCTOR_DETAILS)
                .setParameter(ID, 1L);

        Object result = transformQueryToSingleResult(query, DoctorDetailResponseDTO.class);
        assertNotNull(result);
    }

    @Test
    public void fetchDoctorDetailsForUpdate() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_DOCTOR_DETAILS_FOR_UPDATE)
                .setParameter(ID, 1L);

        Object result = transformQueryToSingleResult(query, DoctorUpdateResponseDTO.class);
        assertNotNull(result);
    }

    @Test
    public void fetchDoctorBySpecializationId() {
        Query query = getQuery.apply(testEntityManager, QUERY_TO_FETCH_DOCTOR_BY_SPECIALIZATION_ID)
                .setParameter(ID, 1L);

        List result = transformQueryToResultList(query, DoctorDropdownDTO.class);
        assertNotNull(result);
    }
}
