package org.shayvin.tourplanner.repository;

import org.shayvin.tourplanner.entity.TourLog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class TourLogRepository {

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public TourLogRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("tourlog");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void save(TourLog tourLog) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(tourLog);
        transaction.commit();
    }

    public List<TourLog> findByTourName(String tourName) {
        /*
        return entityManager.createQuery("SELECT t FROM TourLog t WHERE t.tourName = :tourName", TourLog.class)
                .setParameter("tourName", tourName)
                .getResultList();

         */
        return new ArrayList<TourLog>();
    }

    public void remove(TourLog tourLog) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(tourLog);
        transaction.commit();
    }

    public void edit(TourLog tourLog) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(tourLog);
        transaction.commit();
    }

    public void close() {
        entityManager.close();
        entityManagerFactory.close();
    }
}
