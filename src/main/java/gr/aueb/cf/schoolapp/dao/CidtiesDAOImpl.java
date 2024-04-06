package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.model.Cities;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
@Provider
@ApplicationScoped
public class CidtiesDAOImpl implements ICitiesDAO{



    @Override
    public Cities insert(Cities city) {
        EntityManager em = JPAHelper.getEntityManager();
        em.persist(city);
        return city;
    }

    @Override
    public Cities update(Cities city) {
        JPAHelper.getEntityManager().merge(city);
        return city;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JPAHelper.getEntityManager();
        Cities cityToDelete = em.find(Cities.class,id);
        em.remove(cityToDelete);
    }

    @Override
    public List<Cities> getByCity(String city) {
        EntityManager em = JPAHelper.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Cities> queryCities = builder.createQuery(Cities.class);
        Root<Cities> root = queryCities.from(Cities.class);

        ParameterExpression<String> cityParam = builder.parameter(String.class);
        queryCities.select(root).where(builder.like(root.get(city),cityParam));

        return em
                .createQuery(queryCities)
                .setParameter(cityParam,city + "%")
                .getResultList();
    }

    @Override
    public Cities getById(Long id) {
        return null;
    }
}
