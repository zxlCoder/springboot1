package boot1.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {

	@RequestMapping("test")
    public String hello(Map<String,Object> map) {
        map.put("msg", "Hello Thymeleaf");
		return "hello2";
    }
}
