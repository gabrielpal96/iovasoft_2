package softuniblog.demo.controller;

import org.aspectj.apache.bcel.classfile.annotation.TypeAnnotationGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import softuniblog.demo.bindingModel.ArticleBindingModel;
import softuniblog.demo.bindingModel.CategoryBindingModel;
import softuniblog.demo.bindingModel.PictureBildingModel;
import softuniblog.demo.entity.*;
import softuniblog.demo.repository.*;

import javax.jws.WebParam;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Controller
public class ArticleController {
    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;


    private final CategoryRepository categoryRepository;

    private final TagRepository tagRepository;

    private final PictureRepository pictureRepository;

    private final ModelPhoneRepository modelPhoneRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository, UserRepository userRepository, CategoryRepository categoryRepository,ModelPhoneRepository modelPhoneRepository, TagRepository tagRepository, PictureRepository pictureRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelPhoneRepository=modelPhoneRepository;
        this.tagRepository = tagRepository;
        this.pictureRepository = pictureRepository;

    }

    private HashSet<Tag> findTagsFromString(String tagString) {//string to HasSet
        HashSet<Tag> tags = new HashSet<>();
        String[] tagNames = tagString.split(",\\s*");
        for (String tagName : tagNames) {
            Tag currentTag = this.tagRepository.findByName(tagName);
            if (currentTag == null) {
                currentTag = new Tag(tagName);
                this.tagRepository.saveAndFlush(currentTag);
            }
            tags.add(currentTag);
        }

        return tags;
    }

    @GetMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model) {
        List<Category> categories = this.categoryRepository.findAll();
        List<ModelPhone> modelPhone=this.modelPhoneRepository.findAll();
        List<Picture> pictures = this.pictureRepository.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("modelPhone", modelPhone);
        model.addAttribute("view", "article/create");
        model.addAttribute("picture", pictures);

        return "base-layout";

    }

    @PostMapping("/article/create")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(ArticleBindingModel articleBindingModel ) throws IOException {
       // CategoryBindingModel categoryBindingModel=new  CategoryBindingModel() ;
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User userEntity = this.userRepository.findByEmail(user.getUsername());

        Category category = this.categoryRepository.findOne(articleBindingModel
                .getCategoryId());

        String striing=articleBindingModel.getTitle();

        striing+=" "+articleBindingModel.getCity().toLowerCase();
        StringTokenizer stringTokenizer=new StringTokenizer(striing);
        striing="";

        while (stringTokenizer.hasMoreElements()) {
            striing+=stringTokenizer.nextToken()+",";
        }
        striing+=articleBindingModel.getTitle();
        HashSet<Tag> tags = this.findTagsFromString(striing);

        ModelPhone modelPhone=this.modelPhoneRepository.findOne(articleBindingModel.getModelPhoneId());

        if (category.getModelPhone().getName()!=modelPhone.getName()){
        return "redirect:/error/402";
        }
        Article articleEntity = new Article(articleBindingModel.getTitle(),
                articleBindingModel.getContent(), userEntity, category,modelPhone,articleBindingModel.getPrice(),
                articleBindingModel.getCity(),articleBindingModel.getGsm(),
                tags);

        String coverPhoto=Base64.getEncoder().encodeToString(articleBindingModel.getCoverPhoto().getBytes());

        Article article = this.articleRepository.saveAndFlush(articleEntity);
        article.setCoverPhoto(coverPhoto);
        if (articleBindingModel.getPictures() != null) {
            for (MultipartFile pictureFile : articleBindingModel.getPictures()) {

                String ancPicture = Base64.getEncoder().encodeToString(pictureFile.getBytes());
                Picture picture = new Picture(ancPicture, article);
                Picture pictureEntity = this.pictureRepository.saveAndFlush(picture);
                article.addPicture(pictureEntity);
            }
            this.articleRepository.saveAndFlush(article);
        }


        return "redirect:/";
    }

    @GetMapping("/article/{id}")
    public String details(Model model, @PathVariable Integer id) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }

        if (!(SecurityContextHolder.getContext().getAuthentication()
                instanceof AnonymousAuthenticationToken)) {
            UserDetails principal = (UserDetails) SecurityContextHolder
                    .getContext().getAuthentication().getPrincipal();
            User entityUser = this.userRepository.findByEmail(principal.getUsername());
            model.addAttribute("user", entityUser);
        }

        Article article = this.articleRepository.findOne(id);
        ArticleBindingModel articleBindingModel=new ArticleBindingModel();
        Integer tmp=article.getCnt();
        tmp++;
        article.setCnt(tmp);
        this.articleRepository.saveAndFlush(article);
        model.addAttribute("article", article);
        model.addAttribute("view", "article/details");
        return "base-layout";
    }

    //------------EDIT----------------
    @GetMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String edit(@PathVariable Integer id, Model model) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }
        Article article = this.articleRepository.findOne(id);
        if (!isUserAuthorOrAdmin(article)) {
            return "redirect:/articles/" + id;
        }
        List<Category> categories = this.categoryRepository.findAll();
        String tagString = article.getTags().stream()
                .map(Tag::getName).collect(Collectors.joining(", "));
        model.addAttribute("article", article);
        model.addAttribute("view", "article/edit");
        model.addAttribute("categories", categories);
        model.addAttribute("tags", tagString);
        return "base-layout";
    }

    @PostMapping("/article/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editProcess(@PathVariable Integer id, ArticleBindingModel articleBindingModel) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }
        Article article = this.articleRepository.findOne(id);
        if (!isUserAuthorOrAdmin(article)) {
            return "redirect:/articles/" + id;
        }
        Category category = this.categoryRepository.findOne(articleBindingModel
                .getCategoryId());
        HashSet<Tag> tags = this.findTagsFromString(articleBindingModel
                .getTagString());
        article.setCategory(category);
        article.setContent(articleBindingModel.getContent());
        article.setTitle(articleBindingModel.getTitle());
        article.setTags(tags);
        this.articleRepository.saveAndFlush(article);
        return "redirect:/article/" + article.getId();
    }

    @GetMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String delete(Model model, @PathVariable Integer id) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }
        Article article = this.articleRepository.findOne(id);
        if (!isUserAuthorOrAdmin(article)) {
            return "redirect:/articles/" + id;
        }
        model.addAttribute("article", article);
        model.addAttribute("view", "article/delete");
        return "base-layout";
    }


    @PostMapping("/article/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteProcess(@PathVariable Integer id) {
        if (!this.articleRepository.exists(id)) {
            return "redirect:/";
        }
        Article article = this.articleRepository.findOne(id);
        List<Picture> pictures=this.pictureRepository.findAllByArticleId(article.getId());
        if (!isUserAuthorOrAdmin(article)) {
            return "redirect:/articles/" + id;
        }

        this.pictureRepository.delete(pictures);
        this.articleRepository.delete(article);

        return "redirect:/";
    }


    private boolean isUserAuthorOrAdmin(Article article) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User userEntity = this.userRepository.findByEmail(user.getUsername());
        return userEntity.isAdmin() || userEntity.isAuthor(article);
    }


}
