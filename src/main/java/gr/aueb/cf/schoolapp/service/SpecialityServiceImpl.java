package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.ISpecialityDAO;
import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityInsertDTO;
import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Provider
@ApplicationScoped
public class SpecialityServiceImpl implements ISpecialityService{
    private static final Logger logger = LoggerFactory.getLogger(SpecialityServiceImpl.class);
    @Inject
    ISpecialityDAO specialityDAO;

    @Override
    public Speciality insertSpeciality(SpecialityInsertDTO specialityDTO) throws Exception {
Speciality speciality;
   try {
       JPAHelper.beginTransaction();
       speciality = Mapper.mapToSpeciality(specialityDTO);
       speciality = specialityDAO.insert(speciality);
       if (speciality.getId() == null) {
           throw new Exception("Insert Error");
       }
       JPAHelper.commitTransaction();
       logger.info("Speciality with id: " + speciality.getId());
   }catch (Exception e){
       JPAHelper.rollbackTransaction();
       logger.warn("Insert speciality rollback error");
       throw e;
   }finally {
       JPAHelper.closeEntityManager();
   }
        return speciality;
    }

    @Override
    public Speciality updateSpeciality(SpecialityUpdateDTO specialityDTO) throws EntityNotFoundException {
        Speciality specialityToUpdate;
        Speciality existingSpeciality = null;

        try {
            JPAHelper.beginTransaction();

            Optional.ofNullable(specialityDAO.getById(specialityDTO.getId()))
                    .orElseThrow(() -> new EntityNotFoundException(Speciality.class, specialityDTO.getId()));

            specialityToUpdate = Mapper.mapToSpeciality(specialityDTO);
            existingSpeciality = specialityDAO.update(specialityToUpdate);
            JPAHelper.commitTransaction();
            logger.info("Speciality with id: " + specialityToUpdate.getId() + " updated");
        } catch (EntityNotFoundException e) {
            JPAHelper.rollbackTransaction();
            logger.warn("Update rollback - Entity not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return existingSpeciality;
    }
    @Override
    public void deleteSpeciality(Long id) throws EntityNotFoundException {
        try{
            JPAHelper.beginTransaction();
            Optional.ofNullable(specialityDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Speciality.class,id));
        }catch (EntityNotFoundException e){
            JPAHelper.rollbackTransaction();
            logger.warn("Delete teacher rollback");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }

    }

    @Override
    public Speciality getSpecialityById(Long id) throws EntityNotFoundException {
        Speciality speciality;
        try{
            JPAHelper.beginTransaction();
            speciality = specialityDAO.getById(id);
            speciality = Optional.ofNullable(specialityDAO.getById(id))
                    .orElseThrow(() -> new EntityNotFoundException(Speciality.class,id));
            JPAHelper.commitTransaction();
        } catch (EntityNotFoundException e){
            JPAHelper.rollbackTransaction();
            logger.info("Speciality with id: " + id + " not found");
            throw e;
        } finally {
            JPAHelper.closeEntityManager();
        }
        return speciality;
    }
}
