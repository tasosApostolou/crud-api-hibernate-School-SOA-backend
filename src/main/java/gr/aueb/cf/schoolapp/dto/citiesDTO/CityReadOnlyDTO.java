package gr.aueb.cf.schoolapp.dto.citiesDTO;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityReadOnlyDTO extends AbstractEntity {

    public String city;

    public CityReadOnlyDTO(Long id, String city) {
        this.setId(id);
        this.city = city;
    }
}
