package boot1.beetlsql;

import static boot1.beetlsql.SqlKit.getDao;

import java.io.Serializable;
import java.util.List;

import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;

import boot1.model.Person;

/**
 * @author zxl
 * 针对单表查询的类
 * 增删改查  查询一个  查询多个  查询全部   查询分页    查询多个count  查询全部count
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class Model<M extends Model> extends TailBean implements Serializable {

	    private static final long serialVersionUID = 1L;

		public M dao() {
			return (M) this;
		}

		public boolean save() {
			return getDao().insertTemplate(this, true) > 0; // 会返回id，会忽略插入的id而使用自增id
	    }

		public boolean update() {
	        return getDao().updateTemplateById(this) > 0;
	    }


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
		 * 查询全部对象 不知道为什么getAll和getList和getAlls方法在对象转json的时候会被一直调用，改成findAll all等
		 * 原因：fastjson转换对象的时候会调用get开头的方法
		 */
		public List<M> findAll() {
			return (List<M>) getDao().all(this.getClass());
		}

		/* 查询总数 */
		public long findAllCount() {
			return getDao().allCount(this.getClass());
		}

		/*----------------------------------上面是基本方法---------------------------------*/
		/* 魔板查询方法都是用=连接的,以object中非空和非日期类型为条件 */

		// 根据模板条件查询一个 findFirst
		public M findOne(Object paras) {
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
		public List<M> findList(String orderBy) {
			return (List<M>) getDao().template(this.getClass(), null, -1, -1, orderBy);
		}
		
		
		public List<M> findList(Object paras, String orderBy) {
			return (List<M>) getDao().template(this.getClass(), paras, -1, -1, orderBy);
		}

		
		// 根据模板分页查询，没有orderby
		public List<M> findPage(long start, long size) {
			return (List<M>) getDao().template(this.getClass(), null, start, size, null);
		}
		
		public List<M> findPage(long start, long size, String orderBy) {
			return (List<M>) getDao().template(this.getClass(), null, start, size, orderBy);
		}
		
		public List<M> findPage(Object paras, long start, long size) {
			return (List<M>) getDao().template(this.getClass(), paras, start, size, null);
		}
		
		// 根据模板分页查询，有orderby
		public  List<M> findPage(Object paras, long start, long size, String orderBy) {
			return (List<M>) getDao().template(this.getClass(), paras, start, size, orderBy);
		}
		
		//分页 推荐用这个方法
		public PageQuery<M> findPage(PageQuery<M> pageQuery) {
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
		

		public long findCount(Object object) {
			return getDao().templateCount(object);
		}
		
		// 可用于分页的万能单表通用查询，可代替所有单表操作
		public Query<M> query() {
			return new Query(getDao(), this.getClass());
			//return new Query<M>(getDao(), (Class<M>) this.getClass());
			//return (Query<M>) getDao().query(this.getClass());
		}
		
		//  改进的万能单表通用查询   andEq和andLike方法参数允许为空，like非空参数自动前后加百分号
		//  支持多表关联查询
		public MyQuery<M> myQuery() {
			return new MyQuery(getDao(), this.getClass());
		}
		
		/*default List<? extends ActiveRecord> findByIdLoadColumns(Object id, String... columns) {
			return getDao().query(this.getClass()).andEq(idname, id).select(columns);
		}*/
		
		//通过sql查询
		public List<M> executeQuery(String sql, Object paras) {
			return (List<M>) getDao().execute(sql, this.getClass(), paras);
		}
		
		//通过sql 增删改  返回成功执行条数
		public int executeUpdate(String sql, Object paras) {
			return getDao().executeUpdate(sql, paras);
		}

}
