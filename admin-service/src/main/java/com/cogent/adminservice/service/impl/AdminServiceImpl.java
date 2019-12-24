package com.cogent.adminservice.service.impl;

import com.cogent.adminservice.dto.ProfileMenuResponseDTO;
import com.cogent.adminservice.repository.AdminRepository;
import com.cogent.adminservice.service.AdminService;
import com.cogent.persistence.model.ProfileMenu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static com.cogent.adminservice.utils.QueryUtils.getQuery;
import static com.cogent.adminservice.utils.QueryUtils.transformQueryToResultList;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @PersistenceContext
    private EntityManager entityManager;

    private AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<ProfileMenu> getAdminByUsername(String t) {

        String sql = "SELECT pm.parentId as parentId, " +
                " pm.userMenuId as userMenuId," +
                " pm.roleId as roleId" +
                " FROM Profile p" +
                " LEFT JOIN Admin a ON a.profile = p.id" +
                " LEFT JOIN ProfileMenu pm ON pm.profile = p.id" +
                " WHERE a.username=:username";

        Query query = getQuery.apply(entityManager, sql)
                .setParameter("username", t);

        List results = transformQueryToResultList(query, ProfileMenuResponseDTO.class);

        System.out.println(results);
        return results;

    }
}
