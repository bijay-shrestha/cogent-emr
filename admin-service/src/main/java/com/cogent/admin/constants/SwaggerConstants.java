package com.cogent.admin.constants;

public class SwaggerConstants {
    public static String BASE_PACKAGE = "com.cogent.admin.controller";

    public static String PATH_REGEX = "/api.*";

    //A
    public interface AdminCategoryConstant {
        String BASE_API_VALUE = "This is Admin Category Controller";
        String SAVE_OPERATION = "Save new admin category";
        String UPDATE_OPERATION = "Update existing admin category";
        String DELETE_OPERATION = "Set admin category status as 'D' when deleted";
        String SEARCH_OPERATION = "Search admin category according to given request parameters";
        String DETAILS_OPERATION = "Fetch admin category details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal admin category details (id and name) for dropdown";
        String DOWNLOAD_EXCEL_OPERATION = "Download excelsheet of admin category in byte array format";
    }

    public interface AdminConstant {
        String BASE_API_VALUE = "This is Admin Controller";
        String SAVE_OPERATION = "Save new admin";
        String UPDATE_OPERATION = "Update existing admin details";
        String DELETE_OPERATION = "Set admin status as 'D' with specific remarks";
        String SEARCH_OPERATION = "Search admin according to given request parameters";
        String DETAILS_OPERATION = "Fetch admin details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal admin details (id and name) for dropdown";
        String CHANGE_PASSWORD = "Validate the requested password with original and update it accordingly.";
        String UPDATE_AVATAR_OPERATION = "Update admin avatar and if the file size is empty " +
                "then change the isDefaultImage status as 'Y' else update accordingly.";
        String VERIFY_ADMIN = "Verify if the confirmation token is valid and admin has not been registered.";
        String SAVE_PASSWORD_OPERATION = "Save admin password";
    }

    public interface AppointmentConstant {
        String BASE_API_VALUE = "This is Appointment Controller.";
        String DETAILS_OPERATION = "Fetch required admin details to save an appointment.";
    }

    public interface AppointmentTypeConstant {
        String BASE_API_VALUE = "This is Appointment Type Controller";
        String SAVE_OPERATION = "Save new Appointment Type like Doctor, Department, Package, etc";
        String UPDATE_OPERATION = "Update existing Appointment Type";
        String DELETE_OPERATION = "Set Appointment Type status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Appointment Type according to given request parameters";
        String DETAILS_OPERATION = "Fetch Appointment Type details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal Appointment Type details (id and name) for dropdown";
    }

    public interface AppointmentModeConstant {
        String BASE_API_VALUE = "This is Appointment Mode Controller";
        String SAVE_OPERATION = "Save new Appointment Mode like Walk in, Phone, Online, Follow up etc";
        String UPDATE_OPERATION = "Update existing Appointment Mode";
        String DELETE_OPERATION = "Set Appointment Mode status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Appointment Mode according to given request parameters";
        String DETAILS_OPERATION = "Fetch Appointment Mode details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal Appointment Mode details (id and name) for dropdown";
    }

    public interface ApplicationModuleConstant {
        String BASE_API_VALUE = "This is Application Module Controller";
        String SAVE_OPERATION = "Save new Application Module";
        String UPDATE_OPERATION = "Update existing Application Module";
        String DELETE_OPERATION = "Set Application Module status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Application Module according to given request parameters";
        String DETAILS_OPERATION = "Fetch Application Module details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal Application Module details (id, name and sub-department id) for dropdown";
    }

    public interface AssignBedConstant {
        String BASE_ASSIGN_BED_API_VALUE = "This is Assign Bed Controller";
        String SAVE_ASSIGN_BED_OPERATION = "Save new Assign Bed";
        String UPDATE_ASSIGN_BED_OPERATION = "Update existing Assign Bed";
        String DELETE_ASSIGN_BED_OPERATION = "Set Assign Bed status as 'D' when deleted";
        String ASSIGN_BED_DETAIL_OPERATION = "Fetch Assign Bed details ";
        String SEARCH_ASSIGN_BED_OPERATION = "Search Assign Bed according to given request parameters";
        String FETCH_ASSIGN_BED_FOR_DROP_DOWN_OPERATION = "Fetch minimal Assign Bed details (id and name) for dropdown";
        String FETCH_ACTIVE_ASSIGN_BED_FOR_DROP_DOWN_OPERATION = "Fetch minimal active Assign Bed details (id and name) " +
                "for dropdown";
    }


