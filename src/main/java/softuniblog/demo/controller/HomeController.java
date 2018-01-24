package softuniblog.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import softuniblog.demo.bindingModel.ArticleBindingModel;
import softuniblog.demo.entity.Article;
import softuniblog.demo.entity.Category;
import softuniblog.demo.entity.ModelPhone;
import softuniblog.demo.repository.ArticleRepository;
import softuniblog.demo.repository.CategoryRepository;
import softuniblog.demo.repository.ModelPhoneRepository;

import javax.jws.WebParam;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.*;

@Controller
public class HomeController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelPhoneRepository modelPhoneRepository;
    private ArticleBindingModel  articleBindingModel;

    @GetMapping("/")
    public String modelPhoneIndex(Model model){
        List<ModelPhone> modelPhones=this.modelPhoneRepository.findAll();
        Collections.sort(modelPhones, new Comparator<ModelPhone>() {
            @Override
            public int compare(ModelPhone o1, ModelPhone o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        List<Article> articles=this.articleRepository.findAll();
        Collections.sort(articles, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
               return o2.getId().compareTo(o1.getId());
            }
        });
        //Collections.reverse(articles);
        List<Article> articles1=this.articleRepository.findAll();
        Collections.sort(articles1, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {
                return o1.getCnt().compareTo(o2.getCnt());
            }
        });
        List<Article> tmp=new ArrayList<>();
//        if (articles.size()>5) {
//            for (int i = 0; i < 5; i++) {
//            tmp.add(articles.get(i)) ;
//            }
//            articles.clear();
//            articles=tmp;
//        }
        tmp.clear();
        if (articles1.size()>5) {
            for (int i = 0; i < 5; i++) {
                tmp.add(articles1.get(i)) ;
            }
            articles1.clear();
            articles1=tmp;
        }

        Collections.reverse(articles1);
        model.addAttribute("view","admin/modelPhone/index");
        model.addAttribute("modelPhone",modelPhones);
        model.addAttribute("article",articles);
        model.addAttribute("article1",articles1);
        return "base-layout";
    }
    @GetMapping("/index/{id}")
    public String listArticles(Model model, @PathVariable Integer id){
     //   ModelPhone categories=this.categoryRepository.findOne(id);
        ModelPhone modelPhone=this.modelPhoneRepository.findOne(id);
        ArrayList<Category> categories=new ArrayList<Category>();
        categories.addAll(modelPhone.getCategories());
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category o1, Category o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        Set<Article>articles=new HashSet<>();
        int br=categories.size();
        for (int i = 0; i <br ; i++) {

            articles.addAll(categories.get(i).getArticles());
        }

        model.addAttribute("view","home/index");
        model.addAttribute("categories",categories);
        model.addAttribute("article",articles);
        return "base-layout";
    }
    @RequestMapping("/error/403")
    public String accessDenied(Model model){
        model.addAttribute("view","error/403");
        return "base-layout";
    }
    @RequestMapping("/error/401")
    public String tagError(Model model){
        model.addAttribute("view","error/401");
        return "base-layout";
    }
    @RequestMapping("/error/402")
    public String categoryError(Model model){
        model.addAttribute("view","error/402");
        return "base-layout";
    }

    @GetMapping("/category/{id}")
    public String ArticlesList(Model model, @PathVariable Integer id){
        if (!this.categoryRepository.exists(id)){
            return "redirect:/";
        }
        Category category=this.categoryRepository.findOne(id);

        ArrayList<Article> articles1=new ArrayList<Article>();
        articles1.addAll(category.getArticles());
        Collections.sort(articles1, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {

        return o1.getId().compareTo(o2.getId());
            }
        });
        Collections.reverse(articles1);


       Set<Category>c=category.getModelPhone().getCategories();
        model.addAttribute("articles", articles1);
        model.addAttribute("category",c);
        model.addAttribute("view","home/list-articles");
        return "base-layout";
    }


    @GetMapping("/category/A-z/{id}")
    public String listArticlesAz(Model model, @PathVariable Integer id){
        if (!this.categoryRepository.exists(id)){
            return "redirect:/";
        }
        Category category=this.categoryRepository.findOne(id);
       // Set<Article> articles=category.getArticles();
        ArrayList<Article> articles1=new ArrayList<Article>();
        articles1.addAll(category.getArticles());
        Collections.sort(articles1, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {

                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }
        });

        model.addAttribute("articles", articles1);
        model.addAttribute("category",category);
        model.addAttribute("view","home/list-articles");
        return "base-layout";
    }


    @GetMapping("/category/price/{id}")
    public String listArticlesPrice(Model model, @PathVariable Integer id){
        if (!this.categoryRepository.exists(id)){
            return "redirect:/";
        }
        Category category=this.categoryRepository.findOne(id);
        // Set<Article> articles=category.getArticles();
        ArrayList<Article> articles1=new ArrayList<Article>();
        articles1.addAll(category.getArticles());
        Collections.sort(articles1, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {

                return o1.getPrice().compareTo(o2.getPrice());
            }
        });

        model.addAttribute("articles", articles1);
        model.addAttribute("category",category);
        model.addAttribute("view","home/list-articles");
        return "base-layout";
    }

    @GetMapping("/category/priceUp/{id}")
    public String listArticlesPriceUp(Model model, @PathVariable Integer id){
        if (!this.categoryRepository.exists(id)){
            return "redirect:/";
        }
        Category category=this.categoryRepository.findOne(id);
        // Set<Article> articles=category.getArticles();
        ArrayList<Article> articles1=new ArrayList<Article>();
        articles1.addAll(category.getArticles());
        Collections.sort(articles1, new Comparator<Article>() {
            @Override
            public int compare(Article o1, Article o2) {

                return o2.getPrice().compareTo(o1.getPrice());
            }
        });

        model.addAttribute("articles", articles1);
        model.addAttribute("category",category);
        model.addAttribute("view","home/list-articles");
        return "base-layout";
    }
}

