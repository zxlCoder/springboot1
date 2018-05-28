package boot1.model;

import java.util.Date;

import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.annotatoin.LogicDelete;
import org.beetl.sql.core.annotatoin.Tail;
import org.terracotta.modules.ehcache.writebehind.operations.DeleteAllAsyncOperation;

import boot1.beetlsql.ActiveRecord;
import boot1.beetlsql.DataEntity;
import boot1.beetlsql.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) // 链式编程
//@FieldDefaults(level = AccessLevel.PRIVATE)  // 属性默认都是private
//@Table(name = "t_Person")                     // 实体类与表映射
//@Tail
//@NoArgsConstructor(staticName = "of")
@ToString(callSuper = true)
public class Person extends DataEntity<Person>{
	
	private static final long serialVersionUID = 1L;
	public static final Person dao = new Person();
	
	private String name;
	private Integer age;

}
