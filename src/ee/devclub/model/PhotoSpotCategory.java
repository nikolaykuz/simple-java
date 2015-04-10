package ee.devclub.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Access(FIELD)
@Data
@EqualsAndHashCode(exclude={"id"})
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "photospot_categories")
public class PhotoSpotCategory {
    @GeneratedValue(strategy = AUTO)
    @Id
    Long id;

    @NonNull
    String name;
}
