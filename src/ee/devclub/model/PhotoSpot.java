package ee.devclub.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.FetchType.EAGER;
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
