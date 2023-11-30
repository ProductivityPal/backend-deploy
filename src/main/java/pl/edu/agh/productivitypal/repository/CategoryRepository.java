package pl.edu.agh.productivitypal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.productivitypal.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByDefaultNameAndAppUserId(String name, Integer appUserId);
}
