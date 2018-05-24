package boot1.beetlsql;

import java.io.Serializable;

import static boot1.beetlsql.SqlKit.dao;

/**
 * 对应单个表数据库操作的接口 <BR>
 * 实现接口, 可以在对象上直接使用 save, update, delete 方法 <BR>
 * create time : 2017-05-20 11:18
 *
 * @author luoyizhu@gmail.com
 */
public interface ActiveRecord extends Serializable {

    /**
     * <pre>
     * 保存对象, 这个方法执行完后,不会给对象赋值 主键.
     * 如果想保存后对象拥有主键的业务,请使用 saveDo 方法保存对象
     * </pre>
     *
     * @return 保存成功
     */
    default boolean save() {
        return dao().insertTemplate(this) > 0;
    }

    /**
     * 保存对象, 并给对象的主键赋值
     *
     * @return true 保存成功
     */
    default boolean saveDo() {
        return dao().insertTemplate(this, true) > 0;
    }

    /**
     * 更新对象
     *
     * @return true 表示更新成功
     */
    default boolean update() {
        return dao().updateTemplateById(this) > 0;
    }

    /**
     * 删除对象
     *
     * @return true 表示删除成功
     */
    default boolean delete() {
        return dao().deleteObject(this) > 0;
    }
}