    public interface BedConstant {
        String BASE_BED_API_VALUE = "This is Bed Controller";
        String SAVE_BED_OPERATION = "Save new Bed";
        String UPDATE_BED_OPERATION = "Update existing Bed";
        String DELETE_BED_OPERATION = "Set Bed status as 'D' when deleted";
        String BED_DETAIL_OPERATION = "Fetch Bed details ";
        String SEARCH_BED_OPERATION = "Search Bed according to given request parameters";
        String FETCH_BED_FOR_DROP_DOWN_OPERATION = "Fetch minimal Bed details (id and name) for dropdown";
        String FETCH_ACTIVE_BED_FOR_DROP_DOWN_OPERATION = "Fetch minimal active Bed details (id and name) " +
                "for dropdown";
    }

    public interface BillingModeConstant {
        String BASE_BILLING_MODE_API_VALUE = "This is Billing Mode Controller";
        String SAVE_BILLING_MODE_OPERATION = "Save new Billing Mode";
        String UPDATE_BILLING_MODE_OPERATION = "Update existing Billing Mode";
        String DELETE_BILLING_MODE_OPERATION = "Set Billing Mode status as 'D' when deleted";
        String BILLING_MODE_DETAIL_OPERATION = "Fetch Billing Mode details ";
        String SEARCH_BILLING_MODE_OPERATION = "Search Billing Mode according to given request parameters";
        String FETCH_BILLING_MODE_FOR_DROP_DOWN_OPERATION = "Fetch minimal Billing Mode details (id and name) " +
                "for dropdown";
        String FETCH_ACTIVE_BILLING_MODE_FOR_DROP_DOWN_OPERATION = "Fetch minimal active Billing Mode details" +
                " (id and name) for dropdown";
    }

    public interface BillTypeConstant {
        String BASE_API_VALUE = "This is Bill Type Controller";
        String FETCH_ACTIVE_BILL_TYPE = "Fetch active bill types like payable, non-payable";
    }

    //C
    public interface CategoryConstant {
        String BASE_API_VALUE = "This is Category setup Controller";
        String SAVE_CATEGORY_OPERATION = "Save new category";
        String UPDATE_CATEGORY_OPERATION = "Update existing category";
        String DELETE_CATEGORY_OPERATION = "Set category status as 'D' when deleted with remarks";
        String SEARCH_CATEGORY_OPERATION = "Search category according to given request parameters";
        String FETCH_FOR_CATEGORY_DROPDOWN = "Fetch minimal category details (id and name) for dropdown";
        String FETCH_FOR_CATEGORY_ACTIVE_DROPDOWN = "Fetch minimal category details (id and name) for active dropdown";
        String CATEGORY_DETAIL_OPERATION = "Fetch category details ";
        String CATEGORY_OPERATION = "Fetch category enitity ";
    }

    public interface CountryConstant {
        String BASE_API_VALUE = "This is Country Controller.";
        String FETCH_ACTIVE_COUNTRY = "Fetch active country.";
    }

    //D
    public interface DepartmentConstant {
        String BASE_DEPARTMENT_API_VALUE = "This is Department Controller";
        String SAVE_DEPARTMENT_OPERATION = "Save new department";
        String UPDATE_DEPARTMENT_OPERATION = "Update existing department";
        String DELETE_DEPARTMENT_OPERATION = "Set department(all sub-departments) status as 'D' when deleted";
        String SEARCH_DEPARTMENT_OPERATION = "Search department according to given request parameters";
        String DEPARTMENT_DETAILS_OPERATION = "Fetch department details";
        String FETCH_DEPARTMENT_FOR_DROP_DOWN_OPERATION = "Fetch minimal department details (id and name) for dropdown";
        String FETCH_ACTIVE_DEPARTMENT_FOR_DROP_DOWN_OPERATION = "Fetch minimal active department details (id and name)" +
                " for dropdown";
        String DOWNLOAD_DEPARTMENT_EXCEL_OPERATION = "Download excelsheet of department in byte array format";
    }

