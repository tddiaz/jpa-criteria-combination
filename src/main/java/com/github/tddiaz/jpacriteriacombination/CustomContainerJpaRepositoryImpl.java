package com.github.tddiaz.jpacriteriacombination;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CustomContainerJpaRepositoryImpl implements CustomContainerJpaRepository {

    private final EntityManager entityManager;

    @Override
    public List<Container> findAllByCombinationOfNumberAndMoveTypeAndStatus(List<Container> containers) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Container> criteriaQuery = criteriaBuilder.createQuery(Container.class);

        Root<Container> root = criteriaQuery.from(Container.class);
        criteriaQuery.select(root);

        // build predicates
        // select * from Container c where (c.number=? AND c.moveType=? AND c.status=?) OR (c.number=? AND c.moveType=? AND c.status=?) OR (c.number=? AND c.moveType=? AND c.status=?)
        {
            List<Predicate> mainPredicates = new ArrayList<>();

            containers.forEach(container -> {
                List<Predicate> subPredicates = new ArrayList<>();
                subPredicates.add(criteriaBuilder.equal(root.get("number"), container.getNumber()));
                subPredicates.add(criteriaBuilder.equal(root.get("moveType"), container.getMoveType()));
                subPredicates.add(criteriaBuilder.equal(root.get("status"), container.getStatus()));

                Predicate subPredicate = criteriaBuilder.conjunction();
                subPredicate.getExpressions().addAll(subPredicates);

                mainPredicates.add(subPredicate);
            });

            Predicate predicate = criteriaBuilder.disjunction();
            predicate.getExpressions().addAll(mainPredicates);

            criteriaQuery.where(predicate);
        }

        TypedQuery<Container> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
