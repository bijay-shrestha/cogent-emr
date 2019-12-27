package com.cogent.admin.dto.response.ward;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author Sauravi Thapa 10/22/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class WardUnitResponseDTO implements Serializable {

    private List<String> unitName;

}
