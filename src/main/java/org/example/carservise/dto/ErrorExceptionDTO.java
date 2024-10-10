package org.example.carservise.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorExceptionDTO {
    private int code;
    private String field;
    private String message;
}