    public interface DiscountSchemeConstant {
        String BASE_DISCOUNT_SCHEME_API_VALUE = "This is Discount Scheme Controller";
        String SAVE_DISCOUNT_SCHEME_OPERATION = "Save new Discount Scheme";
        String UPDATE_DISCOUNT_SCHEME_OPERATION = "Update existing Discount Scheme";
        String DELETE_DISCOUNT_SCHEME_OPERATION = "Set Discount Scheme status as 'D' when deleted";
        String DELETE_DEPARTMENT_DISCOUNT_OPERATION = "Set Department Discount status as 'D' when deleted";
        String DELETE_SERVICE_DISCOUNT_OPERATION = "Set Service Discount status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Discount Scheme according to given request parameters";
        String DISCOUNT_SCHEME_DETAIL_OPERATION = "Fetch Discount Scheme details ";
        String FETCH_DISCOUNT_SCHEME_FOR_DROP_DOWN_OPERATION = "Fetch minimal Discount Scheme details (id and name)" +
                " for dropdown";
        String FETCH_ACTIVE_DISCOUNT_SCHEME_FOR_DROP_DOWN_OPERATION = "Fetch minimal active Discount Scheme details (" +
                "id and name) for dropdown";
        String FETCH_DISCOUNT_SCHEME_FOR_DROP_DOWN_BY_DEPARTMENT_ID_OPERATION = "Fetch minimal Discount Scheme details" +
                " (id and name) for dropdown by department id";
        String FETCH_ACTIVE_DISCOUNT_SCHEME_FOR_DROP_DOWN_BY_DEPARTMENT_ID_OPERATION = "Fetch minimal active Discount " +
                "Scheme details (id and name) for dropdown by department id";
        String FETCH_DISCOUNT_SCHEME_FOR_DROP_DOWN_BY_SERVICE_ID_OPERATION = "Fetch minimal Discount Scheme details" +
                " (id and name) for dropdown by service id";
        String FETCH_ACTIVE_DISCOUNT_SCHEME_FOR_DROP_DOWN_BY_SERVICE_ID_OPERATION = "Fetch minimal active Discount" +
                " Scheme details (id and name) for dropdown by service id";
    }

    public interface DistrictConstant {
        String BASE_DISTRICT_API_VALUE = "This is District Controller";
        String SAVE_DISTRICT_OPERATION = "Save new District";
        String UPDATE_DISTRICT_OPERATION = "Update existing District";
        String DELETE_DISTRICT_OPERATION = "Set District status as 'D' when deleted";
        String SEARCH_OPERATION = "Search District according to given request parameters";
        String DISTRICT_DETAIL_OPERATION = "Fetch District details ";
        String FETCH_DISTRICT_FOR_DROP_DOWN_OPERATION = "Fetch minimal District details (id and name) for dropdown";
        String FETCH_ACTIVE_DISTRICT_FOR_DROP_DOWN_OPERATION = "Fetch minimal active District details (id and name) " +
                "for dropdown";
    }

    public interface DoctorCategoryConstant {
        String BASE_DOCTOR_CATEGORY_API_VALUE = "This is Doctor Category Controller";
        String SAVE_DOCTOR_CATEGORY_OPERATION = "Save new Doctor Category";
        String UPDATE_DOCTOR_CATEGORY_OPERATION = "Update existing Doctor Category";
        String DELETE_DOCTOR_CATEGORY_OPERATION = "Set Doctor Category status as 'D' when deleted";
        String DOCTOR_CATEGORY_DETAIL_OPERATION = "Fetch Doctor Category details ";
        String SEARCH_DOCTOR_CATEGORY_OPERATION = "Search Doctor Category according to given request parameters";
        String FETCH_DOCTOR_CATEGORY_FOR_DROP_DOWN_OPERATION = "Fetch minimal Doctor Category details (id and name) for dropdown";
        String FETCH_ACTIVE_DOCTOR_CATEGORY_FOR_DROP_DOWN_OPERATION = "Fetch minimal active Doctor Category details (id and name) " +
                "for dropdown";
    }

