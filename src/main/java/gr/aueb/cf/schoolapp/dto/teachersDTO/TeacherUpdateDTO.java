package gr.aueb.cf.schoolapp.dto.teachersDTO;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherUpdateDTO extends AbstractEntity {
    String ssn;
    String firstname;
    String lastname;
    User user;
    Speciality speciality;

    public TeacherUpdateDTO(Long id,String ssn, String firstname, String lastname,User user, Speciality speciality) {
        super.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.user = user;
        this.speciality=speciality;
    }
}
