package boot1.model;

import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.annotatoin.Tail;

import boot1.beetlsql.ActiveRecord;
import boot1.beetlsql.Model;
import lombok.Data;

@Data
@lombok.experimental.Accessors(chain = true) // 链式编程
//@FieldDefaults(level = AccessLevel.PRIVATE)  // 属性默认都是private
//@Table(name = "t_Person")                     // 实体类与表映射
//@Tail
public class Student extends Model<Student>{
	
	public static final Student dao = new Student();
	private Integer id;
	private String name;
	private Integer age;
	private Integer classId;
}
