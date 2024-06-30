package org.shayvin.tourplanner.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.*;
import org.shayvin.tourplanner.entity.Tour;
import org.shayvin.tourplanner.entity.TourLog;

import java.util.ArrayList;
import java.util.List;

public class FullTextSearchRepository {

    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;

    private enum TransportType {
        Walking,
        Car,
        Cycling
    }

    public FullTextSearchRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("tour");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Tour> findFitting(String searchTerm){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = criteriaBuilder.createQuery(Tour.class);
        Root<Tour> tourRoot = criteriaQuery.from(Tour.class);
        Join<Tour, TourLog> logJoin = tourRoot.join("tourLogList", JoinType.LEFT);

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(tourRoot.get("name")),
                "%" + searchTerm.toLowerCase() + "%"
        ));
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(tourRoot.get("start")),
                "%" + searchTerm.toLowerCase() + "%"
        ));
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(tourRoot.get("destination")),
                "%" + searchTerm.toLowerCase() + "%"
        ));
        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(tourRoot.get("description")),
                "%" + searchTerm.toLowerCase() + "%"
        ));
//        predicates.add(criteriaBuilder.like(
//                criteriaBuilder.lower(logJoin.get("information")),
//                "%" + searchTerm.toLowerCase() + "%"
//        ));
        Expression<String> transportTypeExpression = criteriaBuilder.<String>selectCase()
                .when(criteriaBuilder.equal(tourRoot.get("type"), TransportType.Walking), TransportType.Walking.toString())
                .when(criteriaBuilder.equal(tourRoot.get("type"), TransportType.Car), TransportType.Car.toString())
                .when(criteriaBuilder.equal(tourRoot.get("type"), TransportType.Cycling), TransportType.Cycling.toString())
                .otherwise("UNKNOWN");

        predicates.add(criteriaBuilder.like(
                criteriaBuilder.lower(transportTypeExpression),
                "%" + searchTerm.toLowerCase() + "%"
        ));

        // search for difficulty in logs
//        Expression<String> difficultyExpression = criteriaBuilder.<String>selectCase()
//                .when(criteriaBuilder.equal(logJoin.get("difficulty"), 0), Difficulty.EASY.toString())
//                .when(criteriaBuilder.equal(logJoin.get("difficulty"), 1), Difficulty.MEDIUM.toString())
//                .when(criteriaBuilder.equal(logJoin.get("difficulty"), 2), Difficulty.HARD.toString())
//                .otherwise("UNKNOWN");

//        predicates.add(criteriaBuilder.like(
//                criteriaBuilder.lower(difficultyExpression),
//                "%" + searchTerm.toLowerCase() + "%"
//        ));

        criteriaQuery.select(tourRoot).distinct(true).where(criteriaBuilder.or(predicates.toArray(new Predicate[0])));

        List<Tour> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        return resultList;
    }
}
