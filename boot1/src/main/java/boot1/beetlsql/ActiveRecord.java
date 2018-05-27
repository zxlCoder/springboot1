package boot1.beetlsql;

import static boot1.beetlsql.SqlKit.getDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;
import org.springframework.jdbc.object.SqlQuery;

/**
 * @author zxl
 * 针对单表查询的类
 * 增删改查  查询一个  查询多个  查询全部   查询分页    查询多个count  查询全部count
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public interface ActiveRecord<M extends ActiveRecord> extends Serializable {

	// 在service中可以使用 private static final Blog dao = new Blog().dao();
	// 不用写Blog.dao这么长，实体类中也不用再写一个dao对象
	default M dao() {
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
		return getDao().insertTemplate(this, true) > 0; // 会返回id，会忽略插入的id而使用自增id,对于null值不做处理
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

	// 根据模板条件查询一个 findFirst
	default M findOne(Object paras) {
		int start = getDao().isOffsetStartZero() ? 0 : 1;
		List<M> list =  (List<M>) getDao().template(this.getClass(), paras, null, start, 1);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
	//	return (M) getDao().templateOne(model);
	}
	
	// 根据模板条件查询list
	default List<M> findList(String orderBy) {
		return (List<M>) getDao().template(this.getClass(), null, -1, -1, orderBy);
	}
	
	
	default List<M> findList(Object paras, String orderBy) {
		return (List<M>) getDao().template(this.getClass(), paras, -1, -1, orderBy);
	}


	
	// 根据模板分页查询，没有orderby
	default List<M> findPage(long start, long size) {
		return (List<M>) getDao().template(this.getClass(), null, start, size, null);
	}
	
	default List<M> findPage(long start, long size, String orderBy) {
		return (List<M>) getDao().template(this.getClass(), null, start, size, orderBy);
	}
	
	default List<M> findPage(Object paras, long start, long size) {
		return (List<M>) getDao().template(this.getClass(), paras, start, size, null);
	}
	
	// 根据模板分页查询，有orderby
	default  List<M> findPage(Object paras, long start, long size, String orderBy) {
		return (List<M>) getDao().template(this.getClass(), paras, start, size, orderBy);
	}
	
	//分页 推荐用这个方法
	default PageQuery<M> findPage(PageQuery<M> pageQuery) {
		long start = pageQuery.getPageNumber();
		long size = pageQuery.getPageSize();
		String orderBy = pageQuery.getOrderBy();
		Object paras = pageQuery.getParas();
		List list = getDao().template(this.getClass(), paras, start, size, orderBy);
		long count = list.size();
		if(list.size() > size){ //list的大小大于一页才有必要查询总数
			count = getDao().templateCount(paras);
		}
		pageQuery.setList(list);
		pageQuery.setTotalRow(count);
		return pageQuery;
	//	return getDao().template(this.getClass(), paras, start, size, orderBy);
	}
	

	// List<User> list =
	// sql.query(User.class).andEq("name","hi").orderBy("create_date").select();

	default long findCount(Object object) {
		return getDao().templateCount(object);
	}
	
	// 可用于分页的通用查询
	default Query<M> query() {
		return (Query<M>) getDao().query(this.getClass());
	}
	
	/*default List<? extends ActiveRecord> findByIdLoadColumns(Object id, String... columns) {
		return getDao().query(this.getClass()).andEq(idname, id).select(columns);
	}*/
	
	//通过sql查询
	default List<M> executeQuery(String sql, Object paras) {
		return (List<M>) getDao().execute(sql, this.getClass(), paras);
	}
	
	//通过sql 增删改  返回成功执行条数
	default int executeUpdate(String sql, Object paras) {
		return getDao().executeUpdate(sql, paras);
	}

	// void templatePage(PageQuery<T> query);
}
