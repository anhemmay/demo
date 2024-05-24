package com.example.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class PerformanceAspect {
    @Pointcut("within(com.example.demo.controller.*)")
    public void controllerMethod(){}

    @Around("controllerMethod()")
    public Object getCost(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.nanoTime();
        Object result = joinPoint.proceed();
        long end = System.nanoTime();
        double durationInSeconds = (end - start) / 1e9;
        log.warn("Thời gian thực thi phương thức: " + joinPoint.getSignature().getName() +
                " trong class " + joinPoint.getSignature().getDeclaringTypeName() +
                " là " + durationInSeconds + " giây");
        return result;
    }

}
