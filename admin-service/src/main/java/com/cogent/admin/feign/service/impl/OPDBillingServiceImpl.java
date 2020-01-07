package com.cogent.admin.feign.service.impl;

import com.cogent.admin.feign.dto.request.OPDBilling.InsuranceRequestDTO;
import com.cogent.admin.feign.dto.request.OPDBilling.OPDBillingServiceRequestDTO;
import com.cogent.admin.feign.dto.response.OPDBilling.InsuranceResponseDTO;
import com.cogent.admin.feign.dto.response.OPDBilling.OPDBillingServiceResponseDTO;
import com.cogent.admin.feign.service.OPDBillingService;
import com.cogent.admin.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cogent.admin.feign.utils.OPDBillingUtils.parseToInsuranceResponseDTO;
import static com.cogent.admin.feign.utils.OPDBillingUtils.parseToOPDBillingResponseDTO;

/**
 * @author Sauravi Thapa 12/13/19
 */

@Slf4j
@Service
@Transactional
public class OPDBillingServiceImpl implements OPDBillingService {

    private final BillingModeRepository billingModeRepository;

    private final DoctorRepository doctorRepository;

    private final ReferrerRepository referrerRepository;

    private final DepartmentRepository departmentRepository;

    private final HospitalRepository hospitalRepository;

    private final DiscountSchemeRepository discountSchemeRepository;

    private final SpecializationRepository specializationRepository;

    private final InsuranceCompanyRepository insuranceCompanyRepository;

    private final PaymentTypeRepository paymentTypeRepository;

    public OPDBillingServiceImpl(BillingModeRepository billingModeRepository,
                                 DoctorRepository doctorRepository,
                                 ReferrerRepository referrerRepository,
                                 DepartmentRepository departmentRepository,
                                 HospitalRepository hospitalRepository,
                                 DiscountSchemeRepository discountSchemeRepository,
                                 SpecializationRepository specializationRepository,
                                 InsuranceCompanyRepository insuranceCompanyRepository,
                                 PaymentTypeRepository paymentTypeRepository) {
        this.billingModeRepository = billingModeRepository;
        this.doctorRepository = doctorRepository;
        this.referrerRepository = referrerRepository;
        this.departmentRepository = departmentRepository;
        this.hospitalRepository = hospitalRepository;
        this.discountSchemeRepository = discountSchemeRepository;
        this.specializationRepository = specializationRepository;
        this.insuranceCompanyRepository = insuranceCompanyRepository;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    @Override
    public OPDBillingServiceResponseDTO getDetailsForBilling(OPDBillingServiceRequestDTO serviceRequestDTO) {
        OPDBillingServiceResponseDTO responseDTO = parseToOPDBillingResponseDTO(
                billingModeRepository.fetchActiveBillingModeById(serviceRequestDTO.getBillingModeId()),
                referrerRepository.fetchActiveReferrerById(serviceRequestDTO.getReferrerId()),
                departmentRepository.fetchActiveDepartmentById(serviceRequestDTO.getDepartmentId()),
                hospitalRepository.fetchActiveHospitalById(serviceRequestDTO.getHospitalId()),
                discountSchemeRepository.fetchActiveDiscountSchemeById(serviceRequestDTO.getDiscountSchemeId()),
                specializationRepository.fetchActiveSpecializationById(serviceRequestDTO.getSpecializationId()),
                paymentTypeRepository.fetchActivepaymentTypeById(serviceRequestDTO.getPaymentTypeId()));

        return responseDTO;

    }

    @Override
    public InsuranceResponseDTO getInsuranceDetails(InsuranceRequestDTO requestDTO) {
        InsuranceResponseDTO responseDTO = parseToInsuranceResponseDTO(
                insuranceCompanyRepository.findActiveInsuranceCompanyById(requestDTO.getInsuraceCompanyId()));
        return responseDTO;
    }
}
