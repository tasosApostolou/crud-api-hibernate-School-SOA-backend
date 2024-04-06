package gr.aueb.cf.schoolapp.dto.usersDTO;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserUpdateDTO extends AbstractEntity {

    private String username;
    private String password;
    private String role;

    public UserUpdateDTO(Long id,String username, String password, String role) {
        this.setId(id);
        this.username = username;
        this.password = password;
        this.role = role;
    }
}