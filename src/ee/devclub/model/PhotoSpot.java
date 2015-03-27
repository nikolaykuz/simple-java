package ee.devclub.model;

import lombok.*;

import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Access(FIELD)
@Data
@EqualsAndHashCode(exclude={"id"})
@NoArgsConstructor
@RequiredArgsConstructor
public class PhotoSpot implements Serializable {
    @GeneratedValue(strategy = AUTO)
    @Id Long id;

    @NonNull String name;

    @NonNull String description;

    @NonNull Location location;
}
