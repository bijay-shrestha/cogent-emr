package com.cogent.admin.feign.dto.response.fileserver;

import lombok.*;

import java.io.Serializable;

/**
 * @author smriti on 2019-10-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileServerResponseDTO implements Serializable {
    private String fileUri;

    private String fileType;

    private Long fileSize;


}
