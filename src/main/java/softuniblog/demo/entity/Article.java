package softuniblog.demo.entity;

import softuniblog.demo.bindingModel.ArticleBindingModel;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.util.*;

@Entity
@Table(name = "articles")
public class Article {
    // implements Comparator<Article>
    private Integer id;
    private String title;
    private String content;
    private User author;
    private Category category;
    private ModelPhone modelPhone;
    private Integer cnt;
    private Integer price;
    private String city;

    private Set<Tag> tags;

    private Set<Picture> pictures;
    private String coverPhoto;
    private String gsm;


    public Article(String title, String content, User author, Category category, ModelPhone modelPhone, Integer price
    ,String city,String gsm,  HashSet<Tag>tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.category=category;
        this.modelPhone=modelPhone;
        this.price=price;
        this.city=city;
        this.cnt=0;
        this.gsm=gsm;
        this.tags=tags;
        this.pictures=new HashSet<>();
    }
    @ManyToMany()
    @JoinColumn(table = "articles_tags")
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    @ManyToOne()
    @JoinColumn(nullable = false,name = "modelPhoneId")
    public ModelPhone getModelPhone() {
        return modelPhone;
    }

    public Article() { }
    @OneToMany(mappedBy = "article")
    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    @Column(nullable=false)
    public String getTitle() {
        return title;
    }
    @Column(columnDefinition = "text",nullable = false)
    public String getContent() {
        return content;
    }
    @ManyToOne()
    @JoinColumn(nullable = false,name = "authorId")
    public User getAuthor() {
        return author;
    }
    @Transient
    public String getSummary(){
        return this.getContent().substring(0,this.getContent().length()/2);
    }
    @Column(name = "price",nullable = false)
    public Integer getPrice() {
        return price;
    }
    @Column(name = "city",nullable = false)
    public String getCity() {
        return city;
    }
    @Column(name = "gsm",nullable = false)
    public String getGsm() {
        return gsm;
    }
    @Column(name = "cnt",nullable = false)
    public Integer getCnt() {
        return cnt;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(columnDefinition = "text")
    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
    public void setModelPhone(ModelPhone modelPhone) {
        this.modelPhone = modelPhone;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    @ManyToOne()
    @JoinColumn(nullable = false,name = "categoryId")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void addPicture(Picture picture){
        this.pictures.add(picture);
    }
//***********************GSM*********************


    public void setGsm(String gsm) {
        this.gsm = gsm;
    }


//    public int compareTo(Article compares) {
//        int compareage=((Article)compares).getId();
//        /* For Ascending order*/
//        return this.id-compareage;
//
//    }
}

