package org.shayvin.tourplanner.repository;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;
import org.shayvin.tourplanner.view.TableButtonView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TourMemoryRepository implements TourRepository {

    private static final Logger logger = LogManager.getLogger(TourMemoryRepository.class);

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    public TourMemoryRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("tour");
        entityManager = entityManagerFactory.createEntityManager();
    }


    // Returns all tours in repository
    @Override
    public List<Tour> findAll() {
        logger.info("Loading all tours.");
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);
        CriteriaQuery<Tour> all = criteriaQuery.select(root);

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            logger.info("Successfully loaded all tours.");
            return entityManager.createQuery(all).getResultList();
        }catch ( Exception e ) {
            logger.error(e);
        }

        return null;
    }

    // Adds new tour to repository
    @Override
    public Tour save(Tour entity) {
        logger.info("Saving tour: {}", entity.getName());
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            logger.info("Successfully saved tour: {}", entity.getName());
        }catch ( Exception e ) {
            logger.error(e);
        }

        return entity;
    }

    // Update current tour
    @Override
    public Tour update(Tour entity) {
        logger.info("Updating tour: {}", entity.getName());
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
            logger.info("Successfully updated tour: {}", entity.getName());
        }catch ( Exception e ) {
            logger.error(e);
        }
        return entity;
    }

    // Returns one tour if it exists in the repository
    @Override
    public Optional<Tour> findByName(String tourToFind) {
        logger.info("Finding tour by name: {}", tourToFind);
        CriteriaBuilder criteriaBuilder = entityManagerFactory.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> root = criteriaQuery.from(Tour.class);

        Predicate termPredicate = criteriaBuilder.equal(root.get("name"), tourToFind);
        criteriaQuery.where(termPredicate);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            Tour tour = entityManager.createQuery(criteriaQuery).getSingleResult();
            tour.setTourLogList(getTourLogListFromDB(tour.getId()));
            logger.info("Found tour: {}", tour.getName());
            return Optional.of(tour);
        } catch (NoResultException e) {
            logger.info("No tour found with name: {}", tourToFind);
            return Optional.empty();
        }
    }

    // Deletes tour from repository
    @Override
    public void removeTour(UUID tourToRemove) {
        logger.info("Removing tour: {}", tourToRemove);
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            Tour tour = entityManager.find(Tour.class, tourToRemove);
            entityManager.remove(tour);
            transaction.commit();
            logger.info("Successfully removed tour: {}", tour.getName());
        }catch ( Exception e ) {
            logger.error(e);
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
                logger.warn("No tourlogs found for tour with id: {}", tourId);
                return null;
            }
            logger.info("Found tourlogs: {}", tourLogList);
            return tourLogList;
        } finally {
            entityManager.close();
        }
    }

}
