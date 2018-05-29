package boot1.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.kit.StringKit;
import org.beetl.sql.core.query.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import boot1.beetlsql.MyQuery;
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
		person.setId(1L); // 设置id也不生效
		person.setAge(17);
		person.setName("小小");
	//	dao.insert(person); //全值插入
		// dao.insertTemplate(person); //忽略为null值或者为空值的属性
	}
	
	// 更新
	@Test
	public void testUpdate() {
		Person person = new Person();
		person.setId(1L);
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
		// Person.of().setName("天天");
		new Person().setId(1L).setAge(12).setName("天天").save(); // 设置id也不生效
	}

	// AR更新
	@Test
	public void testUpdate_AR() {
		new Person().setId(2L).setAge(14).setName("天天2").update();
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
		List<Student> list = Student.dao.myQuery().andLike("s.name", "天").andLike("s.name", null).select("select s.*,c.name clazzName,c.desc clazzDesc from student s join clazz c on s.clazzId = c.id ");
		System.out.println(list);
		for (Student student : list) {
			System.out.println(student.get("clazzname"));  //字段名变成了小写
		}
	}
	
	//自定义多表动态查询，Query强转成MyQuery
	@Test
	public void testManyQuery3() {
		//设置条件
		MyQuery<Student> myQuery = (MyQuery<Student>) Student.dao.myQuery().andLike("s.name", "天").andLike("s.name", null).andNotEq("s.name", "good");
		//进行查询
		List<Student> list = myQuery.select("select s.*,c.name clazzName,c.desc clazzDesc from student s join clazz c on s.clazzId = c.id ");
		System.out.println(list);
		for (Student student : list) {
			System.out.println(student.get("clazzname"));  //字段名变成了小写
		}
	}
	
	//自定义多表动态查询，Query强转成MyQuery
	@Test
	public void testManyQuery4() {
		String sql = "select s.*,c.name clazzName,c.desc clazzDesc from student s "
								+ "join clazz c on s.clazzId = c.id ";
		//设置条件
		MyQuery<Student> myQuery = (MyQuery<Student>) Student.dao.myQuery().andLike("s.name", "天").andLike("s.name", null).andNotEq("s.name", "good");
		//进行查询
		List<Student> list = myQuery.select(sql);
		//System.out.println(list);
		for (Student student : list) {
			System.out.println(student.toString()+" "+student.get("clazzname"));  //字段名变成了小写
		}
	}
	
	//orm查询
	@Test
	public void testOrm() {
		List<Student> list = Student.dao.myQuery().select();
		for (Student student : list) {
			System.out.println(student.geClazz());
		}
		//System.out.println(list);
	}

	// orm查询
	// getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
	// getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段。
	// 同样类似的还有getConstructors()和getDeclaredConstructors()、getMethods()和getDeclaredMethods()，这两者分别表示获取某个类的方法、构造函数。
	@Test
	public void testClass() {
		Class c = Student.class;
		Annotation[] list = c.getAnnotations();
		for (Annotation annotation : list) {
			System.out.println(annotation.annotationType().getName());
		}
		Field[] filds = c.getFields();
		for (Field field : filds) {
			System.out.println(field.getName());
		}
		Field[] fild2s = c.getDeclaredFields();
		for (Field field : fild2s) {
			System.out.println(field.getName());
		}
		Method[] methods = c.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method.getName());
		}
		// System.out.println(list);
	}

	@After
	public void after() {
		//System.out.println(dao.all(Person.class));
	}
}
