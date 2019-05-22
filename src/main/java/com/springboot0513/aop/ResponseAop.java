package com.springboot0513.aop;

import com.springboot0513.handler.GlobalExceptionHandler;
import com.springboot0513.util.ReadPropertiesUtil;
import com.springboot0513.util.ReturnCode;
import com.springboot0513.util.ReturnVO;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Properties;

/**
 * 统一封装返回值和异常处理
 *
 * @author vi
 * @since 2018/12/20 6:09 AM
 */
@Slf4j
@Aspect
@Order(5)
@Component
public class ResponseAop {
    @Autowired
    private GlobalExceptionHandler exceptionHandler;

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    private static final Properties properties = ReadPropertiesUtil.getProperties("G:\\idea-workspace\\demo\\src\\main\\resources\\response.properties");

    /**
     * 切点
     */
    @Pointcut("execution(public * com.springboot0513.controller..*(..))")
    public void httpResponse() {
    }

    @Before("httpResponse()")
    public void doBefore(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        System.out.println(request.getServerName());
        System.out.println(request.getServerPort());
        //记录请求的内容
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        log.info("接口路径：{}" , request.getRequestURL().toString());
        log.info("浏览器：{}", userAgent.getBrowser().toString());
        log.info("浏览器版本：{}",userAgent.getBrowserVersion());
        log.info("操作系统: {}", userAgent.getOperatingSystem().toString());
        log.info("IP : {}" , request.getRemoteAddr());
        log.info("请求类型：{}", request.getMethod());
        log.info("类方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数 : {} " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 环切
     */
    @Around("httpResponse()")
    public ReturnVO handlerController(ProceedingJoinPoint proceedingJoinPoint) {
        ReturnVO returnVO = new ReturnVO();
        try {
            //获取方法的执行结果
            Object proceed = proceedingJoinPoint.proceed();
            //如果方法的执行结果是ReturnVO，则将该对象直接返回
            if (proceed instanceof ReturnVO) {
                returnVO = (ReturnVO) proceed;
            } else {
                //否则，就要封装到ReturnVO的data中
                returnVO.setData(proceed);
            }
        }  catch (Throwable throwable) {
            // 这里直接调用刚刚我们在handler中编写的方法
            returnVO =  exceptionHandler.handlerException(throwable);
        }
        return returnVO;
    }

    /**
     * 异常处理
     */
    private ReturnVO handlerException(Throwable throwable) {
        ReturnVO returnVO = new ReturnVO();
        //这里需要注意，返回枚举类中的枚举在写的时候应该和异常的名称相对应，以便动态的获取异常代码和异常信息
        //获取异常名称的方法
        String errorName = throwable.toString();
        errorName = errorName.substring(errorName.lastIndexOf(".") + 1);
        //直接获取properties文件中的内容
        returnVO.setMessage(properties.getProperty(ReturnCode.valueOf(errorName).msg()));
        returnVO.setCode(properties.getProperty(ReturnCode.valueOf(errorName).val()));
        return returnVO;
    }

    @AfterReturning(returning = "ret" , pointcut = "httpResponse()")
    public void doAfterReturning(Object ret){
        //处理完请求后，返回内容
        log.info("方法返回值：{}" , ret);
        log.info("方法执行时间：{}毫秒", (System.currentTimeMillis() - startTime.get()));
    }
}
