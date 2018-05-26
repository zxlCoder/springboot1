package boot1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.ext.fn.StringUtil;
import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.kit.StringKit;
import org.beetl.sql.core.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import boot1.beetlsql.SqlKit;
import boot1.model.Person;
import boot1.model.Student;

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
		// System.out.println(Person.dao.query().desc("id").select("id","name"));
		// beetl默认1的第一个数据
		//System.out.println(Person.dao.query().desc("id").limit(0, 1).select("id", "name"));
		//System.out.println(Person.dao.find("select * from person where id = ?", new Person().setId(4)));
		//System.out.println(dao.execute("select * from person where id = #id#", Person.class, new Person().setId(2)));
		//System.out.println(dao.execute(new SQLReady("select * from person where id = ?", 2), Person.class));
		//System.out.println(dao.execute(new SQLReady("select * from person where id != ?", 100), Person.class, new PageQuery<>()));
		//System.out.println(Person.dao.findPage(new Person().setAge(19), 1, 10, "age desc,id desc"));
		/*List<Person> list = Person.dao.findPage(new Person().setAge(19), 1, 10, "age desc,id desc");
		for (Person person : list) {
			System.out.println(person);
		}*/
		//System.out.println(Person.dao.findOne(new Person().setId(2)));
		//System.out.println(Person.dao.findOne(new HashMap().put("id", 2))); 错误写法，put后返回的不是map
		//System.out.println(Person.dao.findList(new HashMap() {{put("id", "2");}}));
		//System.out.println(Person.dao.findList(null));
		
		Query<Person> query = Person.dao.query();
		query.andLike("name", "%天%");
		query.andNotEq("age", 100);
		System.out.println(query.getSql());
		System.out.println(query.select());
	};
	
	//动态查询
	@Test
	public void testQuery() {
		String name = "天";
		Integer age = 100;
		Query<Person> query = Person.dao.query();
		if(StringKit.isNotBlank(name)){
			query.andLike("name", name);
		}
		if(age!=null){
			query.andNotEq("age", age);
		}
		System.out.println(query.getSql());
		System.out.println(query.select());
	}
	
	//改进动态查询， andEq和andLike方法参数允许为空，like非空参数自动前后加百分号
	@Test
	public void testMyQuery() {
		Query<Person> query = Person.dao.myQuery().andLike("name", "天").andLike("name", null);
		System.out.println(query.getSql());
		System.out.println(query.select());
	}
	
	//自定义多表动态查询
	@Test
	public void testManyQuery() {
		List<Person> list = Person.dao.myQuery().andLike("p.name", "天").andLike("name", null).select("select * from person p");
		System.out.println(list);
	}
	
	//自定义多表动态查询
	@Test
	public void testManyQuery2() {
		List<Student> list = Student.dao.myQuery().andLike("s.name", "天").andLike("s.name", null).select("select s.*,c.name className,c.desc classDesc from student s join class c on s.classId = c.id ");
		System.out.println(list);
		for (Student student : list) {
			System.out.println(student.get("classname"));  //字段名变成了小写
		}
	}

	@After
	public void after() {
		//System.out.println(dao.all(Person.class));
	}
}
