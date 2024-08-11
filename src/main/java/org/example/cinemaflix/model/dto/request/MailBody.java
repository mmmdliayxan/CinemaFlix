package org.example.cinemaflix.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailBody {

    @NotNull(message = "Email cannot be null")
    private String receiverEmail;

    private String subject;

    private String text;

}
