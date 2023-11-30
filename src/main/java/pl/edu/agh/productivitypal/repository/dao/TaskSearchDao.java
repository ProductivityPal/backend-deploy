package pl.edu.agh.productivitypal.repository.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.edu.agh.productivitypal.model.Task;
import pl.edu.agh.productivitypal.request.TaskRequest;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskSearchDao {
    private final EntityManager entityManager;

    public List<Task> findAllByCriteria(TaskRequest request){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        List<Predicate> predicates = new ArrayList<>();

        Root<Task> root = criteriaQuery.from(Task.class);

        if (request.getPriority() > 0){
            Predicate priorityPredicate = criteriaBuilder.like(
                    root.get("priority").as(String.class), "" + request.getPriority()
            );
            predicates.add(priorityPredicate);
        }

        if (request.getDifficulty() != null){
            Predicate difficultyPredicate = criteriaBuilder.like(
                    root.get("difficulty").as(String.class),  request.getDifficulty().name()
            );
            predicates.add(difficultyPredicate);
        }

        if (request.getCategory() != null){
            Predicate categoryPredicate = criteriaBuilder.like(
                    root.get("category"), "%" + request.getCategory() + "%"
            );
            predicates.add(categoryPredicate);
        }

        if (request.getLikeliness() != null){
            Predicate likelinessPredicate = criteriaBuilder.like(
                    root.get("likeliness").as(String.class), request.getLikeliness().name()
            );
            predicates.add(likelinessPredicate);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Task> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }
}
