package com.cogent.admin.constants;

/**
 * @author smriti on 7/5/19
 */
public class WebResourceKeyConstants {

    //A
    public static final String API_V1 = "/api/v1";
    public static final String ACTIVE = "/active";

    public interface AdminCategoryConstants {
        String BASE_ADMIN_CATEGORY = "/admin-category";
    }

    public interface AdminConstants {
        String BASE_ADMIN = "/admin";
        String AVATAR = "/avatar";
        String ADMIN_META_INFO = "/metaInfo";
        String ASSIGNED_SUB_DEPARTMENTS = "/assignedSubDepartments";
        String CHANGE_PASSWORD = "/changePassword";
        String RESET_PASSWORD = "/resetPassword";
        String INFO = "/info";
        String VERIFY = "/verify";
    }

    public interface AppointmentModeConstants {
        String BASE_APPOINTMENT_MODE = "/appointmentMode";
    }

    public interface AppointmentTypeConstants {
        String BASE_APPOINTMENT_TYPE = "/appointmentType";
    }

    public interface ApplicationModuleConstants {
        String BASE_APPLICATION_MODULE = "/applicationModule";
    }

    public interface AssignBedConstants {
        String BASE_ASSIGN_BED = "/assignbeds";
    }

    //B
    public static final  String BASE_PASSWORD = "/password";

    public interface BedConstants {
        String BASE_BED = "/beds";
    }

    public static final String BILLING_MODE_ID_PATH_VARIABLE_BASE = "/{billingModeId}";

    public static final String BILLING_MODE = "/{billingMode}";

    public interface BillingModeConstants {
        String BASE_BILLING_MODE = "/billingModes";
    }

    public interface BillTypeConstants {
        String BASE_BILL_TYPE = "/billType";
    }

    //C
    public interface CategoryConstants {
        String BASE_CATEGORY = "/categories";
    }

    public interface CountryConstants {
        String BASE_COUNTRY = "/country";
    }

    //D
    public static final String DEPARTMENT_ID_PATH_VARIABLE_BASE = "/{departmentId}";

    public static final String DROPDOWN = "/dropdown";

    public static final String DETAILS = "/details";

    public interface DepartmentConstants {
        String BASE_DEPARTMENT = "/department";
    }

    public interface DiscountSchemeConstants {
        String BASE_DISCOUNT_SCHEME = "/discountSchemes";
        String DEPARTMENT_DISCOUNT = "/departmentDiscount";
        String SERVICE_DISCOUNT = "/serviceDiscountRate";
    }

    public interface DistrictConstants {
        String BASE_DISTRICT = "/districts";
    }

    public interface DrugConstants {
        String BASE_DRUG = "/drugs";
    }

    public interface DoctorConstants {
        String BASE_DOCTOR = "/doctor";
        String UPDATE_DETAILS = "/updateDetails";
        String DOCTOR_ID_PATH_VARIABLE_BASE = "/{doctorId}";
    }

    public interface DoctorTypeConstants {
        String BASE_DOCTOR_TYPE = "/doctorType";
    }

    public interface DoctorCategoryConstants {
        String BASE_DOCTOR_CATEGORY = "/doctor-categories";
    }

    public interface DoctorDutyRosterConstants {
        String BASE_DOCTOR_DUTY_ROSTER = "/doctorDutyRoster";
        String CHECK_AVAILABILITY = "/checkAvailability";
        String DOCTOR_DUTY_ROSTER_OVERRIDE = "/doctorDutyRosterOverride";
        String DOCTOR_DUTY_ROSTER_STATUS = "/doctorDutyRosterStatus";
    }

    //E
    public interface EthnicityConstants {
        String BASE_ETHNICITY = "/ethnicities";
    }

    public static final String EXCEL = "/excel";

    //F
    public static final String SERVICE_ID_PATH_VARIABLE_BASE = "/{serviceId}";

    public static final String FILE = "/files/{subDirectoryLocation}/{filename:.+}";

    public interface ServiceConstants {
        String BASE_SERVICE = "/services";
    }

    public interface ServiceChargeConstants {
        String BASE_SERVICE_CHARGE = "/service-charges";
    }

    public interface FollowUpSetupConstants {
        String BASE_FOLLOW_UP_SETUP = "/followUpSetup";
    }

    public interface ForgotPasswordConstants {
        String VERIFY = "/verify";
        String FORGOT = "/forgot";
    }

    //G
    public interface GenderConstants {
        String BASE_GENDER = "/gender";
    }

    //H
    public interface HospitalConstants {
        String BASE_HOSPITAL = "/hospital";
    }

    //I
    public static final String ID_PATH_VARIABLE_BASE = "/{id}";

    public interface InsuranceCompanyConstants {
        String BASE_INSURANCE_COMPANY = "/insurance-companies";
    }


    //J

    //K

    //L

    //M
    public interface MunicipalityConstants {
        String BASE_MUNICIPALITY = "/municipalities";
    }

    public interface MaritalStatusConstants {
        String BASE_MARITAL_STATUS = "/marital-status";
    }

    //N
    public interface NationalityConstants {
        String BASE_NATIONALITY = "/nationalities";
    }


    //O

    //P
    public interface PaymentTypeConstants {
        String BASE_PAYMENT_TYPE = "/paymentTypes";
    }

    public interface PatientTypeConstants {
        String BASE_PATIENT_TYPE = "/patientType";
    }

    public interface ProfileSetupConstants {
        String BASE_PROFILE = "/profiles";
    }

    public interface PatientConstants {
        String BASE_PATIENT = "/patients";
    }

    public interface ProvincesConstants {
        String BASE_PROVINCES = "/provinces";
    }

    //Q
    public interface QualificationConstants {
        String BASE_QUALIFICATION = "/qualification";
    }

    public interface QualificationAliasConstants {
        String BASE_QUALIFICATION_ALIAS = "/qualificationAlias";
    }

    //R
    public interface RegisterdBankConstants {
        String BASE_REGISTERED_BANK = "/registeredBanks";
    }

    public interface ReligionConstants {
        String BASE_RELIGION = "/religions";
    }

    public interface ReferrerConstants {
        String BASE_REFERRER = "/referrer";
    }

    //S
    public static final String SAVE = "/save";
    public static final String SEARCH = "/search";

    public interface SidebarConstants {
        String BASE_SIDE_BAR = "/sidebar";
    }

    public interface SpecializationConstants {
        String BASE_SPECIALIZATION = "/specialization";
        String SPECIALIZATION_ID_PATH_VARIABLE_BASE = "/{specializationId}";
    }

    public interface SubDepartmentConstants {
        String BASE_SUB_DEPARTMENT = "/sub-departments";
        String SUB_DEPARTMENT_ID_PATH_VARIABLE_BASE = "/{subDepartmentId}";
    }

    public interface SurnameConstants {
        String BASE_SURNAME = "/surnames";
    }

    //T
    public interface TitleConstants {
        String BASE_TITLE = "/title";
    }

    //U
    public static final String USERNAME_VARIABLE_BASE = "/{username}";

    public interface UnitConstants {
        String BASE_UNIT = "/units";
    }

    //V

    //W
    public interface WardConstants {
        String BASE_WARD = "/wards";
        String WARD_ID_PATH_VARIABLE_BASE = "/{wardId}";
    }

    public interface WeekDaysConstants {
        String BASE_WEEK_DAYS = "/weekDays";
    }

    public interface WardUnitConstants {
        String UNIT = "/unit";
    }

    //X

    //Y

    //Z
}
