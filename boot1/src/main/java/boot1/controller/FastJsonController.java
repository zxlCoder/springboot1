package boot1.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import boot1.javamail.JavaMailComponent;
import boot1.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(value = "FastJson测试", tags = { "测试接口" })
@CrossOrigin(origins = "http://localhost:8088") // 细粒度跨域设置
@Controller
@RequestMapping("fastjson")
public class FastJsonController {
	@Autowired
	private JavaMailComponent javaMailComponent;

	@RequestMapping("/test")
    @ResponseBody
    public User test() {
        User user = new User();
        user.setId(1);
        user.setUsername("jack");
        user.setPassword("jack123");
        user.setBirthday(new Date());
        return user;
    }

	@ApiOperation("获取用户信息")
	@ApiImplicitParam(name = "name", value = "用户名", dataType = "string", paramType = "query")
	@GetMapping("/test/{name}")
	@ResponseBody
	public User test(@PathVariable("name") String name) {
		User user = new User();
		user.setId(1);
		user.setUsername(name);
		user.setPassword("jack123");
		user.setBirthday(new Date());
		return user;
	}

	@RequestMapping("/sendemail")
	@ResponseBody
	public User test2() {
		User user = new User();
		user.setId(1);
		user.setUsername("jack");
		user.setPassword("jack123");
		user.setBirthday(new Date());
		this.javaMailComponent.sendMail("1004175116@qq.com");
		return user;
	}
}
