package com.bug.framework.aop.aspect;

import com.alibaba.fastjson.JSON;
import com.bug.framework.aop.annotation.SystemLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统日志切面类
 */
@Component
@Aspect
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 指定切点, 使用了指定注解的地方都是切点
     */
    @Pointcut("@annotation(com.bug.framework.aop.annotation.SystemLog)")
    public void pointcut() {

    }

    /**
     * 打印日志
     * "@Around"注解 环绕控制, 指定所用的切点
     *
     * @param joinPoint 被增强方法的信息所包装出来的对象
     * @return 返回被增强方法的执行结果
     */
    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

        // 调用目标方法
        Object proceed;
        try {
            // 目标方法执行前, 打印...
            handlerBefore(joinPoint);

            proceed = joinPoint.proceed();

            // 目标方法执行后, 打印...
            logger.info("Response Result     {}" + JSON.toJSONString(proceed));
        } finally {
            // 拼接系统换行符
            logger.info("==========End==========" + System.lineSeparator());
        }

        return proceed;
    }

    private void handlerBefore(ProceedingJoinPoint joinPoint) {
        // 获取请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取joinPoint中的签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        // 获取被增强方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);

        logger.info("==========Start===========");
        // 打印请求的IP
        logger.info("IP                  {}", request.getRemoteHost());
        // 打印请求的URL
        logger.info("URL                 {}", request.getRequestURL());
        // 打印请求的描述信息
        logger.info("business            {}", systemLog.business());
        // 打印 Http Method
        logger.info("Http Method         {}", request.getMethod());
        // 打印调用controller的全路径和执行方法
        logger.info("Class Method        {}.{}", methodSignature.getDeclaringTypeName(), methodSignature.getName());
        // 打印请求的入参
        logger.info("Request Args        {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        // 获取joinPoint的签名, 签名中包含了被增强方法的完全体
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取并返回被增强方法的注解
        return signature.getMethod().getAnnotation(SystemLog.class);
    }

}
