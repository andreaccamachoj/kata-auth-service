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
            "TEC0003", "Error saving Credentials", "409",
            "An error occurred while trying to save the credentials."
    ),
    USER_NOT_FOUND(
            "TEC0004", "User not found", "404",
            "The specified user does not exist in the system."
    ),
    FIND_USER_ERROR(
            "TEC0005", "Find user error", "500",
            "An error occurred while trying to find the user."
    ),
    FIND_CREDENTIALS_ERROR(
            "TEC0006", "Error finding credentials in database", "500",
            "An error occurred while trying to find the credentials in the database."
    ),
    CREDENTIALS_NOT_FOUND(
            "TEC0007", "Credentials not found for the given user", "404",
            "Credentials not found for the given user."
    ),
    INVALID_CREDENTIALS(
            "TEC0008", "Credentials invalid for the given user", "409",
            "Credentials invalid for the given user."
    ),
    SAVING_TOKEN_SESSION_ERROR(
            "TEC0009", "Saving token session error", "409",
            "An error occurred while trying to save the token session."
    ),
    EXPIRED_TOKEN(
            "TEC0010", "JWT token has expired", "409",
            "expired JWT token."
    ),
    INVALID_TOKEN(
            "TEC0011", "Invalid or malformed JWT token", "409",
            "invalid or malformed JWT token."
    );
    private final String code;
    private final String description;
    private final String itcCode;
    private final String message;
}
