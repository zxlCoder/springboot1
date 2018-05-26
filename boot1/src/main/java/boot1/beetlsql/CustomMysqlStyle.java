package boot1.beetlsql;

import org.beetl.sql.core.db.KeyWordHandler;
import org.beetl.sql.core.db.MySqlStyle;


/**
 * @author zxl
 * 去掉列中的引号，防止用别名的时候报错
 */
public class CustomMysqlStyle extends MySqlStyle {
	public CustomMysqlStyle() {
		 this.keyWordHandler = new KeyWordHandler() {
	            @Override
	            public String getTable(String tableName) {
	                return "`" + tableName + "`";

	            }

	            @Override
	            public String getCol(String colName) {
	                return colName;
	            }

	        };
	}
}