    public interface DrugConstant {
        String BASE_DRUG_API_VALUE = "This is Drug Controller";
        String SAVE_DRUG_OPERATION = "Save new drug";
        String UPDATE_DRUG_OPERATION = "Update existing drug";
        String DELETE_DRUG_OPERATION = "Set drug status as 'D' when deleted";
        String SEARCH_OPERATION = "Search drug according to given request parameters";
        String DRUG_DETAIL_OPERATION = "Fetch drug details ";
        String FETCH_DRUG_FOR_DROP_DOWN_OPERATION = "Fetch minimal drug details (id and name) " +
                "for dropdown";
        String FETCH_ACTIVE_DRUG_FOR_DROP_DOWN_OPERATION = "Fetch minimal active drug details" +
                " (id and name) for dropdown";
    }

    public interface DoctorConstant {
        String BASE_API_VALUE = "This is Doctor Controller";
        String SAVE_OPERATION = "Save new Doctor like Dr.Sanjeeev Upreti, Dr. Daniel Shrestha, etc";
        String UPDATE_OPERATION = "Update existing Doctor";
        String DELETE_OPERATION = "Set Doctor status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Doctor according to given request parameters";
        String DETAILS_OPERATION = "Fetch Doctor details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch Doctor details (id,name and Doctor avatar) for dropdown";
    }

    public interface DoctorTypeConstant {
        String BASE_API_VALUE = "This is Doctor Type Controller.";
        String FETCH_ACTIVE_DOCTOR_TYPE = "Fetch active Doctor type.";
    }

    public interface DoctorDutyRosterConstant {
        String BASE_API_VALUE = "This is Doctor Duty Roster Controller";
        String SAVE_OPERATION = "Save Doctor Duty Roster";
        String UPDATE_OPERATION = "Update Doctor Duty Roster";
        String DELETE_OPERATION = "Set Doctor Duty Roster status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Doctor Duty Roster according to given request parameters";
        String DETAILS_OPERATION = "Fetch Doctor Duty Roster details by its id";
        String CHECK_AVAILABILITY_OPERATION = "Fetch available doctor time schedules";
        String FETCH_DOCTOR_DUTY_ROSTER_STATUS_OPERATION = "Fetch doctor duty roster status";
        String UPDATE_DOCTOR_DUTY_ROSTER_OVERRIDE_OPERATION = "Update Doctor Duty Roster Override schedules";
    }

    //E
    public interface EthnicityConstant {
        String BASE_ETHNICITY_API_VALUE = "This is Ethnicity Controller";
        String SAVE_ETHNICITY_OPERATION = "Save new ethnicity";
        String UPDATE_ETHNICITY_OPERATION = "Update existing ethnicity";
        String DELETE_ETHNICITY_OPERATION = "Set ethnicity status as 'D' when deleted";
        String ETHNICITY_DETAIL_OPERATION = "Fetch ethnicity details ";
        String ETHNICITY_OPERATION = "Fetch ethnicity enitity ";
        String SEARCH_OPERATION = "Search ethnicity according to given request parameters";
        String FETCH_ETHNICITY_FOR_DROP_DOWN_OPERATION = "Fetch minimal ethnicity details (id and name) for dropdown";
        String FETCH_ACTIVE_ETHNICITY_FOR_DROP_DOWN_OPERATION = "Fetch minimal active ethnicity details (id and name) " +
                "for dropdown";
    }

