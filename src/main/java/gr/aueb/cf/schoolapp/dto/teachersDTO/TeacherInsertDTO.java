package gr.aueb.cf.schoolapp.dto.teachersDTO;

import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.model.User;
import jakarta.validation.constraints.NotBlank;
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
public class TeacherInsertDTO {


    @NotNull
    @Size(min = 5)
    private String ssn;
//
    @NotNull
    @Size(min = 2, max = 255)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 255)
    private String lastname;

    private User user;

    private Speciality speciality;

}
