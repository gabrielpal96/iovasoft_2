package softuniblog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniblog.demo.entity.Tag;

public interface TagRepository extends JpaRepository<Tag,Integer> {
    Tag findByName(String name);
}