    //F
    public interface ServiceConstant {
        String BASE_SERVICE_API_VALUE = "This is Service Controller";
        String SAVE_SERVICE_OPERATION = "Save new service";
        String UPDATE_SERVICE_OPERATION = "Update existing service";
        String DELETE_SERVICE_OPERATION = "Set service status as 'D' when deleted";
        String SEARCH_OPERATION = "Search service according to given request parameters";
        String SERVICE_DETAIL_OPERATION = "Fetch service details ";
        String FETCH_SERVICE_FOR_DROP_DOWN_OPERATION = "Fetch minimal service details (id and name) " +
                "for dropdown";
        String FETCH_ACTIVE_SERVICE_FOR_DROP_DOWN_OPERATION = "Fetch minimal active service details" +
                " (id and name) for dropdown";
    }

    public interface ServiceChargeConstant {
        String BASE_SERVICE_CHARGE_API_VALUE = "This is Service-Charge Controller";
        String SAVE_SERVICE_CHARGE_OPERATION = "Save new service-charge";
        String UPDATE_SERVICE_CHARGE_OPERATION = "Update existing service-charge";
        String DELETE_SERVICE_CHARGE_OPERATION = "Set service-charge status as 'D' when deleted";
        String SEARCH_OPERATION = "Search service-charge according to given request parameters";
        String SERVICE_CHARGE_DETAIL_OPERATION = "Fetch service-charge details ";
        String FETCH_SERVICE_CHARGE_FOR_DROP_DOWN_OPERATION = "Fetch minimal service-charge details (id and name) " +
                "for dropdown";
        String FETCH_ACTIVE_SERVICE_CHARGE_FOR_DROP_DOWN_OPERATION = "Fetch minimal active service-charge details" +
                " (id and name) for dropdown";
        String FETCH_ACTIVE_SERVICE_CHARGE_BY_BILLING_MODE_ID_FOR_DROP_DOWN_OPERATION = "Fetch minimal active service-charge details" +
                " (id and name) for dropdown by billing mode id";
    }

    public interface FollowUpSetupConstant {
        String BASE_API_VALUE = "This is Follow up setup Controller";
        String SAVE_OPERATION = "Save new Follow up setup for each patient type.";
        String UPDATE_OPERATION = "Update existing Follow up setup.";
        String DELETE_OPERATION = "Set follow up status as 'D' when deleted";
        String FETCH_MINIMAL_OPERATION = "Fetch minimal follow up details";
        String DETAILS_OPERATION = "Fetch follow up details by its id";
    }

    public interface ForgotPasswordConstant {
        String BASE_API_VALUE = "This is Forgot Password Controller";
        String FORGOT_PASSWORD_OPERATION = "Validate admin and send reset code in email";
        String VERIFY_RESET_CODE = "Verify if the reset code is valid and has not expired";
        String UPDATE_PASSWORD = "Update password of respective admin";
    }

    //G
    public interface GenderConstant {
        String BASE_API_VALUE = "This is Gender Controller";
        String FETCH_ACTIVE_GENDER = "Fetch active gender like MALE, FEMALE & OTHERS";
    }

    //H
    public interface HospitalConstant {
        String BASE_API_VALUE = "This is Hospital setup Controller";
        String SAVE_OPERATION = "Save new hospital";
        String UPDATE_OPERATION = "Update existing hospital";
        String DELETE_OPERATION = "Set hospital status as 'D' when deleted with remarks";
        String SEARCH_OPERATION = "Search hospital according to given request parameters";
        String ENTITY_OPERATION = "Fetch hospital entity by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal hospital details (id and name) for dropdown";
    }


    //I
    public interface InsuranceCompanyConstant {
        String BASE_API_VALUE = "This is Insurance setup Controller";
        String SAVE_INSURANCE_COMPANY_OPERATION = "Save new insurance company";
        String UPDATE_INSURANCE_COMPANY_OPERATION = "Update existing insurance company";
        String DELETE_INSURANCE_COMPANY_OPERATION = "Set insurance company status as 'D' when deleted with remarks";
        String SEARCH_INSURANCE_COMPANY_OPERATION = "Search insurance company according to given request parameters";
        String FETCH_FOR_INSURANCE_COMPANY_DROPDOWN = "Fetch minimal insurance company details (id and name) for dropdown";
        String FETCH_FOR_INSURANCE_COMPANY_ACTIVE_DROPDOWN = "Fetch minimal insurance company details (id and name) for active dropdown";
        String INSURANCE_COMPANY_DETAIL_OPERATION = "Fetch insurance company details ";
        String INSURANCE_COMPANY_OPERATION = "Fetch insurance company details ";
    }

