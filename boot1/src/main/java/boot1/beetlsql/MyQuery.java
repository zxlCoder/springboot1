package boot1.beetlsql;

import java.util.List;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLReady;
import org.beetl.sql.core.kit.StringKit;
import org.beetl.sql.core.query.Query;


/**
 * 重写adnEq和andLike方法，添加非空判断,并给like自动加左右百分号
 */
//alt+shift+j
public class MyQuery<T> extends Query<T>{
	
	Class<T> clazz = null;  //父类中的clazz不可见，所以在这里再放一个供下面定义的方法使用
	
	public MyQuery(SQLManager sqlManager, Class<T> clazz) {
		super(sqlManager, clazz);
		this.clazz = clazz;
	}

	/* 
	 * 因为我们生成的是MyQuery对象，所以每次调下面的方法都是调用的我们重写的方法
	 */
	@Override
    public MyQuery<T> andEq(String column, Object value) {
		if(value!=null){
			if(value instanceof String) {
				String str = (String)value;
				if(StringKit.isNotBlank(str)){
					//return super.andEq(column, value);
					appendAndSql(column, value, "=");
				}
			}else{
				//return super.andEq(column, value);
				appendAndSql(column, value, "=");
			}
		}
        return (MyQuery) this;
    }
	
	@Override
	public MyQuery<T> andLike(String column, String value) {
		if(value instanceof String && StringKit.isNotBlank(value)){
			value = "%"+value+"%";
			//return super.andLike(column, value);
	        appendAndSql(column, value, "LIKE ");
		}
        return (MyQuery) this;
	}
	
	//下面三个方法是为了让query支持多表查询，下面三个方法父类中没有，都是新增的而不是覆写的
    public List<T> select(String sql) {
        return this.selectByType2(sql, clazz);
    }
    
    protected <K> List<K> selectByType2(String sql ,Class<K> retType) {
        StringBuilder sb = new StringBuilder(sql);
        sb.append(" ").append(getSql());
        this.setSql(sb);
        addAdditionalPartSql2(); //因为父类中此方法不可见，所以把实现搬到了下面
        
        String targetSql = this.getSql().toString();
        Object[] paras = getParams().toArray();
        //先清除，避免执行出错后无法清除
        clear();
        List<K> list = this.sqlManager.execute(new SQLReady(targetSql, paras), retType);
        return list;
    }
    
    private void addAdditionalPartSql2() {
        StringBuilder sb = this.getSql();
        if (this.orderBy != null) {
            sb.append(orderBy.getOrderBy()).append(" ");
        }

        if (this.groupBy != null) {
            sb.append(groupBy.getGroupBy()).append(" ");
        }
        // 增加翻页
        if (this.startRow != -1) {
            setSql(new StringBuilder(
                    sqlManager.getDbStyle().getPageSQLStatement(this.getSql().toString(), startRow, pageSize)));
        }
    }
    
    
	
}
