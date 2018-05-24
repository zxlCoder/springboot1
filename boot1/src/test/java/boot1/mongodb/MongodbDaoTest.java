package boot1.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import boot1.model.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbDaoTest {

    @Autowired
    private MongodbDao mongodbDao;
    
    @Test
    public void testInsert() {
    	User user = new User();
    	user.setId(1);
    	user.setUsername("张三");
    	user.setDescr("乖宝宝");
    	mongodbDao.insert(user);
    	User user2 = new User();
    	user2.setId(2);
    	user2.setUsername("李四");
    	user2.setDescr("乖宝宝");
    	mongodbDao.insert(user2);
    }
    
    @Test
    public void testDelete() {
        mongodbDao.deleteById(2);
    }
    
    @Test
    public void testUpdate() {
    	User user = new User();
    	user.setId(1);
    	user.setDescr("坏宝宝");
    	mongodbDao.update(user);
    }
    
    @Test
    public void testGet() {
    	User user = mongodbDao.getById(1);
    	System.out.println(user.toString());
    }
}
