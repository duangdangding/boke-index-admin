package com.pearadmin.boke.utils.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;

/**
 * 全局异常处理
 */
@ResponseBody
@ControllerAdvice
public class MyExceptionHander {
	private static final Logger logger = LoggerFactory.getLogger(MyExceptionHander.class);
	
	/**
	 * 处理自定义的业务异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = BizException.class)
	public ResultDto bizExceptionHandler(BizException e){
		logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
		return ResultDtoManager.fail(-1,e.getErrorMsg());
	}

	/**
	 * 数字转换异常
	 * @param e
	 * @return
	 */
	/*@ExceptionHandler(value = NumberFormatException.class)
	public ResultDto numberFormat(NumberFormatException e) {
		logger.error("异常原因是：{}",e);
		return ResultDtoManager.fail(-1, "请输入正确的数字格式！");
	}*/

	/**
	 * 处理空指针的异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value =NullPointerException.class)
	public ResultDto nullException(NullPointerException e){
		logger.error("发生空指针异常！原因是:",e);
		return ResultDtoManager.fail(-1, CommonEnum.BODY_NOT_MATCH.getResultMsg());
	}

    /**
     * 断言的异常
	 * @param e
     * @return
     */
	@ExceptionHandler(value =IllegalArgumentException.class)
	public ResultDto illegalArgumentException(IllegalArgumentException e){
		logger.error("异常！原因是:",e);
		return ResultDtoManager.fail(-1,e.getMessage());
	}

	/**
	 * 处理其他异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value =Exception.class)
	public ResultDto otherException(Exception e){
		logger.error("未知异常！原因是:",e);
		return ResultDtoManager.fail(-1, CommonEnum.INTERNAL_SERVER_ERROR.getResultMsg());
	}
	
}
