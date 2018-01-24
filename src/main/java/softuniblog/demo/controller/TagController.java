package softuniblog.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuniblog.demo.bindingModel.ArticleBindingModel;
import softuniblog.demo.entity.Article;
import softuniblog.demo.entity.Category;
import softuniblog.demo.entity.Tag;
import softuniblog.demo.repository.TagRepository;

import javax.persistence.GeneratedValue;
import java.util.HashSet;

@Controller
public class TagController {

    @Autowired
    private TagRepository tagRepository;
    @GetMapping("/tag/{name}")
    public String articlesWithTag(Model model, @PathVariable String name){

        Tag tag=this.tagRepository.findByName(name);
        if (tag==null){
            return "redirect:/";
        }
        model.addAttribute("view","tag/articles");
        model.addAttribute("tag",tag);
        return "base-layout";
    }

}
