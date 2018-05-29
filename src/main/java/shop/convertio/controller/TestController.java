package shop.convertio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import shop.convertio.model.User;
import shop.convertio.request.RepeatReadServletRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class TestController {

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public User test(HttpServletRequest request){
        ObjectMapper mapper = new ObjectMapper();
        User user = null;
        try {

            user = mapper.readValue(request.getInputStream(),User.class);

            System.out.println(user);

            User user1 = new User();
            user1.setAge("12");
            user1.setId("hello");
            user1.setName("nihao");
            user1.setSex("girl");


            System.out.println(mapper.writeValueAsString(user1));


            //Thread.sleep(1000 * 10);

            return  user;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
