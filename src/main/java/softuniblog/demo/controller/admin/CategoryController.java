package softuniblog.demo.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;
import softuniblog.demo.bindingModel.CategoryBindingModel;
import softuniblog.demo.entity.Article;
import softuniblog.demo.entity.Category;
import softuniblog.demo.entity.ModelPhone;
import softuniblog.demo.repository.ArticleRepository;
import softuniblog.demo.repository.CategoryRepository;
import softuniblog.demo.repository.ModelPhoneRepository;

import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ModelPhoneRepository modelPhoneRepository;

    @GetMapping("/")
    public String list(Model model){
        List<Category> categories=this.categoryRepository.findAll();
        categories=categories.stream()
                .sorted(Comparator.comparingInt(Category::getId))
                .collect(Collectors.toList());
        List<ModelPhone> modelPhones=this.modelPhoneRepository.findAll();
        model.addAttribute("modelPhone",modelPhones);
        model.addAttribute("categories",categories);
        model.addAttribute("view","admin/category/list");

        return "base-layout";
    }
    @GetMapping("/create")
    public String create(Model model){
        List<ModelPhone> modelPhones=this.modelPhoneRepository.findAll();
        model.addAttribute("modelPhone",modelPhones);
        model.addAttribute("view","admin/category/create");

        return "base-layout";
    }
    @PostMapping("/create")
    public String createProcess(CategoryBindingModel categoryBindingModel){
        if(StringUtils.isEmpty(categoryBindingModel.getName())){
            return "redirect:/admin/categories/create";
        }

        ModelPhone modelPhone=this.modelPhoneRepository.findOne(categoryBindingModel.getModelPhoneId());
        Category category=new Category(categoryBindingModel.getName(),modelPhone);
        this.categoryRepository.saveAndFlush(category);
        return "redirect:/admin/categories/";
    }
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id){
        if (!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }
        Category category=this.categoryRepository.findOne(id);
        model.addAttribute("category",category);
        model.addAttribute("view","admin/category/edit");
        return "base-layout";
    }
    @PostMapping("/edit/{id}")
    public String editProces(@PathVariable Integer id,
                             CategoryBindingModel categoryBindingModel){
        if (!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }
        Category category=this.categoryRepository.findOne(id);
        category.setName(categoryBindingModel.getName());
        this.categoryRepository.saveAndFlush(category);
        return "redirect:/admin/categories/";
    }
    @GetMapping("/delete/{id}")
    public String delete(Model model,@PathVariable Integer id){
        if (!this.categoryRepository.exists(id)){
            return "redirect:/admin/categories/";
        }
        Category category=this.categoryRepository.findOne(id);
        model.addAttribute("category",category);
        model.addAttribute("view","admin/category/delete");
        return "base-layout";
    }
    @PostMapping("/delete/{id}")
    public String deleteProcess(@PathVariable Integer id){
        if (!this.categoryRepository.exists(id)){
            return "redirect:/admin/categoris";
        }
        Category category=this.categoryRepository.findOne(id);
        for (Article article:category.getArticles()){
            this.articleRepository.delete(article);
        }
        this.categoryRepository.delete(category);
        return "redirect:/admin/categories/";
    }


}
