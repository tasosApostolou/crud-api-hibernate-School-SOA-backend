package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.ext.Provider;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Provider
@ApplicationScoped
public class SpecialityDAOImpl implements ISpecialityDAO{
    @Override
    public Speciality insert(Speciality speciality) {
        EntityManager em = getEntityManager();
        em.persist(speciality);
        return speciality;
    }

    @Override
    public Speciality update(Speciality speciality) {
        EntityManager em = getEntityManager();
        em.merge(speciality);
        return speciality;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        Speciality specToDelete =  em.find(Speciality.class,id);
        em.remove(specToDelete);
    }

    @Override
    public Speciality getById(Long id) {
        return JPAHelper.getEntityManager().find(Speciality.class,id);
    }

    @Override
    public List<Speciality> getBySpeciality(String speciality) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Speciality> selectQuery = builder.createQuery(Speciality.class);
        Root<Speciality> root = selectQuery.from(Speciality.class);
        ParameterExpression<String> specialityParam = builder.parameter(String.class);
        selectQuery.select(root).where(builder.like(root.get("speciality"),specialityParam));

        return getEntityManager()
                .createQuery(selectQuery)
                .setParameter(specialityParam,speciality + "%")
                .getResultList();
    }



    private EntityManager getEntityManager() {
        return JPAHelper.getEntityManager();
    }

}
