package com.example.movie.aspects;

import com.example.movie.entities.AuditLog;
import com.example.movie.repositories.AuditLogRepository;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Around(value = "@annotation(Audit)")
    public Object logMethodInvocation(ProceedingJoinPoint pjp) throws Throwable {
        AuditLog auditLog = AuditLog.builder()
                .methodName(pjp.getSignature().toShortString())
                .arguments(ToStringBuilder.reflectionToString(pjp.getArgs()))
                .invocationTime(LocalDateTime.now())
                .build();
        try {
            Object proceed = pjp.proceed();
            auditLog.setReturnValue(proceed.toString());
            return proceed;
        } catch (Throwable e) {
            auditLog.setThrownException(e.toString());
            throw e;
        } finally {
            auditLogRepository.save(auditLog);
        }
    }
}