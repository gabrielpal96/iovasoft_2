package softuniblog.demo.bindingModel;

import org.springframework.web.multipart.MultipartFile;
import softuniblog.demo.entity.Article;

import javax.validation.constraints.NotNull;

public class PictureBildingModel {
    @NotNull
    private String data;
    @NotNull
    private Article article;


    public PictureBildingModel(){}
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}
