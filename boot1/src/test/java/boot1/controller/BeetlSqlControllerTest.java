package boot1.controller;

import org.beetl.sql.core.SQLManager;
import org.junit.After;
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
		SqlKit.setDao(dao);
	}
	
	// 新增
	@Test
	public void testAdd() {
		Person person = new Person();
		person.setId(1); // 设置id也不生效
		person.setAge(17);
		person.setName("小小");
	//	dao.insert(person); //全值插入
		// dao.insertTemplate(person); //忽略为null值或者为空值的属性
	}
	
	// 更新
	@Test
	public void testUpdate() {
		Person person = new Person();
		person.setId(1);
		person.setAge(19);
	//	dao.updateById(person);
		dao.updateTemplateById(person); //只有不为null的属性参与更新
	}
	
	// 查询
	@Test
	public void testGet() {
		dao.unique(Person.class, 3); // 如果对象不存在，则会抛出一个Runtime异常
	//	dao.single(Person.class, 1);  //如果对象不存在，返回null
	}
	
	// 删除
	@Test
	public void testDelete() {
		dao.deleteById(Person.class, 1);
	}

	// AR新增
	@Test
	public void testAdd_AR() {
		new Person().setId(1).setAge(12).setName("天天").save(); // 设置id也不生效
	}

	// AR更新
	@Test
	public void testUpdate_AR() {
		new Person().setId(2).setAge(14).setName("天天2").update();
	}

	// AR查询
	@Test
	public void testGet_AR() {
		// Person person = new Person().findById(2); 疑问：不知道jfinal为什么不提倡这样用
		Person person = Person.dao.findById(3);
		System.out.println(person);
	}

	// AR删除
	@Test
	public void testDelete_AR() {
		// new Person().deleteById(2);
		Person.dao.deleteById(2);
	}

	// AR查询全部
	@Test
	public void testGetAll_AR() {
		// System.out.println(new Person().findAll());
		System.out.println(Person.dao.findAll());
	}

	// 通用查询
	@Test
	public void testCommonQuery() {
		//System.out.println(Person.dao.query().select("id", "name"));
		//System.out.println(Person.dao.query().andEq("id", 4).select("id", "name"));
		// System.out.println(Person.dao.query().desc("id").select("id",
		// "name"));
		// beetl默认1的第一个数据
		System.out.println(Person.dao.query().desc("id").limit(1, 1).select("id", "name"));
	}

	@After
	public void after() {
		System.out.println(dao.all(Person.class));
	}
}
