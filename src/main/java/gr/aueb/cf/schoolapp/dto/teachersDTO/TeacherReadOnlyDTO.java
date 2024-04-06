package gr.aueb.cf.schoolapp.dto.teachersDTO;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.model.User;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherReadOnlyDTO extends AbstractEntity {
    String ssn;
    String firstname;
    String lastname;
    User user;
    Speciality speciality;

    public TeacherReadOnlyDTO(Long id,String ssn, String firstname, String lastname,User user, Speciality speciality) {
        super.setId(id);
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.user = user;
        this.speciality=speciality;
    }


}
