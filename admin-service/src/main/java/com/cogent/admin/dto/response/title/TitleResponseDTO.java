package com.cogent.admin.dto.response.title;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TitleResponseDTO implements Serializable {

    private Long id;

    private String name;

    private Character status;

    private String remarks;

    private int totalItems;
}
