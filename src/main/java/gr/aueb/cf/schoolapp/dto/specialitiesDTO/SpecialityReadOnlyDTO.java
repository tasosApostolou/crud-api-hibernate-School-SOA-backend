package gr.aueb.cf.schoolapp.dto.specialitiesDTO;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import gr.aueb.cf.schoolapp.model.Teacher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpecialityReadOnlyDTO extends AbstractEntity {
    private String speciality;
    private Set<Teacher> teachers = new HashSet<>();

    public SpecialityReadOnlyDTO(Long id, String speciality) {
        super.setId(id);
        this.speciality = speciality;

    }
}
