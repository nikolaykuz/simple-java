package ee.devclub.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import java.io.Serializable;

//TODO: better class available in some lib?

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Location implements Serializable {
    @NonNull @Digits(integer = 2, fraction = 2)
    float latitude;

    @NonNull @Digits(integer = 2, fraction = 2)
    float longitude;
}
