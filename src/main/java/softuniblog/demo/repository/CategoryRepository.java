package softuniblog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniblog.demo.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
}
