package softuniblog.demo.bindingModel;

import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.StringUtils;
import softuniblog.demo.entity.Category;

public class CategoryBindingModel {
    private String name;

    private Integer modelPhoneId;

    public Integer getModelPhoneId() {
        return modelPhoneId;
    }

    public void setModelPhoneId(Integer modelPhoneId) {
        this.modelPhoneId = modelPhoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
