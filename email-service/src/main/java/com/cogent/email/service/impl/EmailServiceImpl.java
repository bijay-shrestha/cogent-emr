package com.cogent.email.service.impl;

import com.cogent.email.dto.request.email.EmailRequestDTO;
import com.cogent.email.repository.EmailToSendRepository;
import com.cogent.email.service.EmailService;
import com.cogent.persistence.model.EmailToSend;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.cogent.email.constants.EmailConstants.*;
import static com.cogent.email.constants.EmailParams.Admin.*;
import static com.cogent.email.constants.EmailParams.Appointment.*;
import static com.cogent.email.constants.EmailParams.ForgotPassword.RESET_CODE;
import static com.cogent.email.constants.EmailParams.USERNAME;
import static com.cogent.email.constants.EmailTemplates.*;
import static com.cogent.email.constants.StatusConstants.YES;
import static com.cogent.email.constants.StringConstant.*;
import static com.cogent.email.log.CommonLogConstant.SAVING_PROCESS_COMPLETED;
import static com.cogent.email.log.CommonLogConstant.SAVING_PROCESS_STARTED;
import static com.cogent.email.log.constant.EmailLog.*;
import static com.cogent.email.utils.DateUtils.getDifferenceBetweenTwoTime;
import static com.cogent.email.utils.DateUtils.getTimeInMillisecondsFromLocalDate;
import static com.cogent.email.utils.EmailUtils.convertDTOToEmailToSend;
import static com.cogent.email.utils.EmailUtils.convertToUpdateEmailToSend;

/**
 * @author smriti on 7/23/19
 */
