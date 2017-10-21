package by.vasilevsky.ice_tooll.aspect;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import by.vasilevsky.ice_tooll.dao.RequestLogItemDao;
import by.vasilevsky.ice_tooll.domain.RequestLogItem;

@Aspect
@Component
public class RequestLoggingAspect {

	@Autowired
	private RequestLogItemDao requestLogItemDao;

	@Before("execution(* by.vasilevsky.ice_tooll.web.controller.IcetradeController.getTenderInfo(..))")
	public void logHttpRequest(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		for (Object argument : args) {
			if (argument instanceof Long) {
				RequestLogItem logItem = new RequestLogItem();
				logItem.setTenderId((Long) argument);
				logItem.setRequestDate(new Date());
				requestLogItemDao.save(logItem);
			}
		}
	}
}