    //J
    //K
    //L
    //M
    public interface MaritalStatusConstant {
        String BASE_API_VALUE = "This is Marital-Status setup Controller";
        String SAVE_OPERATION = "Save new marital-status";
        String UPDATE_OPERATION = "Update existing marital-status";
        String DELETE_OPERATION = "Set marital-status status as 'D' when deleted with remarks";
        String SEARCH_OPERATION = "Search marital-status according to given request parameters";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal marital-status details (id and name) for dropdown";
        String FETCH_MARITAL_OPERATION = "Fetch nationality ";
    }

    public interface MunicipalityConstant {
        String BASE_API_VALUE = "This is Municipality Controller";
        String SAVE_OPERATION = "Save new Municipality";
        String UPDATE_OPERATION = "Update existing Municipality";
        String DELETE_OPERATION = "Set Municipality status as 'D' when deleted with remarks";
        String SEARCH_OPERATION = "Search Municipality according to given request parameters";
        String DETAILS_OPERATION = "Fetch Municipality details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal Municipality details (id and name) for dropdown";
    }

    //N
    public interface NationalityConstant {
        String BASE_API_VALUE = "This is Nationality setup Controller";
        String SAVE_NATIONALITY_OPERATION = "Save new nationality";
        String UPDATE_NATIONALITY_OPERATION = "Update existing nationality";
        String DELETE_NATIONALITY_OPERATION = "Set nationality status as 'D' when deleted with remarks";
        String SEARCH_NATIONALITY_OPERATION = "Search nationality according to given request parameters";
        String FETCH_DETAILS_FOR_NATIONALITY_DROPDOWN = "Fetch minimal nationality details (id and name) for dropdown";
        String NATIONALITY_DETAIL_OPERATION = "Fetch nationality details ";
        String NATIONALITY_OPERATION = "Fetch nationality ";
    }

    //O

    //P
    public interface PaymentTypeConstant {
        String BASE_PAYMENT_TYPE_VALUE = "This is Payment Type Controller";
        String FETCH_PAYMENT_TYPET_FOR_DROP_DOWN_OPERATION = "Fetch minimal Payment Type details (id and name) for dropdown";
        String FETCH_ACTIVE_PAYMENT_TYPE_FOR_DROP_DOWN_OPERATION = "Fetch minimal active Payment Type details (id and name) " +
                "for dropdown";
    }

    public interface PatientTypeConstant {
        String BASE_API_VALUE = "This is Patient Type Controller";
        String SAVE_OPERATION = "Save new Patient Type like Out Patient, Inpatient, New Patient etc";
        String UPDATE_OPERATION = "Update existing Patient Type";
        String DELETE_OPERATION = "Set Patient Type status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Patient Type according to given request parameters";
        String DETAILS_OPERATION = "Fetch Patient Type details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal Patient Type details (id and name) for dropdown";
    }

    public interface ProfileConstant {
        String BASE_API_VALUE = "This is Profile Controller";
        String SAVE_OPERATION = "Save new profile";
        String UPDATE_OPERATION = "Update existing profile";
        String DELETE_OPERATION = "Set profile status as 'D' when deleted";
        String SEARCH_OPERATION = "Search profile according to given request parameters";
        String DETAILS_OPERATION = "Fetch profile details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal profile details (id and name) for dropdown";
        String FETCH_PROFILE_BY_SUB_DEPARTMENT_ID = "Fetch active profiles by sub-department id";
    }

