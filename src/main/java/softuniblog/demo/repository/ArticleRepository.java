package softuniblog.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import softuniblog.demo.entity.Article;

public interface ArticleRepository extends JpaRepository<Article,Integer>{
}
