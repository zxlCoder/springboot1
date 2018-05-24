package boot1.controller;

import org.beetl.sql.core.SQLManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import boot1.beetlsql.SqlKit;
import boot1.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeetlSqlControllerTest {
	@Autowired
	private SQLManager dao ;

	@Before
	public void before() {
		SqlKit.dao(dao);
	}
	
	@Test
	public void testAdd() {
		Person person = new Person();
		person.setAge(17);
		person.setName("小小");
	//	dao.insert(person); //全值插入
		dao.insertTemplate(person); //忽略为null值或者为空值的属性
	}
	
	@Test
	public void testDelete() {
		Person person = new Person();
		person.setId(1);
		dao.deleteObject(person);
	//	dao.deleteById(Person.class, 1);
	}
	
	@Test
	public void testUpdate() {
		Person person = new Person();
		person.setId(1);
		person.setAge(19);
	//	dao.updateById(person);
		dao.updateTemplateById(person); //只有不为null的属性参与更新
		//	dao.deleteById(Person.class, 1);
	}
	
	@Test
	public void testGet() {
		dao.unique(Person.class, 1);  //如果对象不存在，则会抛出一个Runtime异常
	//	dao.single(Person.class, 1);  //如果对象不存在，返回null
	}
	
	@Test
	public void testAdd2() {
		int a=1;
		int b=a;
		new Person().setId(2).setAge(12).setName("天天").save();
	}
}