    public interface ProvincesConstant {
        String BASE_PROVINCES_API_VALUE = "This is Provinces Controller";
        String SAVE_PROVINCES_OPERATION = "Save new provinces";
        String UPDATE_PROVINCES_OPERATION = "Update existing provinces";
        String DELETE_PROVINCES_OPERATION = "Set provinces status as 'D' when deleted";
        String PROVINCES_DETAIL_OPERATION = "Fetch provinces details ";
        String SEARCH_OPERATION = "Search provinces according to given request parameters";
        String FETCH_PROVINCES_FOR_DROP_DOWN_OPERATION = "Fetch minimal provinces details (id and name) for dropdown";
        String FETCH_ACTIVE_PROVINCES_FOR_DROP_DOWN_OPERATION = "Fetch minimal active provinces details (id and name) " +
                "for dropdown";
    }

    //Q
    public interface QualificationConstant {
        String BASE_API_VALUE = "This is Qualification Controller";
        String SAVE_OPERATION = "Save new Qualification";
        String UPDATE_OPERATION = "Update existing Qualification";
        String DELETE_OPERATION = "Set Qualification status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Qualification according to given request parameters";
        String DETAILS_OPERATION = "Fetch Qualification details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal Qualification details for dropdown";
    }

    public interface QualificationAliasConstant {
        String BASE_API_VALUE = "This is Qualification Alias Controller";
        String FETCH_ACTIVE_QUALIFICATION_ALIAS = "Fetch active Qualification Alias like M.D.,M.B.B.S, etc";
    }

    //R
    public interface RegisteredBankConstant {
        String BASE_REGISTERED_BANK_API_VALUE = "This is Registered Bank Controller";
        String SAVE_REGISTERED_BANK_OPERATION = "Save new Registered Bank";
        String UPDATE_REGISTERED_BANK_OPERATION = "Update existing Registered Bank";
        String DELETE_REGISTERED_BANK_OPERATION = "Set Registered Bank status as 'D' when deleted";
        String REGISTERED_BANK_DETAIL_OPERATION = "Fetch Registered Bank details ";
        String SEARCH_REGISTERED_BANK_OPERATION = "Search Registered Bank according to given request parameters";
        String FETCH_REGISTERED_BANK_FOR_DROP_DOWN_OPERATION = "Fetch minimal Registered Bank details (id and name) for dropdown";
        String FETCH_ACTIVE_REGISTERED_BANK_FOR_DROP_DOWN_OPERATION = "Fetch minimal active Registered Bank details (id and name) " +
                "for dropdown";
    }

    public interface ReligionConstant {
        String BASE_API_VALUE = "This is Religion setup Controller";
        String SAVE_OPERATION = "Save new religion";
        String UPDATE_OPERATION = "Update existing religion";
        String DELETE_OPERATION = "Set religion status as 'D' when deleted with remarks";
        String SEARCH_OPERATION = "Search religion according to given request parameters";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal religion details (id and name) for dropdown";
        String FETCH_RELIGION_OPERATION = "Fetch religion ";
    }

    public interface ReferrerConstant {
        String BASE_API_VALUE = "This is Referrer setup Controller";
        String SAVE_OPERATION = "Save new referrer";
        String UPDATE_OPERATION = "Update existing referrer";
        String DELETE_OPERATION = "Set referrer status as 'D' when deleted with remarks";
        String SEARCH_OPERATION = "Search referrer according to given request parameters";
        String ENTITY_OPERATION = "Fetch referrer entity by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal referrer details (id and name) for dropdown";
    }

    //S


    public interface SpecializationConstant {
        String BASE_API_VALUE = "This is Specialization Controller";
        String SAVE_OPERATION = "Save new Specialization like Physician, Surgeon, etc";
        String UPDATE_OPERATION = "Update existing Specialization";
        String DELETE_OPERATION = "Set Specialization status as 'D' when deleted";
        String SEARCH_OPERATION = "Search Specialization according to given request parameters";
        String DETAILS_OPERATION = "Fetch Specialization details by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal Specialization details (id and name) for dropdown";
    }

