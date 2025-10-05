package co.com.bb.kata.model.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TechnicalExceptionMessage {
    ROL_NOT_FOUND(
            "TEC0001", "Rol not found", "404",
            "The specified role does not exist in the system."
    ),
    SAVING_USER_ERROR(
            "TEC0002", "Error saving user", "409",
                    "An error occurred while trying to save the user."
    ),
    SAVING_CREDENTIALS_ERROR(
            "TEC0002", "Error saving Credentials", "409",
            "An error occurred while trying to save the credentials."
    );
    private final String code;
    private final String description;
    private final String itcCode;
    private final String message;
}
