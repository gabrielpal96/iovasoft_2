package softuniblog.demo.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softuniblog.demo.repository.TagRepository;

@RestController
public class TagRestController {

    private final TagRepository tagRepository;

    @Autowired
    public TagRestController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("/checkTag")
    public boolean checkIfExist(@RequestParam("tagName") String tagName){
        if (this.tagRepository.findByName(tagName) == null){
            return false;
        }
        return true;
    }
}
