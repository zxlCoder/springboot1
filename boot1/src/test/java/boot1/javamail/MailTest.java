package boot1.javamail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
	@Autowired
	private JavaMailComponent javaMailComponent;

	@Test
	public void testAdd() {
		this.javaMailComponent.sendMail("1004175116@qq.com");
	}
}

