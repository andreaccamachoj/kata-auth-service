package co.com.bb.kata.model.exception.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BusinessExceptionMessage {
    USER_ALREADY_EXISTS(
            "BUS0001", "User already exists", "409",
            "The user with the provided email or identity document already exists in the system."
    );

    private final String code;
    private final String description;
    private final String itcCode;
    private final String message;
}
