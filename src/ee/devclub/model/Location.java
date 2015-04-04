package ee.devclub.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

//TODO: better class available in some lib?

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Location implements Serializable {
    @NonNull
    float latitude;

    @NonNull
    float longitude;
}
