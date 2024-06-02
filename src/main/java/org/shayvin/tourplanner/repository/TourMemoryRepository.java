package org.shayvin.tourplanner.repository;

import org.shayvin.tourplanner.entity.Tour;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;

public class TourMemoryRepository implements TourRepository {

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public TourMemoryRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("tour");
        entityManager = entityManagerFactory.createEntityManager();
    }

    private void beginTransaction() {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
    }

    private void commitTransaction() {
        EntityTransaction transaction = entityManager.getTransaction();
        if (transaction.isActive()) {
            transaction.commit();
        }
    }

    // Returns all tours in repository
    @Override
    public List<Tour> findAll() {
        List<Tour> tours;
        beginTransaction();
        try {
            tours = entityManager.createQuery("FROM Tour", Tour.class).getResultList();
        } finally {
            commitTransaction();
        }
        return tours;
    }

    // Adds new tour to repository
    @Override
    public Tour save(Tour entity) {
        beginTransaction();
        try {
            // It ensures that the entity is reattached to the current persistence context before persisting it again. Needed for editing tours.
            entity = entityManager.merge(entity);
        } finally {
            commitTransaction();
        }
        return entity;
    }

    // Returns one tour if it exists in the repository
    @Override
    public Optional<Tour> findByTourName(String tourToFind) {
        Optional<Tour> tour;
        beginTransaction();
        try {
            tour = entityManager.createQuery("FROM Tour WHERE tourName = :tourName", Tour.class)
                    .setParameter("tourName", tourToFind)
                    .getResultStream()
                    .findFirst();
        } finally {
            commitTransaction();
        }
        return tour;
    }

    // Deletes tour from repository
    @Override
    public void removeTour(String tourToRemove) {
        beginTransaction();
        try {
            Tour tour = entityManager.createQuery("FROM Tour WHERE tourName = :tourName", Tour.class)
                    .setParameter("tourName", tourToRemove)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            if (tour != null) {
                entityManager.remove(tour);
            }
        } finally {
            commitTransaction();
        }
    }

    public void close() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
