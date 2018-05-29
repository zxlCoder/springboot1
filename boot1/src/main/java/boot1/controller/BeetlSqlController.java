package boot1.controller;

import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import boot1.model.Person;
import boot1.model.Student;

@Controller
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
	
	// json渲染clazz会被放进tails中，获取要用student.tails.clazz
	@RequestMapping("/testOrm")
	@ResponseBody
	public JSONObject testOrm() {
		// Person person = new Person().findById(2); 疑问：不知道jfinal为什么不提倡这样用
		Student student = Student.dao.findById(1);
		student.set("clazz", student.geClazz());
		JSONObject json = new JSONObject();
		json.put("data", student);
		System.out.println(json.toString());
		return json;
	}

	// ${student.clazz.name}
	// ${student.clazzName}
	// 页面渲染可直接获取
	@RequestMapping("/testOrm2")
	public String testOrm2(Map<String, Object> map) {
		// Person person = new Person().findById(2); 疑问：不知道jfinal为什么不提倡这样用
		Student student = Student.dao.findById(1);
		student.set("clazz", student.geClazz());
		student.set("clazzName", student.geClazz().getName());
		map.put("student", student);
		return "test2";
	}
}
