package org.shayvin.tourplanner.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourMemoryRepository implements TourRepository {

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public TourMemoryRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("tour");
        entityManager = entityManagerFactory.createEntityManager();
    }


    // Returns all tours in repository
    @Override
    public List<Tour> findAll() {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        CriteriaQuery<Tour> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.createQuery(all).getResultList();
        }

    }

    // Adds new tour to repository
    @Override
    public Tour save(Tour entity) {

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        }

        return entity;
    }

    // Update current tour
    @Override
    public Tour update(Tour entity) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        }
        return entity;
    }

    // Returns one tour if it exists in the repository
    @Override
    public Optional<Tour> findByName(String tourToFind) {
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);

        Predicate termPredicate = criteriaBuilder.equal(root.get("name"), tourToFind);
        criteriaQuery.where(termPredicate);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Tour tour = entityManager.createQuery(criteriaQuery).getSingleResult();
            tour.setTourLogList(getTourLogListFromDB(tour.getId()));
            return Optional.of(tour);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    // Deletes tour from repository
    @Override
    public void removeTour(UUID tourToRemove) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Tour tour = entityManager.find(Tour.class, tourToRemove);
            entityManager.remove(tour);
            transaction.commit();
        }
    }

    public List<TourLog> getTourLogListFromDB(UUID tourId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<TourLog> criteriaQuery = criteriaBuilder.createQuery(TourLog.class);
            Root<TourLog> root = criteriaQuery.from(TourLog.class);
            criteriaQuery.where(criteriaBuilder.equal(root.get("tour").get("id"), tourId));

            List<TourLog> tourLogList = entityManager.createQuery(criteriaQuery).getResultList();
            if (tourLogList.isEmpty()) {
                return null;
            }
            return tourLogList;
        } finally {
            entityManager.close();
        }
    }

}