@Service
@Transactional
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailToSendRepository emailToSendRepository;

    private final JavaMailSender javaMailSender;

    private final Configuration configuration;

    public EmailServiceImpl(EmailToSendRepository emailToSendRepository,
                            JavaMailSender javaMailSender,
                            Configuration configuration) {
        this.emailToSendRepository = emailToSendRepository;
        this.javaMailSender = javaMailSender;
        this.configuration = configuration;
    }

    @Override
    public void sendEmail(EmailRequestDTO emailRequestDTO) {
        EmailToSend emailToSend = saveEmailToSend(emailRequestDTO);
        send(emailToSend);
        updateEmailToSend(emailToSend);
    }

    public EmailToSend saveEmailToSend(EmailRequestDTO emailRequestDTO) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SAVING_PROCESS_STARTED, EMAIL_TO_SEND);

        EmailToSend emailToSend = emailToSendRepository.save(convertDTOToEmailToSend(emailRequestDTO));

        log.info(SAVING_PROCESS_COMPLETED, EMAIL_TO_SEND, getDifferenceBetweenTwoTime(startTime));

        return emailToSend;
    }

    public void send(EmailToSend emailToSend) {
        Long startTime = getTimeInMillisecondsFromLocalDate();

        log.info(SENDING_EMAIL_PROCESS_STARTED);

        try {
            MimeMessage message = getMimeMessage(emailToSend);
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            Map<String, Object> model = new HashMap<>();
            String html = "";

            switch (emailToSend.getTemplateName()) {
                case ADMIN_VERIFICATION: {
                    parseToAdminVerificationTemplate(emailToSend, model);
                    html = getFreeMarkerContent(model, ADMIN_VERIFICATION_TEMPLATE, html);
                    break;
                }

                case RESET_PASSWORD: {
                    parseToResetPasswordTemplate(emailToSend, model);
                    html = getFreeMarkerContent(model, ADMIN_RESET_PASSWORD_TEMPLATE, html);
                    break;
                }

                case UPDATE_ADMIN: {
                    parseToUpdateAdminTemplate(emailToSend, model);
                    html = getFreeMarkerContent(model, UPDATE_ADMIN_TEMPLATE, html);
                    break;
                }

                case FORGOT_PASSWORD: {
                    parseToForgotPasswordTemplate(emailToSend, model);
                    html = getFreeMarkerContent(model, FORGOT_PASSWORD_TEMPLATE, html);
                    break;
                }

                case APPOINTMENT_NOTIFICATION: {
                    parseToAppointmentTemplate(emailToSend, model);
                    html = getFreeMarkerContent(model, APPOINTMENT_TEMPLATE, html);
                    break;
                }

                case APPOINTMENT_RESCHEDULED_NOTIFICATION: {
                    parseToRescheduledAppointmentTemplate(emailToSend, model);
                    html = getFreeMarkerContent(model, APPOINTMENT_RESCHEDULE_TEMPLATE, html);
                    break;
                }

                default:
                    break;
            }

            helper.setText(html, true);
            helper.addInline(LOGO_FILE_NAME, new FileSystemResource(new File(LOGO_LOCATION)));

            javaMailSender.send(message);

            log.info(SENDING_EMAIL_PROCESS_COMPLETED, getDifferenceBetweenTwoTime(startTime));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private MimeMessage getMimeMessage(EmailToSend emailToSend) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailToSend.getReceiverEmailAddress()));
        message.setSubject(emailToSend.getSubject());
        return message;
    }

    private String getFreeMarkerContent(Map<String, Object> model, String templateName, String html) {
        try {
            Template template = configuration.getTemplate(templateName);
            html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return html;
    }

    private void updateEmailToSend(EmailToSend emailToSend) {
        emailToSendRepository.save(convertToUpdateEmailToSend(emailToSend));
    }

    private void parseToAdminVerificationTemplate(EmailToSend emailToSend,
                                                  Map<String, Object> model) {
        final int USERNAME_INDEX = 0;
        final int ADMIN_CONFIRMATION_URL_INDEX = 1;

        String[] paramValues = emailToSend.getParamValue().split(COMMA_SEPARATED);

        model.put(USERNAME, paramValues[USERNAME_INDEX]);
        model.put(ADMIN_CONFIRMATION_URL, paramValues[ADMIN_CONFIRMATION_URL_INDEX]);
    }

    private void parseToResetPasswordTemplate(EmailToSend emailToSend,
                                              Map<String, Object> model) {
        final int USERNAME_INDEX = 0;
        final int PASSWORD_INDEX = 1;
        final int REMARKS_INDEX = 2;

        String[] paramValues = emailToSend.getParamValue().split(COMMA_SEPARATED);

        model.put(USERNAME, paramValues[USERNAME_INDEX]);
        model.put(PASSWORD, paramValues[PASSWORD_INDEX]);
        model.put(REMARKS, paramValues[REMARKS_INDEX]);
    }

    private void parseToUpdateAdminTemplate(EmailToSend emailToSend,
                                            Map<String, Object> model) {
        final int USERNAME_INDEX = 0;
        final int UPDATED_ADMIN_DETAILS_INDEX = 1;
        final int HAS_MAC_BINDING_INDEX = 2;
        final int MAC_ADDRESS_INDEX = 3;

        String param = emailToSend.getParamValue().replaceAll(BRACKET_REGEX, "");
        String[] paramValues = param.split(HYPHEN);
        String[] updatedValues = paramValues[UPDATED_ADMIN_DETAILS_INDEX].split(COMMA_SEPARATED);

        String[] updatedMacAddress = paramValues[HAS_MAC_BINDING_INDEX].equals(YES) ?
                paramValues[MAC_ADDRESS_INDEX].split(COMMA_SEPARATED)
                : new String[]{paramValues[MAC_ADDRESS_INDEX]};

        model.put(USERNAME, paramValues[USERNAME_INDEX]);
        model.put(UPDATED_DATA, Arrays.asList(updatedValues));
        model.put(HAS_MAC_BINDING, paramValues[HAS_MAC_BINDING_INDEX]);
        model.put(UPDATED_MAC_ADDRESS, Arrays.asList(updatedMacAddress));
    }

    private void parseToForgotPasswordTemplate(EmailToSend emailToSend, Map<String, Object> model) {
        final int USERNAME_INDEX = 0;
        final int RESET_CODE_INDEX = 1;

        String[] paramValues = emailToSend.getParamValue().split(COMMA_SEPARATED);
        model.put(USERNAME, paramValues[USERNAME_INDEX]);
        model.put(RESET_CODE, paramValues[RESET_CODE_INDEX]);
    }

    private void parseToAppointmentTemplate(EmailToSend emailToSend,
                                            Map<String, Object> model) {

        final int PATIENT_NAME_INDEX = 0;
        final int DOCTOR_NAME_INDEX = 1;
        final int APPOINTMENT_DATE_INDEX = 2;
        final int APPOINTMENT_TIME_INDEX = 3;
        final int APPOINTMENT_NUMBER_INDEX = 4;

        String[] paramValues = emailToSend.getParamValue().split(COMMA_SEPARATED);

        model.put(PATIENT_NAME, paramValues[PATIENT_NAME_INDEX]);
        model.put(DOCTOR_NAME, paramValues[DOCTOR_NAME_INDEX]);
        model.put(APPOINTMENT_DATE, paramValues[APPOINTMENT_DATE_INDEX]);
        model.put(APPOINTMENT_TIME, paramValues[APPOINTMENT_TIME_INDEX]);
        model.put(APPOINTMENT_NUMBER, paramValues[APPOINTMENT_NUMBER_INDEX]);
    }

    private void parseToRescheduledAppointmentTemplate(EmailToSend emailToSend,
                                                       Map<String, Object> model) {

        final int PATIENT_NAME_INDEX = 0;
        final int DOCTOR_NAME_INDEX = 1;
        final int APPOINTMENT_DATE_INDEX = 2;
        final int APPOINTMENT_TIME_INDEX = 3;
        final int APPOINTMENT_NUMBER_INDEX = 4;
        final int RESCHEDULED_APPOINTMENT_DATE_INDEX = 5;
        final int RESCHEDULED_APPOINTMENT_TIME_INDEX = 6;

        String[] paramValues = emailToSend.getParamValue().split(COMMA_SEPARATED);

        model.put(PATIENT_NAME, paramValues[PATIENT_NAME_INDEX]);
        model.put(DOCTOR_NAME, paramValues[DOCTOR_NAME_INDEX]);
        model.put(APPOINTMENT_DATE, paramValues[APPOINTMENT_DATE_INDEX]);
        model.put(APPOINTMENT_TIME, paramValues[APPOINTMENT_TIME_INDEX]);
        model.put(APPOINTMENT_NUMBER, paramValues[APPOINTMENT_NUMBER_INDEX]);
        model.put(RESCHEDULED_APPOINTMENT_DATE, paramValues[RESCHEDULED_APPOINTMENT_DATE_INDEX]);
        model.put(RESCHEDULED_APPOINTMENT_TIME, paramValues[RESCHEDULED_APPOINTMENT_TIME_INDEX]);
    }
}
