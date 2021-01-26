package cn.zjw.study.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * LogAspect
 *
 * @author zjw
 * @createTime 2021/1/24 20:11
 */
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* cn.zjw.study..*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.printf("进入%s方法%n", joinPoint.getSignature().getName());
    }

    @After("log()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.printf("离开%s方法%n", joinPoint.getSignature().getName());
    }

}
