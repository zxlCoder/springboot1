package boot1.beetlsql;

import static boot1.beetlsql.SqlKit.getDao;

import java.io.Serializable;
import java.util.List;

import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;

import boot1.model.Person;

/**
 * @author zxl
 * @说明  针对单表查询的类
 * 增删改查  查询一个  查询多个  查询多个count 查询全部   查询全部count 查询分页    
 * where方法是为了简写，可用其他方法代替，目前只有query系列方法支持查询指定列
 * findList和findPage系列方法是否有点多余，考虑全去掉(用myQuery代替)，使用object传参未必有助于代码的理解
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class Model<M extends Model> extends TailBean implements Serializable {

	    private static final long serialVersionUID = 1L;

		public M dao() {
			return (M) this;
		}

		/**
		 * @说明 保存对象，会返回id，会忽略插入的id而使用自增id,对于null值不做处理
		 */
		public boolean save() {
			return getDao().insertTemplate(this, true) > 0; 
	    }

		/**
	     * @说明 根据id更新，忽略空的属性
	     */
		public boolean update() {
	        return getDao().updateTemplateById(this) > 0;
	    }


		/**
		 * @说明 根据id删除对象
		 */
		public boolean deleteById(Object id) {
			return getDao().deleteById(this.getClass(), id) > 0;
	    }


		/**
		 * @说明 根据id查询对象，考虑用find代替
		 */
		public M findById(Object id) {
			//return (M) getDao().unique(this.getClass(), id); // 如果对象不存在，则会抛出一个Runtime异常
			return (M) getDao().single(this.getClass(), id); //如果对象不存在，返回null
		}

		/**
		 * @说明 
		 * 查询全部对象 不知道为什么getAll和getList和getAlls方法在对象转json的时候会被一直调用，改成findAll all等
		 * 原因：fastjson转换对象的时候会调用get开头的方法
		 */
		public List<M> findAll() {
			return (List<M>) getDao().all(this.getClass());
		}

		/**
		 * @说明 查询总数，考虑把名字换成allConut,加find是为了统一
		 */
		public long findAllCount() {
			return getDao().allCount(this.getClass());
		}

		/*----------------------------------上面是基本方法---------------------------------*/
		/* 魔板查询方法都是用=连接的,以object中非空和非日期类型为条件 */

		// 根据模板条件查询一个 findFirst
		
		/**
		 * @说明 根据模板条件查询一个，还有一个名字findFirst可考虑
		 */
		public M findOne(Object paras) {
			int start = getDao().isOffsetStartZero() ? 0 : 1;
			List<M> list =  (List<M>) getDao().template(this.getClass(), paras, null, start, 1);
			//List<M> list =  findList(null);  考虑用这方法替换，templateOne(model)底层用的上面这个方法
	        if (list.isEmpty()) {
	            return null;
	        } else {
	            return list.get(0);
	        }
		//	return (M) getDao().templateOne(model); 这方法用map传参返回的是map
		}
		
		// 根据模板条件查询list
		/**
		 * @说明 根据模板条件查询list
		 */
		public List<M> findList(Object paras) {
			return findList(paras, null);
		}
		
		/**
		 * @说明 根据模板条件查询list
		 */
		public List<M> findList(String orderBy) {
			return findList(null, orderBy);
		}
		
		/**
		 * @说明 根据模板条件查询list, 核心实现
		 */
		public List<M> findList(Object paras, String orderBy) {
			return (List<M>) getDao().template(this.getClass(), paras, -1, -1, orderBy);
		}

		
		// 根据模板分页查询，没有orderby
		/**
		 * @说明 根据模板条件分页查询，没有paras和orderBy
		 */
		public PageQuery<M> findPage(long pageNumber, long pageSize) {
			return findPage(null, pageNumber, pageSize, null);
		}
		
		/**
		 * @说明 根据模板条件分页查询, 没有paras
		 */
		public PageQuery<M> findPage(long pageNumber, long pageSize, String orderBy) {
			return findPage(null, pageNumber, pageSize, orderBy);
		}
		
		/**
		 * @说明 根据模板条件分页查询，没有orderBy
		 */
		public PageQuery<M> findPage(Object paras, long pageNumber, long pageSize) {
			return findPage( paras, pageNumber, pageSize, null);
		}
		
		/**
		 * @说明 根据模板条件分页查询，分页的所有条件都有，核心实现
		 */
		public  PageQuery<M> findPage(Object paras, long pageNumber, long pageSize, String orderBy) {
			long start = (pageNumber-1) * pageSize;
			List<M> list = (List<M>) getDao().template(this.getClass(), paras, start, pageSize, orderBy);
			long count = list.size();
			if(list.size() > pageSize){ //list的大小大于一页才有必要查询总数
				count = findCount(paras);
			}
			PageQuery<M> pageQuery = new PageQuery<M>();
			pageQuery.setList(list);
			pageQuery.setTotalRow(count);
			pageQuery.setPageNumber(pageNumber);
			pageQuery.setPageSize(pageSize);
			return pageQuery;
		}
		
		//分页
		/**
		 * @说明 根据模板条件分页查询，分页的所有条件都有，一个参数实现分页
		 */
		public PageQuery<M> findPage(PageQuery<M> pageQuery) {
			return findPage(pageQuery.getParas(), pageQuery.getPageNumber(), pageQuery.getPageSize(), pageQuery.getOrderBy());
		}
		
		/**
		 * @说明 根据模板条件查询总数,考虑把名字换成conut
		 */
		public long findCount(Object object) {
			return getDao().templateCount(object);
		}
		
		/**
		 * @说明 可用于分页的万能单表通用查询，可代替所有单表操作
		 */
		public Query<M> query() {
			return new Query(getDao(), this.getClass());
			//return new Query<M>(getDao(), (Class<M>) this.getClass());
			//return (Query<M>) getDao().query(this.getClass());
		}
		
		/**
		 * @说明 改进的万能单表通用查询   andEq和andLike方法参数允许为空，like非空参数自动前后加百分号
		 * 支持多表关联查询，调用非andEq和andLike后需要把query强转回myQuery才能使用，因为父接口中没有此方法
		 */
		//例子：List<Person> list = Person.dao.myQuery().andEq("p.name", null).andLike("p.name", "天").andLike("name", null).select();
		public MyQuery<M> myQuery() {
			return new MyQuery(getDao(), this.getClass());
		}
		
		/*default List<? extends ActiveRecord> findByIdLoadColumns(Object id, String... columns) {
			return getDao().query(this.getClass()).andEq(idname, id).select(columns);
		}*/
		
		/**
		 * @说明 通过sql查询，这里的sql是原生sql，不是beetl语法的sql，考虑换名findBySql
		 */
		public List<M> executeQuery(String sql, Object paras) {
			return (List<M>) getDao().execute(new SQLReady(sql, paras), this.getClass());
		}
		
		/**
		 * @说明 通过sql 增删改  返回成功执行条数，这里的sql是原生sql，不是beetl语法的sql，考虑换名updateBySql
		 */
		public int executeUpdate(String sql, Object paras) {
			return getDao().executeUpdate(new SQLReady(sql, paras));
		}

	// List<Student> list = Student.dao.where("clazzId = ?", 2); 找到班级下所有学生  activejdbc的写法
	// List<Student> list = Student.dao.query().andEq("clazzId", 2).select();
	// List<Student> list = Student.dao.where("clazzId", 2);
	/**
	 * @说明 方便一个条件的查询(用=连接)，就是为了少写一个select和一个andEq，主要用于外键查询,查询班级下的所有学生特别方便
	 *     查询全部列，不再提供查询部分列的方法，免去记忆负担，查询部分列要使用MyQuery查询，模板查询同理
	 */
	public List<M> where(String column, Object para) {
		return query().andEq(column, para).select();

		// return new Query<M>(getDao(), (Class<M>) this.getClass());
		// return (Query<M>) getDao().query(this.getClass());
	}
}
