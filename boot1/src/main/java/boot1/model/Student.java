package boot1.model;

import org.beetl.sql.core.orm.OrmCondition;
import org.beetl.sql.core.orm.OrmQuery;

import com.alibaba.fastjson.annotation.JSONField;

import boot1.beetlsql.Model;
import lombok.Data;
import lombok.experimental.Accessors;
import net.bytebuddy.asm.Advice.This;

@Data
@Accessors(chain = true) // 链式编程
//@FieldDefaults(level = AccessLevel.PRIVATE)  // 属性默认都是private
//@Table(name = "t_Person")                     // 实体类与表映射
//@Tail
/*@OrmQuery(
	    value={
	        @OrmCondition(target=Clazz.class,attr="clazzId",targetAttr="id",type=OrmQuery.Type.ONE,lazy=false),
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
	@JSONField(serialize=false)
	public Clazz getClazz(){
	//	return Clazz.dao.query().andEq("id", this.clazzId).select();
		return Clazz.dao.findById(this.clazzId);
	}
}
