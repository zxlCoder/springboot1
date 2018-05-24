package boot1.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//如果项目前后端是通过 JSON 进行数据通信，则当出现异常时可以常用如下方式处理异常信息
//我们还可以自定义异常，在全局异常的处理类中捕获和判断，从而对不同的异常做出不同的处理。
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

	/**
	 * 处理 Exception 类型的异常
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public Map<String, Object> defaultExceptionHandler(Exception e) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", 500);
		map.put("msg", e.getMessage());
		return map;
	}
}
