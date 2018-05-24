package boot1.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import boot1.model.User;

@RestController
@RequestMapping("error")
public class ErrorController {

	@RequestMapping("/500")
	@ResponseBody
	public User test() {
		User user = new User();

		user.setId(1);
		user.setUsername("jack");
		user.setPassword("jack123");
		user.setBirthday(new Date());

		// 模拟异常
		int i = 1 / 0;

		return user;
	}
}