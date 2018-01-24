package softuniblog.demo.entity;

import groovy.lang.GrabExclude;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "modelPhone")
public class ModelPhone {
    private Integer id;
    private String name;
    private Set<Category> categories;

    public ModelPhone() {
    }

    public ModelPhone(String name) {
        this.name = name;
        this.categories=new HashSet<>();
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
    @OneToMany(mappedBy = "modelPhone")
    public Set<Category> getCategories() {
        return categories;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
