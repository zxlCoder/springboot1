package boot1.beetlsql;

import org.beetl.sql.core.SQLManager;

/**
 * <BR>
 * create time : 2017-04-06 15:46
 *
 * @author luoyizhu@gmail.com
 */

public class SqlKit {
    static SQLManager dao;

    private SqlKit() {

    }

    /**
     * @return sqlManager
     */
    public static SQLManager dao() {
        return dao;
    }

    /**
     * 设置sqlMananger, 整个服务器启动只需要设置一次
     *
     * @param sqlManager sm
     */

    public static void dao(SQLManager sqlManager) {
    	dao = sqlManager;
    }

    /**
     * 获取接口内置实现
     *
     * @param mapperInterface 接口
     * @param <T>             接口实例
     * @return 接口实例
     */
    public static <T> T mapper(Class<T> mapperInterface) {
        return dao().getMapper(mapperInterface);
    }
}
