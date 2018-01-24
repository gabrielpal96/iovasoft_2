package softuniblog.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "picture")
public class Picture {
    private Integer id;
    private String data;
    private Article article;

    public Picture(String data, Article article) {
        this.data = data;
        this.article = article;
    }
    public Picture(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "Data",columnDefinition = "text")
    public String getData() {
        return data;
    }

    @ManyToOne()
    @JoinColumn(name = "Article_Picture")
    public Article getArticle() {
        return article;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
