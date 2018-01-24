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
import softuniblog.demo.bindingModel.ModelPhoneBindingModel;
import softuniblog.demo.entity.Article;
import softuniblog.demo.entity.Category;
import softuniblog.demo.entity.ModelPhone;
import softuniblog.demo.repository.CategoryRepository;
import softuniblog.demo.repository.ModelPhoneRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/modelPhone")
public class ModelPhoneController {

    @Autowired
    private ModelPhoneRepository
    modelPhoneRepository;
    @Autowired
    private CategoryRepository
    categoryRepository;

    @GetMapping("/")
    public String list(Model model){
        List<ModelPhone> modelPhones=this.modelPhoneRepository.findAll();
        modelPhones=modelPhones.stream().sorted(Comparator.comparingInt(ModelPhone::getId))
                .collect(Collectors.toList());
        model.addAttribute("modelPhone",modelPhones);

        model.addAttribute("view","admin/modelPhone/list");
        return "base-layout";
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("view","admin/modelPhone/create");
        return "base-layout";
    }
    @PostMapping("/create")
    public String createProcess(ModelPhoneBindingModel modelPhoneBindingModel){
        if(StringUtils.isEmpty(modelPhoneBindingModel.getName())){
            return "redirect:/admin/modelPhone/create";
        }
        ModelPhone modelPhone=new ModelPhone(modelPhoneBindingModel.getName());
        this.modelPhoneRepository.saveAndFlush(modelPhone);
        return "redirect:/admin/modelPhone/";
    }
    @GetMapping("/createSql")
    public String createSql(ModelPhoneBindingModel modelPhoneBindingModel){
        System.out.println("TEST");
        String str =("Samsung Nokia LG HTC Sony Apple Huawei Lenovo Motorola");
        StringTokenizer st=new StringTokenizer(str);
        while (st.hasMoreElements()) {
            ModelPhone modelPhone=new ModelPhone(st.nextToken());
            this.modelPhoneRepository.saveAndFlush(modelPhone);
        }
        return "redirect:/admin/modelPhone/";
    }


    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id){
        if (!this.modelPhoneRepository.exists(id)){
            return "redirect:/admin/modelPhone/";
        }
        ModelPhone modelPhone=this.modelPhoneRepository.findOne(id);
        model.addAttribute("modelPhone",modelPhone);
        model.addAttribute("view","admin/modelPhone/edit");
        return "base-layout";
    }
    @PostMapping("/edit/{id}")
    public String editProces(@PathVariable Integer id,
                             ModelPhoneBindingModel modelPhoneBindingModel){
        if (!this.modelPhoneRepository.exists(id)){
            return "redirect:/admin/modelPhone/";
        }
        ModelPhone modelPhone=this.modelPhoneRepository.findOne(id);
        modelPhone.setName(modelPhoneBindingModel.getName());
        this.modelPhoneRepository.saveAndFlush(modelPhone);
        return "redirect:/admin/modelPhone/";
    }
}
