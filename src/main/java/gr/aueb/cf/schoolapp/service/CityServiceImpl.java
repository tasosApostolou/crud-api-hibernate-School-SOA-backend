package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.ICitiesDAO;
import gr.aueb.cf.schoolapp.dto.citiesDTO.CityInsertDTO;
import gr.aueb.cf.schoolapp.dto.citiesDTO.CityUpdateDTO;
import gr.aueb.cf.schoolapp.model.Cities;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Provider
@ApplicationScoped
public class CityServiceImpl implements ICityService{
    private static final Logger logger = LoggerFactory.getLogger(CityServiceImpl.class);
    @Inject
    ICitiesDAO dao;
    @Override
    public Cities insert(CityInsertDTO dto) throws Exception {
        Cities city;
        try {
            JPAHelper.beginTransaction();
            city = new Cities(null,dto.getCity());
            city = dao.insert(city);
            if (city.getId() == null) throw new Exception("Error in insert");
            JPAHelper.commitTransaction();
            logger.info("City: "+city.getCity()+" iserted succesfully");
        }catch (Exception e){
            JPAHelper.rollbackTransaction();
            logger.warn("Insert teacher error. Rollback Transaction");
            throw e;
        }finally {
            JPAHelper.closeEntityManager();
        }
        return city;
    }

    @Override
    public Cities update(CityUpdateDTO dto) throws EntityNotFoundException {
        Cities cityToUpdate;
        Cities existingCity = null;
        try {
            JPAHelper.beginTransaction();
            Optional.ofNullable(dao.getById(dto.getId())).orElseThrow(() -> new EntityNotFoundException(Cities.class,dto.getId()));
            cityToUpdate = new Cities(dto.getId(), dto.getCity());
            existingCity = dao.update(cityToUpdate);
            JPAHelper.commitTransaction();
            logger.info("City "+ existingCity.getCity() + " was updated succesfully");
        }catch (EntityNotFoundException e){
            JPAHelper.rollbackTransaction();
            logger.warn("Error in update city");
            throw e;
        }finally {
            JPAHelper.closeEntityManager();
        }
        return existingCity;
    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
       try{
           JPAHelper.beginTransaction();
           Optional.ofNullable(dao.getById(id)).orElseThrow(() -> new EntityNotFoundException(Cities.class,id));
           dao.delete(id);
           JPAHelper.commitTransaction();
           logger.info("City deleted succesfully");
       }catch (EntityNotFoundException e){
           JPAHelper.rollbackTransaction();
           logger.warn("Error in deleting city. Rollback");
       }finally {
           JPAHelper.closeEntityManager();
       }


    }

    @Override
    public Cities getById(Long id) throws EntityNotFoundException {
        Cities city;
        try {
            JPAHelper.beginTransaction();
            city = dao.getById(id);
            city = Optional.ofNullable(dao.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(List.class, id));
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.info("City with id: " + id + " not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return city;
    }



    @Override
    public List<Cities> getByCity(String city) throws EntityNotFoundException {
        List<Cities> cities;
        try {
            JPAHelper.beginTransaction();
            cities = dao.getByCity(city);
            if (cities.isEmpty()) {
                throw new EntityNotFoundException(List.class, 0L);
            }

            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.info("Cities not found by city name");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return cities;

    }
}

