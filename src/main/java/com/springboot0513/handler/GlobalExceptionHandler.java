package com.springboot0513.handler;

import com.springboot0513.util.ReadPropertiesUtil;
import com.springboot0513.util.ReturnVO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Properties;
import static com.springboot0513.util.ReturnCode.valueOf;

/**
 * 全局异常处理类
 * @author yangwei
 *
 * 用于全局返回json，如需返回ModelAndView请使用@ControllerAdvice
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static Properties properties = ReadPropertiesUtil.getProperties("G:\\idea-workspace\\demo\\src\\main\\resources\\response.properties");


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(handlerException(ex), HttpStatus.OK);
    }

    /**
     * 进入controller之后的异常捕获
     * @param e 捕获的异常
     * @return 封装的返回对象
     **/
    @ExceptionHandler(Exception.class)
    public ReturnVO handlerException(Throwable e) {
        ReturnVO returnVO = new ReturnVO();
        String errorName = e.getClass().getName();
        errorName = errorName.substring(errorName.lastIndexOf(".") + 1);
        if (e.getClass() == RuntimeException.class) {
            returnVO.setMessage(properties.getProperty(valueOf("RuntimeException").msg()) +": "+ e.getMessage());
            returnVO.setCode(properties.getProperty(valueOf("RuntimeException").val()));
        } else {
            returnVO.setMessage(properties.getProperty(valueOf(errorName).msg()));
            returnVO.setCode(properties.getProperty(valueOf(errorName).val()));
        }
        return returnVO;
    }
}
