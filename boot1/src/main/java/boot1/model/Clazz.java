package boot1.model;

import java.util.List;

import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.annotatoin.Tail;

import com.alibaba.fastjson.annotation.JSONField;

import boot1.beetlsql.ActiveRecord;
import boot1.beetlsql.Model;
import lombok.Data;
import net.bytebuddy.asm.Advice.This;

@Data
@lombok.experimental.Accessors(chain = true) // 链式编程
//@FieldDefaults(level = AccessLevel.PRIVATE)  // 属性默认都是private
//@Table(name = "t_Person")                     // 实体类与表映射
//@Tail
public class Clazz extends Model<Clazz>{
	
	public static final Clazz dao = new Clazz();
	private Integer id;
	private String name;
	private String desc;
	
	//变相的orm
	//使fastjson序列化时忽略此方法
	@JSONField(serialize=false)
	public List<Student> getStudents(){
		//return Student.dao.query().andEq("clazzId", this.id).select();
		return Student.dao.findList(new Student().setClazzId(this.id));
	}
}
