//package pl.edu.agh.productivitypal.repository;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import pl.edu.agh.productivitypal.enums.Difficulty;
//import pl.edu.agh.productivitypal.enums.Likeliness;
//import pl.edu.agh.productivitypal.model.Task;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataJpaTest
//class TaskRepositoryTest {
//
//    @Autowired
//    private TaskRepository testedRepository;
//
//    @Test
//    void itShouldReturnChildTaskWithGivenParentId() {
//        // given
//        Task parentTask = new Task(1L, "Buy new skirt", "Buy new skirt for prom", 5, Difficulty.HARD, Likeliness.DISLIKE, LocalDate.of(2019,12,12), 2L, 0L, false, false, false, null);
//        Task childTask = new Task(2L, "Buy new shoes", "Buy new shoes for prom", 5, Difficulty.HARD, Likeliness.DISLIKE, LocalDate.of(2019,12,12), 2L, 1L, false, false, false, parentTask.getId());
//
//        testedRepository.save(parentTask);
//        testedRepository.save(childTask);
//
//        // when
//
//        List<Task> tasks = testedRepository.findAllByParentId(parentTask.getId());
//
//        // then
//
//        assertThat(tasks.size()).isEqualTo(1);
//        assertThat(tasks.get(0).getId()).isEqualTo(childTask.getId());
//    }
//
//    @Test
//    void itShouldReturnEmptyListWhenNoSubtasksWithGivenParentIdExists() {
//        // given
//
//        Long parentId = 1L;
//
//        // when
//
//        List<Task> tasks = testedRepository.findAllByParentId(parentId);
//
//        // then
//
//        assertThat(tasks.size()).isEqualTo(0);
//    }
//
//}