    public interface SubDepartmentConstant {
        String BASE_SUB_DEPARTMENT_API_VALUE = "This is Sub-Department Controller";
        String SAVE_SUB_DEPARTMENT_OPERATION = "Save new sub-department";
        String UPDATE_SUB_DEPARTMENT_OPERATION = "Update existing sub-department";
        String DELETE_SUB_DEPARTEMNT_OPERATION = "Set sub-department status as 'D' when deleted";
        String SEARCH_SUB_DEPARTMENT_OPERATION = "Search sub-department according to given request parameters";
        String SUB_DEPARTMENT_DETAILS_OPERATION = "Fetch sub-department details";
        String FETCH_SUB_DEPARTMENT_FOR_DROP_DOWN_OPERATION = "Fetch minimal sub-department details (id and name) " +
                "for dropdown";
        String DOWNLOAD_SUB_DEPARTMENT_EXCEL_OPERATION = "Download excelsheet of sub-department in byte array format";
    }

    public interface SurnameConstant {
        String BASE_API_VALUE = "This is Surname setup Controller";
        String SAVE_OPERATION = "Save new surname";
        String UPDATE_OPERATION = "Update existing surname";
        String DELETE_OPERATION = "Set surname status as 'D' when deleted with remarks";
        String SEARCH_OPERATION = "Search surname according to given request parameters";
        String DETAIL_OPERATION = "Fetch surname details ";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal surname details (id and name) for dropdown";
        String FETCH_SURNAME_OPERATION = "Fetch surname (feign-client)";
    }

    //T
    public interface TitleConstant {
        String BASE_API_VALUE = "This is Title setup Controller";
        String SAVE_OPERATION = "Save new title";
        String UPDATE_OPERATION = "Update existing title";
        String DELETE_OPERATION = "Set title status as 'D' when deleted with remarks";
        String SEARCH_OPERATION = "Search title according to given request parameters";
        String ENTITY_OPERATION = "Fetch title entity by its id";
        String FETCH_DETAILS_FOR_DROPDOWN = "Fetch minimal title details (id and name) for dropdown";
    }

    //U
    public interface UnitConstant {
        String BASE_UNIT_API_VALUE = "This is Unit Controller";
        String SAVE_UNIT_OPERATION = "Save new unit";
        String UPDATE_UNIT_OPERATION = "Update existing unit";
        String DELETE_UNIT_OPERATION = "Set unit status as 'D' when deleted";
        String UNIT_DETAIL_OPERATION = "Fetch unit details ";
        String SEARCH_UNIT_OPERATION = "Search unit according to given request parameters";
        String FETCH_UNIT_FOR_DROP_DOWN_OPERATION = "Fetch minimal unit details (id and name) for dropdown";
        String FETCH_ACTIVE_UNIT_FOR_DROP_DOWN_OPERATION = "Fetch minimal active unit details (id and name) " +
                "for dropdown";
    }

    //V

    //W
    public interface WardConstant {
        String BASE_WARD_API_VALUE = "This is Ward Controller";
        String SAVE_WARD_OPERATION = "Save new Ward";
        String UPDATE_WARD_OPERATION = "Update existing Ward";
        String DELETE_WARD_OPERATION = "Set Ward status as 'D' when deleted";
        String DELETE_WARD_UNIT_OPERATION = "Set Ward_Unit status as 'D' when deleted";
        String WARD_DETAIL_OPERATION = "Fetch Ward details ";
        String SEARCH_WARD_OPERATION = "Search Ward according to given request parameters";
        String FETCH_WARD_FOR_DROP_DOWN_OPERATION = "Fetch minimal Ward details (id and name) for dropdown";
        String FETCH_ACTIVE_WARD_FOR_DROP_DOWN_OPERATION = "Fetch minimal active Ward details (id and name) " +
                "for dropdown";
    }

    public interface WeekDaysConstant {
        String BASE_API_VALUE = "This is Week Days Controller.";
        String FETCH_ACTIVE_WEEK_DAYS = "Fetch active week days.";
    }

    //X
    //Y
    //Z
}
