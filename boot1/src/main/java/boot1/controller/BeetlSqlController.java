package boot1.controller;

import org.beetl.sql.core.SQLManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("beetlsql")
public class BeetlSqlController {
	@Autowired
	private SQLManager dao ;
}
