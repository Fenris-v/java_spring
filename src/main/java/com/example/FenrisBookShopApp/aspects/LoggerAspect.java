package com.example.FenrisBookShopApp.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggerAspect {
    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Around(value = "@annotation(com.example.FenrisBookShopApp.annotations.Loggable)")
    public Object logAdvice(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        logger.info("method " + signature.getMethod().getName() + " started");

        Object returnValue;
        try {
            returnValue = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }

        logger.info("method " + signature.getMethod().getName() + " finished");
        return returnValue;
    }
}
