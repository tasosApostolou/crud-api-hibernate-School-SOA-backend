package gr.aueb.cf.schoolapp.dto.citiesDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CityInsertDTO {
    @NotNull
    private String city;
}
