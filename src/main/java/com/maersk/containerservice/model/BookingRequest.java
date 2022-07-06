package com.maersk.containerservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRequest {

    @Valid
    @NotNull
    @Schema(required = true, description = "This is appropriate size of container")
    private Integer containerSize;

    @Valid
    @NotNull
    @Schema(required = true, description = "This is type of container", implementation = ContainerType.class, example = "DRY")
    private String containerType;

    @Valid
    @NotNull
    @Size(min = 5, max = 20)
    @Schema(required = true, description = "This is the origin of the container")
    private String origin;

    @Valid
    @NotNull
    @Size(min = 5, max = 20)
    @Schema(required = true, description = "This is the destination of the container")
    private String destination;

    @Valid
    @NotNull
    @Range(min = 1, max= 100)
    @Schema(required = true, description = "This is the quantity of the container")
    private Integer quantity;


    @JsonFormat(pattern = "yyyy/MM/dd")
    @Schema(required = true, description = "This is the Booking date of the container")
    private Date bookingDate;
}
