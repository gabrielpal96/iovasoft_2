package softuniblog.demo.entity;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {
    private Integer id;
    private String name;
    private Set<Article> articles;

    public Tag() {
    }
    public Tag(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    @Column (unique = true,nullable = false)
    public String getName() {
        return name;
    }
    @ManyToMany(mappedBy = "tags")
    public Set<Article> getArticles() {
        return articles;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}
