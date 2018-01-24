package softuniblog.demo.entity;

import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.jws.WebParam;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    private Integer id;
    private String name;
    private Set<Article> articles;

    @NotNull
    private ModelPhone modelPhone;

    public Category() {
    }

    public Category(String name,ModelPhone modelPhone) {
        this.name = name;
        this.modelPhone=modelPhone;
        this.articles=new HashSet<>();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    @Column(nullable = false,unique = true)
    public String getName() {
        return name;
    }
    @OneToMany(mappedBy = "category")
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

    @ManyToOne
    @JoinColumn(nullable = false,name = "modelPhoneId")
    public ModelPhone getModelPhone() {
        return modelPhone;
    }

    public void setModelPhone(ModelPhone modelPhone) {
        this.modelPhone = modelPhone;
    }
}
