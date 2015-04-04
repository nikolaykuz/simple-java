package ee.devclub.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Access(FIELD)
@Data
@EqualsAndHashCode(exclude={"id"})
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @GeneratedValue(strategy = AUTO)
    @Id
    Long id;

    @NonNull
    String name;

    @OneToMany(fetch = LAZY, mappedBy = "id")
    List<PhotoSpot> createdPhotoSpots;
}
