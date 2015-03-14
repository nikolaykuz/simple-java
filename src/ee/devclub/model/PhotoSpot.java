package ee.devclub.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
@NoArgsConstructor
@RequiredArgsConstructor
public class PhotoSpot implements Serializable {
    @GeneratedValue(strategy = AUTO)
    @Id Long id;

    @NonNull String name;

    @NonNull String description;

    @NonNull Location location;
}
