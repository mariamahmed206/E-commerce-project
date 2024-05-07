
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;

// import com.nimbusds.oauth2.sdk.util.StringUtils;

// import ch.qos.logback.classic.Logger;

// @Aspect
// @Component
// public class logTimeAspect {

//     Logger log = (Logger) LoggerFactory.getLogger(LogTimeAspect.class);
// @Around(value = "execution(* com.global.book.service..*(..))")
// public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
//     long startTime = System.currentTimeMillis();
//     StringBuilder sb = new StringBuilder("KPI:");
//     sb.append("[").append(joinPoint.getKind()).append("]\tfor: ").append(joinPoint.getSignature())
//              .append("\twithArgs: ").append("(").append(StringUtils.join(joinPoint.getArgs(), ",")).append(")");
//     sb.append("\ttook: ");
//     Object returnValue = joinPoint.proceed();
//     log.info(sb.append(System.currentTimeMillis() - startTime).append(" ms. ").toString());

//     return returnValue;
// }

// }


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class logTimeAspect {

    private static final Logger log = LoggerFactory.getLogger(logTimeAspect.class);

    @Around("execution(* com.global.book.service..*(..))")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("KPI:");
        sb.append("[").append(joinPoint.getKind()).append("]\tfor: ").append(joinPoint.getSignature())
                .append("\twithArgs: (");
        Object[] args = joinPoint.getArgs();
        if (args.length > 0) {
            sb.append(args[0]);
            for (int i = 1; i < args.length; i++) {
                sb.append(", ").append(args[i]);
            }
        }
        sb.append(")");
        sb.append("\ttook: ");
        Object returnValue = joinPoint.proceed();
        long elapsedTime = System.currentTimeMillis() - startTime;
        log.info(sb.append(elapsedTime).append(" ms.").toString());
        return returnValue;
    }
}
