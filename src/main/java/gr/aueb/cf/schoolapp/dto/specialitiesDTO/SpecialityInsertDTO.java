package gr.aueb.cf.schoolapp.dto.specialitiesDTO;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecialityInsertDTO{
    @NotNull
    private String speciality;

}
