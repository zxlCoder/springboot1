package boot1.beetlsql;

import static boot1.beetlsql.SqlKit.getDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.beetl.sql.core.TailBean;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;

/**
 * @author zxl
 * @说明  
 * 针对单表查询的类
 * 增删改查  查询一个  查询多个  查询全部   查询分页    查询多个count  查询全部count
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class Model2<M extends Model2> extends TailBean implements Serializable {

	    private static final long serialVersionUID = 1L;

		public M dao() {
			return (M) this;
		}

		/**
		 * @说明 保存对象，会返回id，会忽略插入的id而使用自增id,对于null值不做处理
		 */
		public boolean save() {
			//return getDao().insertTemplate(this, true) > 0; 
			return new Query(getDao(), this.getClass()).insertSelective(this) > 0;
	    }

		/**
	     * @说明 根据id更新，忽略空的属性
	     */
		public boolean update() {
	        //return getDao().updateTemplateById(this) > 0;
		// 此处不能用myquery，如果id不存在就更新全部的了
			return new Query(getDao(), this.getClass()).updateSelective (this) > 0;
	    }


		/**
		 * @说明 根据id删除对象
		 */
		public boolean deleteById(Object id) {
			//return getDao().deleteById(this.getClass(), id) > 0;
		// 此处不能用myquery，如果id不存在就删除全部的了
			return new Query(getDao(), this.getClass()).andEq("id", id).delete() > 0; //要获取主键名，复合主键要循环调用andEq
	    }


		/**
		 * @说明 根据id查询对象
		 */
		public M findById(Object id) {
			//return (M) getDao().unique(this.getClass(), id); // 如果对象不存在，则会抛出一个Runtime异常
			//return (M) getDao().single(this.getClass(), id); //如果对象不存在，返回null
			return (M) new Query(getDao(), this.getClass()).andEq("id", id).single(); //要获取主键名，复合主键要循环调用andEq

		}

		/**
		 * @说明 
		 * 查询全部对象 不知道为什么getAll和getList和getAlls方法在对象转json的时候会被一直调用，改成findAll all等
		 * 原因：fastjson转换对象的时候会调用get开头的方法
		 */
		public List<M> findAll() {
			//return (List<M>) getDao().all(this.getClass());
			return (List<M>) new Query(getDao(), this.getClass()).select(); 
			
		}

		/**
		 * @说明 查询总数
		 */
		public long findAllCount() {
			//return getDao().allCount(this.getClass());
			return new Query(getDao(), this.getClass()).count();
		}

		/*----------------------------------上面是基本方法---------------------------------*/
		/* 魔板查询方法都是用=连接的,以object中非空和非日期类型为条件 */

		// 根据模板条件查询一个 findFirst
		
		/**
		 * @说明 根据模板条件查询一个
		 */
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
		/**
		 * @说明 根据模板条件查询list
		 */
		public List<M> findList(Object paras) {
			//return (List<M>) getDao().template(this.getClass(), paras, -1, -1, null);
			MyQuery myQuery = new MyQuery(getDao(), this.getClass()); //其他所有模板方法都类似
	        Map<String, Object> param = null;
			if(paras instanceof Map){
				param = (Map) paras;
				if (param != null) {
		            for (Entry<String, Object> entry : param.entrySet()) {
		    			myQuery.andEq(entry.getKey(), entry.getValue());
		            }
		        }
			}
			if(paras instanceof Model2){
				//暂未实现
			}
			return myQuery.select();
		}
		
		/**
		 * @说明 根据模板条件查询list
		 */
		public List<M> findList(String orderBy) {
			return (List<M>) getDao().template(this.getClass(), null, -1, -1, orderBy);
		}
		
		/**
		 * @说明 根据模板条件查询list
		 */
		public List<M> findList(Object paras, String orderBy) {
			return (List<M>) getDao().template(this.getClass(), paras, -1, -1, orderBy);
		}

		
		// 根据模板分页查询，没有orderby
		/**
		 * @说明 根据模板条件分页查询，没有paras和orderBy
		 */
		public List<M> findPage(long start, long size) {
			return (List<M>) getDao().template(this.getClass(), null, start, size, null);
		}
		
		/**
		 * @说明 根据模板条件分页查询, 没有paras
		 */
		public List<M> findPage(long start, long size, String orderBy) {
			return (List<M>) getDao().template(this.getClass(), null, start, size, orderBy);
		}
		
		/**
		 * @说明 根据模板条件分页查询，没有orderBy
		 */
		public List<M> findPage(Object paras, long start, long size) {
			return (List<M>) getDao().template(this.getClass(), paras, start, size, null);
		}
		
		/**
		 * @说明 根据模板条件分页查询，分页的所有条件都有
		 */
		public  List<M> findPage(Object paras, long start, long size, String orderBy) {
			return (List<M>) getDao().template(this.getClass(), paras, start, size, orderBy);
		}
		
		//分页 推荐用这个方法
		/**
		 * @说明 根据模板条件分页查询，分页的所有条件都有
		 */
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
		
		/**
		 * @说明 根据模板条件查询总数
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
		public MyQuery<M> myQuery() {
			return new MyQuery(getDao(), this.getClass());
		}
		
		/*default List<? extends ActiveRecord> findByIdLoadColumns(Object id, String... columns) {
			return getDao().query(this.getClass()).andEq(idname, id).select(columns);
		}*/
		
		/**
		 * @说明 通过sql查询
		 */
		public List<M> executeQuery(String sql, Object paras) {
			return (List<M>) getDao().execute(sql, this.getClass(), paras);
		}
		
		/**
		 * @说明 通过sql 增删改  返回成功执行条数
		 */
		public int executeUpdate(String sql, Object paras) {
			return getDao().executeUpdate(sql, paras);
		}

}
