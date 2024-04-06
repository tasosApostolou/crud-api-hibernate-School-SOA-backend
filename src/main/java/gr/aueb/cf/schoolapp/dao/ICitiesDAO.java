package gr.aueb.cf.schoolapp.dao;



import gr.aueb.cf.schoolapp.model.Cities;

import java.util.List;

public interface ICitiesDAO {
    Cities insert(Cities city);
    Cities update(Cities city);
    void delete(Long id);
    List<Cities> getByCity(String city);
    Cities getById(Long id);

}
