package boot1.controller;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import boot1.model.Person;

@RestController
@RequestMapping("beetlsql")
public class BeetlSqlController {
	@Autowired
	private SQLManager dao ;

	@RequestMapping("/testGet")
	@ResponseBody
	public Person testGet_AR() {
		// Person person = new Person().findById(3); 疑问：不知道jfinal为什么不提倡这样用
		Person person = Person.dao.findById(3);
		System.out.println(person);
		return person;
	}

	@RequestMapping("/testGet2")
	@ResponseBody
	public JSONObject testGet_AR2() {
		// Person person = new Person().findById(2); 疑问：不知道jfinal为什么不提倡这样用
		Person person = Person.dao.findById(3);
		System.out.println(person);
		JSONObject json = new JSONObject();
		json.put("msg", "hello");
		json.put("data", person);
		System.out.println(json.toString());
		return json;
	}
}
