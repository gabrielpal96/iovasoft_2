package softuniblog.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import softuniblog.demo.entity.User;
import softuniblog.demo.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils.*;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

    @Autowired
    private UserRepository userRepository;



    @Override
    @RequestMapping(value = "public_profile", method = RequestMethod.GET)
    public String execute(Connection<?> connection) {

        User user = new User();
        user.setFullName(connection.getDisplayName());
        user.setPassword(randomAlphabetic(8));
        userRepository.save(user);
        return user.getFullName();
    }

}
