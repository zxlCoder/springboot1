package boot1.beetlsql;

import static boot1.beetlsql.SqlKit.getDao;

import java.io.Serializable;
import java.util.List;

import org.beetl.sql.core.query.Query;

/**
 * 对应单个表数据库操作的接口 <BR>
 * 实现接口, 可以在对象上直接使用 save, update, delete 方法 <BR>
 * create time : 2017-05-20 11:18
 *
 * @author luoyizhu@gmail.com
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public interface ActiveRecord<M extends ActiveRecord> extends Serializable {

	// 在service中可以使用 private static final Blog dao = new Blog().dao();
	// 不用写Blog.dao这么长，实体类中也不用再写一个dao对象
	public default M dao() {
		return (M) this;
	}
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
	default boolean save() {
		return getDao().insertTemplate(this, true) > 0; // 会返回id，会忽略插入的id而使用自增id
    }

    /**
     * 更新对象
     *
     * @return true 表示更新成功
     */
    default boolean update() {
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
	default boolean deleteById(Object id) {
		return getDao().deleteById(this.getClass(), id) > 0;
    }

	/*
	 * default boolean delete(Object id) { return
	 * getDao().deleteById(this.getClass(), id) > 0; }
	 */

	/**
	 * 根据id查询对象
	 */
	default M findById(Object id) {
		return (M) getDao().unique(this.getClass(), id); // 如果对象不存在，则会抛出一个Runtime异常
		// return (T) getDao().single(this.getClass(), id); //如果对象不存在，返回null
	}

	/*
	 * default T get(Object id) { return (T) getDao().unique(this.getClass(),
	 * id); // 如果对象不存在，则会抛出一个Runtime异常 }
	 */

	/**
	 * 查询全部对象 不知道为什么getAll和getList和getAlls方法在对象转json的时候会被一直调用，改成findAll all等
	 * 原因：fastjson转换对象的时候会调用get开头的方法
	 */
	default List<M> findAll() {
		return (List<M>) getDao().all(this.getClass());
	}

	/* 查询总数 */
	default long findAllCount() {
		return getDao().allCount(this.getClass());
	}

	/*----------------------------------上面是基本方法---------------------------------*/
	/* 魔板查询方法都是用=连接的,以object中非空和非日期类型为条件 */

	// 根据模板条件查询list
	default List<M> findList(M object) {
		return (List<M>) getDao().templateOne(object);
	}

	// 根据模板条件查询一个 findFirst
	default M findOne(M object) {
		return getDao().templateOne(object);
	}

	// 根据模板分页查询，没有orderby和limit
	default void findPage(M object, long start, long size) {
		getDao().template(object, start, size);
	}

	// 可用于分页的通用查询
	default Query<? extends ActiveRecord> query() {
		return getDao().query(this.getClass());
	}

	// List<User> list =
	// sql.query(User.class).andEq("name","hi").orderBy("create_date").select();

	default long findCount(M object) {
		return getDao().templateCount(object);
	}

	// void templatePage(PageQuery<T> query);
}
