package pl.edu.agh.productivitypal.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.edu.agh.productivitypal.model.AppUser;
import pl.edu.agh.productivitypal.model.Category;
import pl.edu.agh.productivitypal.repository.CategoryRepository;

@Slf4j
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void addDefaultCategories(AppUser user){
        Category c1 = Category.builder()
                .defaultName("beige")
                .appUser(user)
                .build();

        Category c2 = Category.builder()
                .defaultName("green")
                .appUser(user)
                .build();

        Category c3 = Category.builder()
                .defaultName("accent")
                .appUser(user)
                .build();

        Category c4 = Category.builder()
                .defaultName("grey")
                .appUser(user)
                .build();

        categoryRepository.save(c1);
        categoryRepository.save(c2);
        categoryRepository.save(c3);
        categoryRepository.save(c4);
    }


}
