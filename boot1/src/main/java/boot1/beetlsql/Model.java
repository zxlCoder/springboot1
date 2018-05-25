package boot1.beetlsql;

import static boot1.beetlsql.SqlKit.getDao;

import java.io.Serializable;
import java.util.List;

/**
 * 对应单个表数据库操作的接口 <BR>
 * 实现接口, 可以在对象上直接使用 save, update, delete 方法 <BR>
 * create time : 2017-05-20 11:18
 *
 * @author luoyizhu@gmail.com
 */
// @SuppressWarnings("unchecked")
public abstract class Model<M extends Model> implements Serializable {
    /**
     * <pre>
     * 保存对象, 这个方法执行完后,不会给对象赋值 主键.
     * 如果想保存后对象拥有主键的业务,请使用 saveDo 方法保存对象
     * </pre>
     *
     * @return 保存成功
     */
	/*
	 * default boolean saveDo() { return getDao().insertTemplate(this) > 0; }
	 */

    /**
     * 保存对象, 并给对象的主键赋值
     *
     * @return true 保存成功
     */
	public boolean save() {
		return getDao().insertTemplate(this, true) > 0; // 会返回id，会忽略插入的id而使用自增id
    }

    /**
     * 更新对象
     *
     * @return true 表示更新成功
     */
	public boolean update() {
        return getDao().updateTemplateById(this) > 0;
    }

    /**
     * 删除对象
     *
     * @return true 表示删除成功
     */
	/*
	 * default boolean delete() { return getDao().deleteObject(this) > 0; }
	 */

	/**
	 * 删除对象
	 *
	 * @return true 表示删除成功
	 */
	/*
	 * default boolean delete() { return getDao().deleteObject(this) > 0; }
	 */

	/**
	 * 根据id删除对象
	 * @return true 表示删除成功
	 */
	public boolean deleteById(Object id) {
		return getDao().deleteById(this.getClass(), id) > 0;
    }

	/**
	 * 根据id查询对象
	 */
	public M findById(Object id) {
		return (M) getDao().unique(this.getClass(), id); // 如果对象不存在，则会抛出一个Runtime异常
		// return (T) getDao().single(this.getClass(), id); //如果对象不存在，返回null
	}

	/**
	 * 查询全部对象
	 */
	public List<M> getAll() {
		return (List<M>) getDao().all(this.getClass());
	}

}
