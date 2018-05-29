package boot1.model;

import boot1.beetlsql.Model;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) // 链式编程
//@FieldDefaults(level = AccessLevel.PRIVATE)  // 属性默认都是private
//@Table(name = "t_Person")                     // 实体类与表映射
//@Tail
/*@OrmQuery(
	    value={
	        @OrmCondition(target=Clazz.class,attr="clazzId",targetAttr="id",type=OrmQuery.Type.ONE,lazy=true),
	        //@OrmCondition(target=ProductOrder.class,attr="id",targetAttr="userId" ,type=OrmQuery.Type.MANY),
	       // @OrmCondition(target=Role.class,attr="id",targetAttr="userId" ,sqlId="user.selectRole",type=OrmQuery.Type.MANY)
	    }
)*/
public class Student extends Model<Student>{
	
	public static final Student dao = new Student();
	private Integer id;
	private String name;
	private Integer age;
	private Integer clazzId;
	
	//变相的orm
	//使fastjson序列化时忽略此方法
	//@JSONField(serialize=false)
	/*public Clazz getClazz(){
		Clazz clazz = (Clazz) get("clazz");
		if(clazz == null){
			clazz = Clazz.dao.findById(this.clazzId);
			set("clazz", clazz);
		}
		return clazz;
	}*/
	
	//@JSONField(serialize=false)
	//使用geClass而不用getClass可以免去序列化，get开头方法在转json的时候会被调用
	//ge可以看成是generate或get的缩写
	public Clazz geClazz(){
		return Clazz.dao.findById(this.clazzId);
	}
	
}
