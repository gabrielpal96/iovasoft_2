package softuniblog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniblog.demo.entity.Picture;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PictureRepository extends JpaRepository<Picture,Integer> {
    List<Picture> findAllByArticleId(Integer articleId);
}
