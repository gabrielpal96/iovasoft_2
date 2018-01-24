package softuniblog.demo.bindingModel;

import org.springframework.web.multipart.MultipartFile;
import softuniblog.demo.entity.Picture;

import javax.validation.constraints.NotNull;
import java.io.IOException;

import static org.codehaus.groovy.tools.shell.util.Logger.io;

public class ArticleBindingModel    {
    @NotNull
    private String title;
    @NotNull
    private String content;

    @NotNull
    private MultipartFile[] pictures;

    public MultipartFile[] getPictures() {
        return pictures;
    }

    public void setPictures(MultipartFile[] pictures) {
        this.pictures = pictures;
    }


    private Integer categoryId;
    private String tagString;

    private Integer modelPhoneId;

    private Integer price;

    private String city;
    private String gsm;
    private Integer cnt;


    private MultipartFile coverPhoto;

    public Integer getModelPhoneId() {
        return modelPhoneId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setModelPhoneId(Integer modelPhoneId) {
        this.modelPhoneId = modelPhoneId;
    }

    public String getTagString() {
        return tagString;
    }

    public void setTagString(String tagString) {
        this.tagString = tagString;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getTitle() {
        return title;
    }


    public String getContent() {
        return content;
    }

    public MultipartFile getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(MultipartFile coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }
}
