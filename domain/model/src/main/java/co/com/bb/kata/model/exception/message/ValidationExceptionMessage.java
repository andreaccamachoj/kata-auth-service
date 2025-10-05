package co.com.bb.kata.model.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ValidationExceptionMessage {
    NAMES_REQUIRED(
      "VAL0001", "Field 'nombres' is required", "400",
              "El campo 'nombres' no puede estar vacío."
    );

    private final String code;
    private final String description;
    private final String itcCode;
    private final String message;
}
