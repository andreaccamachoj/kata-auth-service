package co.com.bb.kata.model.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionMessage {
    USER_ALREADY_EXISTS(
            "BUS0001", "User already exists", "409",
            "The user with the provided email or identity document already exists in the system."
    ),
    INVALID_OR_EXPIRED_TOKEN(
            "BUS0002", "The provided token is invalid or has expired", "409",
                    "The token provided for authentication is either invalid or has expired. Please log in again to obtain a new token."
    ),
    USER_BY_EMAIL_NOT_FOUND(
            "BUS0003", "User not found by email", "404",
            "No user found with the provided email address."
    );

    private final String code;
    private final String description;
    private final String itcCode;
    private final String message;
}
