package ee.devclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.EAGER;
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

    @NonNull @NotNull
    @Size(min = 5, max = 100)
    String name;

    @NonNull @NotNull
    @Size(max=2000)
    String description;

    @Embedded
    @NonNull @NotNull
    @Valid
    Location location;

    //TODO: make non-nullable, how to set the value actually?
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_id", /*nullable = false,*/ updatable = false,
            foreignKey = @ForeignKey(name = "fk_photospots_owner")
    )
    User owner;

    //TODO: ManyToMany is an issue for REST paradigm
    @JsonIgnore
    @ManyToMany(fetch = EAGER)
    @JoinTable(name = "photospot_category_membership",
            foreignKey = @ForeignKey(name = "fk_photospot_category"),
            inverseForeignKey = @ForeignKey(name = "fk_category_photospot")
    )
    List<PhotoSpotCategory> categories;

    //TODO: date from Java 8
}
