package ee.devclub.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Access(FIELD)
@Data
@EqualsAndHashCode(exclude={"id"})
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "photospots")
public class PhotoSpot {
    @GeneratedValue(strategy = AUTO)
    @Id
    Long id;

    @NonNull
    String name;

    @NonNull
    String description;

    @Embedded
    @NonNull
    Location location;

    //TODO: NonNull annotations?

    //TODO: nullable
    //TODO: name foreign key properly
    @ManyToOne
    @JoinColumn(name = "owner_id", /*nullable = false,*/ updatable = false, referencedColumnName = "id")
    User owner;

    @ManyToMany
    @JoinTable(name = "photospot_categories_relation")
    List<PhotoSpotCategory> categories;

    //TODO: date from Java 8
}
