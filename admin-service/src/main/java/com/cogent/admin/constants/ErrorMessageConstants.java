package com.cogent.admin.constants;

/**
 * @author smriti on 7/9/19
 */
public class ErrorMessageConstants {

    //A
    public interface AdminCategoryServiceMessage {
        String NAME_DUPLICATION_DEBUG_MESSAGE = "Admin Category entity is not null with name : ";
        String NAME_DUPLICATION_MESSAGE = " already exists with name : ";

        String CODE_DUPLICATION_DEBUG_MESSAGE = "Admin Category entity is not null with code : ";
        String CODE_DUPLICATION_MESSAGE = " already exists with code : ";

        String NAME_AND_CODE_DUPLICATION_DEBUG_MESSAGE = "Admin Category is not null with given name and code.";
        String NAME_AND_CODE_DUPLICATION_MESSAGE = " already exists with given name and code.";
    }

    public interface AdminServiceMessages {
        String ADMIN_DUPLICATION_DEBUG_MESSAGE = "Admin entity with given username, email and" +
                " mobile number is not null.";
        String ADMIN_DUPLICATION_MESSAGE = " with given username, email and mobile number" +
                " already exists.";

        String ADMIN_NOT_FOUND = "doesn't exist";

        String ADMIN_NOT_ACTIVE = "is not active";

        String ADMIN_REGISTERED = "Admin is already registered";

        String DUPLICATE_PASSWORD_MESSAGE = "New Password must be different than current password.";

        String EMAIL_DUPLICATION_DEBUG_MESSAGE = "Admin entity with given email is not null.";
        String EMAIL_DUPLICATION_MESSAGE = " with given email already exists.";

        String INVALID_CONFIRMATION_TOKEN = "Invalid Confirmation Link. Please try again.";

        String MOBILE_NUMBER_DUPLICATION_DEBUG_MESSAGE = "Admin entity with given mobile number is not null.";
        String MOBILE_NUMBER_DUPLICATION_MESSAGE = " with given mobile number already exists.";

        String PASSWORD_MISMATCH_MESSAGE = "Old password doesn't match.";

        String USERNAME_DUPLICATION_DEBUG_MESSAGE = "Admin entity with given username is not null.";
        String USERNAME_DUPLICATION_MESSAGE = " with given username already exists.";

    }

    public interface AssignBedMessages {
        String BED_ALREADY_EXIST = " Bed Already exists in this ward";
    }

    public interface AppointmentServiceMessage {
        String APPOINTMENT_EXISTS_MESSAGE = "Cannot update doctor duty roster because appointment" +
                " exists within the selected date range";
    }

    //B

    //C
    public final static String CODE_DUPLICATION_DEBUG_MESSAGE = " entity is not null with given Code : ";
    public final static String CODE_DUPLICATION_MESSAGE = " code already exists with  : ";

    //D
    public interface DoctorDutyRosterServiceMessages {
        String DUPLICATION_MESSAGE = "Doctor Duty Roster already exists for selected doctor.";
        String BAD_REQUEST_MESSAGE = "Doctor Duty Roster Override doesn't lie within the duty roster date range.";
    }


    //E
    public interface EthnicityServiceMessages {
        String ETHNICITY_NAME_DUPLICATION_DEBUG_MESSAGE = " entity is not null with name : ";
        String ETHNICITY_NAME_DUPLICATION_MESSAGE = " already exists with name : ";
    }

    //F
    public interface FileServiceMessages {
        String FILES_EMPTY_MESSAGE = "Failed to store empty file";
        String INVALID_FILE_TYPE_MESSAGE = "Could not read file :";
        String INVALID_FILE_SEQUENCE = "Sorry! Filename contains invalid path sequence";
        String FILE_EXCEPTION = "Unable to store file. Please try again later";
    }

    public interface ServiceServiceMessages {
        String SERVICE_WITH_BILLING_MODE_DUPLICATION_DEBUG_MESSAGE = " entity already exists with this billing mode";
        String SERVICEWITH_BILLING_MODE_DUPLICATION_MESSAGE = " already exists with this billing mode ";
    }

    public interface ForgotPasswordMessages {
        String INVALID_RESET_CODE = "Invalid Password Reset Code. Please try again.";
        String RESET_CODE_EXPIRED = "Reset code has expired. Request a new password reset code.";
    }


    //G

    //H
    public interface HospitalServiceMessages {
        String HOSPITAL_NAME_DUPLICATION_DEBUG_MESSAGE = " entity is not null with name : ";
        String HOSPITAL_NAME_DUPLICATION_MESSAGE = " already exists with name : ";
    }



    //I

    //J

    //K

    //L

    //M
    public interface MaritalStatusServiceMessages {
        String MARITAL_STATUS_NAME_DUPLICATION_DEBUG_MESSAGE = " entity is not null with name : ";
        String MARITAL_STATUS_NAME_DUPLICATION_MESSAGE = " already exists with name : ";
    }

    //N
    public final static String NAME_AND_CODE_DUPLICATION_DEBUG_MESSAGE = " entity is not null with name and code";
    public final static String NAME_AND_CODE_DUPLICATION_MESSAGE = " name and code already exists";

    public final static String NAME_DUPLICATION_DEBUG_MESSAGE = " entity is not null with given name : ";
    public final static String NAME_DUPLICATION_MESSAGE = " already exists with name : ";


    //O

    //P
    public interface ProfileServiceMessages {

        String NAME_DUPLICATION_DEBUG_MESSAGE = "Profile entity is not null with given name : ";
        String NAME_DUPLICATION_MESSAGE = " already exists with given name : ";
    }

    public interface ProvincesMessages {
        String PROVINCES_DUPLICATION_DEBUG_MESSAGE = " entity is not null with name : ";
        String PROVINCES_DUPLICATION_MESSAGE = " already exists with name : ";
    }

    //Q

    //R
    public interface RegisteredBankMessages {
        String REGISTERED_BANK_SWIFT_CODE_DUPLICATION_DEBUG_MESSAGE = " entity already exists with Swift Code :";
        String REGISTERED_BANK_SWIFT_CODE_DUPLICATION_MESSAGE = " already exists with Swift Code: ";
    }

    public interface ReligionServiceMessages {
        String RELIGION_NAME_DUPLICATION_DEBUG_MESSAGE = " entity is not null with name : ";
        String RELIGION_NAME_DUPLICATION_MESSAGE = " already exists with name : ";
    }

    public interface ReferrerServiceMessages {
        String REFERRER_NAME_DUPLICATION_DEBUG_MESSAGE = " entity is not null with name : ";
        String REFERRER_NAME_DUPLICATION_MESSAGE = " already exists with name : ";
    }


    //S


    public interface SubDepartmentServiceMessages {
        String SUB_DEPARTMENT_NAME_DUPLICATION_DEBUG_MESSAGE = "Sub-DepartmentQuery entity is not null with name : ";
        String SUB_DEPARTMENT_NAME_DUPLICATION_MESSAGE = "Sub-Department already exists with name : ";
    }


    //T

    public interface TitleServiceMessages {
        String TITLE_NAME_DUPLICATION_DEBUG_MESSAGE = " entity is not null with name : ";
        String TITLE_NAME_DUPLICATION_MESSAGE = " already exists with name : ";
    }


    //U

    //V

    //W

    //X

    //Y

    //Z
}
