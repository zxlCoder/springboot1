package boot1.model;

import boot1.beetlsql.ActiveRecord;
import lombok.Data;

@Data
@lombok.experimental.Accessors(chain = true) // 链式编程
//@FieldDefaults(level = AccessLevel.PRIVATE)  // 属性默认都是private
//@Table(name = "t_Person")                     // 实体类与表映射
public class Person implements ActiveRecord{
	private Integer id;
	private String name;
	private Integer age;
}
