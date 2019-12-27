package com.cogent.admin.dto.registeredBank;

import com.cogent.admin.dto.commons.DropDownResponseDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankSearchRequestDTO;
import com.cogent.admin.dto.request.registeredBank.RegisteredBankUpdateRequestDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankMinimalResponseDTO;
import com.cogent.admin.dto.response.registeredBank.RegisteredBankResponseDTO;
import com.cogent.persistence.model.RegisteredBank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sauravi Thapa 11/4/19
 */
public class RegisteredBankTestUtils {

    public static RegisteredBankRequestDTO getRegisteredBankRequestDTO() {
        return RegisteredBankRequestDTO.builder()
                .name("RegisteredBank-1")
                .code("R-1")
                .status('Y')
                .contact("9843698393")
                .address("baluwatar")
                .swiftCode("STBSSSSMRC")
                .build();
    }

    public static RegisteredBank getRegisteredBankInfo() {
        RegisteredBank registeredBank = new RegisteredBank();
        registeredBank.setId(1L);
        registeredBank.setName("RegisteredBank-1");
        registeredBank.setCode("R-1");
        registeredBank.setStatus('Y');
        registeredBank.setContact("9843698393");
        registeredBank.setAddress("baluwatar");
        registeredBank.setSwiftCode("STBSSSSMRC");

        return registeredBank;
    }

    public static List<RegisteredBank> getRegisteredBanks() {
        return Arrays.asList(getRegisteredBankInfo());
    }

    public static RegisteredBankUpdateRequestDTO getUpdateRegisteredBankRequestDTO() {
        return RegisteredBankUpdateRequestDTO.builder()
                .id(1L)
                .name("RegisteredBank-1")
                .code("R-1")
                .status('Y')
                .contact("9843698393")
                .address("baluwatar")
                .swiftCode("STBSSSSMRC")
                .remarks("I want to update re")
                .build();
    }

    public static RegisteredBank getDeletedRegisteredBankInfo() {
        RegisteredBank registeredBank = new RegisteredBank();
        registeredBank.setId(1L);
        registeredBank.setName("RegisteredBank-1");
        registeredBank.setCode("R-1");
        registeredBank.setStatus('D');
        registeredBank.setContact("9843698393");
        registeredBank.setAddress("baluwatar");
        registeredBank.setSwiftCode("STBSSSSMRC");
        registeredBank.setRemarks("Yes.. Delete it");

        return registeredBank;
    }

    public static RegisteredBankSearchRequestDTO getSearchRegisteredBankRequestDTO() {
        return RegisteredBankSearchRequestDTO.builder()
                .id(1L)
                .name("RegisteredBank-1")
                .code("R-1")
                .status('Y')
                .build();
    }

    public static List<Object[]> getRegisteredBankObjectForNameAndCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "RegisteredBank-1";
        object[1] = "R-1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getRegisteredBankObjectForCode() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[1] = "R-1";
        objects.add(object);
        return objects;
    }

    public static List<Object[]> getRegisteredBankObjectForName() {
        List<Object[]> objects = new ArrayList<>();
        Object[] object = new Object[2];
        object[0] = "RegisteredBank-1";
        objects.add(object);
        return objects;
    }

    public static List<RegisteredBankMinimalResponseDTO> getRegisteredBankMinimalResponseDTOList() {
        return Arrays.asList(RegisteredBankMinimalResponseDTO
                .builder()
                .name("RegisteredBank-1")
                .code("R-1")
                .status('Y')
                .build());
    }

    public static List<DropDownResponseDTO> getRegisteredBankDropDownInfo() {
        return Arrays.asList(DropDownResponseDTO
                .builder()
                .value(1L)
                .label("RegisteredBank-2")
                .build());

    }

    public static RegisteredBankResponseDTO getRegisteredBankResponseDTO() {
        return RegisteredBankResponseDTO.builder()
                .name("RegisteredBank-1")
                .code("R-1")
                .status('Y')
                .remarks("test")
                .build();
    }


}
