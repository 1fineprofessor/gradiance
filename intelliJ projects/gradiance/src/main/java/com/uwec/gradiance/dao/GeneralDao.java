package com.uwec.gradiance.dao;

import com.uwec.gradiance.database.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class GeneralDao {

    @PersistenceContext
    private EntityManager em;

    public <T> T save(T entity) {
        if (getId(entity) == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }

    public <T> Optional<T> findById(Class<T> type, Object id) {
        return Optional.ofNullable(em.find(type, id));
    }

    public Optional<Users> findByStudentId(String studentId) {
        return em.createQuery("SELECT u FROM Users u WHERE u.studentId = :studentId", Users.class)
                .setParameter("studentId", studentId)
                .getResultStream()
                .findFirst();
    }

    public <T> void delete(T entity) {
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }

    public <T> List<T> findAll(Class<T> type) {
        String q = "SELECT e FROM " + type.getSimpleName() + " e";
        return em.createQuery(q, type).getResultList();
    }

    // Use email as unique user identifier now
    public Optional<Users> findUserByEmail(String email) {
        TypedQuery<Users> q = em.createQuery(
                "SELECT u FROM Users u WHERE u.email = :email", Users.class);
        q.setParameter("email", email);
        return q.getResultStream().findFirst();
    }

    public boolean existsByStudentId(String studentId) {
        TypedQuery<Long> q = em.createQuery(
                "SELECT COUNT(u) FROM Users u WHERE u.studentId = :studentId", Long.class);
        q.setParameter("studentId", studentId);
        return q.getSingleResult() > 0;
    }

    public boolean existsByEmail(String email) {
        return findUserByEmail(email).isPresent();
    }

    // Helper to pull out the @Id value via JPA metamodel (or use a shared interface)
    private Object getId(Object entity) {
        return em.getEntityManagerFactory()
                .getPersistenceUnitUtil()
                .getIdentifier(entity);
    }
}
