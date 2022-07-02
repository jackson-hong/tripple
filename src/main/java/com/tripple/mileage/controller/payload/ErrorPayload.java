package com.tripple.mileage.controller.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ErrorPayload {
    private String message;
    private String description;

    public ErrorPayload(String message) {
        this.message = message;
    }

    public ErrorPayload(String message, String description) {
        this.message = message;
        this.description = description;
    }

}
