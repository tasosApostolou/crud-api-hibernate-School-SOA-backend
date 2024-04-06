package gr.aueb.cf.schoolapp.dto.studentsDTO;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import gr.aueb.cf.schoolapp.model.Cities;
import gr.aueb.cf.schoolapp.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentUpdateDTO extends AbstractEntity {
    private String firstname;
    private String lastname;
    private String gender;
    private String birthday;
    private Cities city;
    private User user;


    public StudentUpdateDTO(Long id, String firstname, String lastname, String gender, String birthday, Cities city, User user) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthday = birthday;
        this.city = city;
        this.user = user;
    }
}
