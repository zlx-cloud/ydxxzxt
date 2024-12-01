package net.sinodata.security.rest;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.mapper.JsonMapper;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private JsonMapper jsonMapper = new JsonMapper();
	private static Logger logger = LoggerFactory
			.getLogger(ResponseEntityExceptionHandler.class);
	@ExceptionHandler(value = { ConstraintViolationException.class })
	public final ResponseEntity<?> handleException(
			ConstraintViolationException ex, WebRequest request) {
		Map<String, String> errors = BeanValidators
				.extractPropertyAndMessage(ex.getConstraintViolations());
		logger.error("springmvc 拦截错误", ex);
		String body = jsonMapper.toJson(errors);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return handleExceptionInternal(ex, body, null, HttpStatus.BAD_REQUEST,
				request);
	}

	@ExceptionHandler(value = { Exception.class })
	public final ResponseEntity<?> handleAllException(Exception ex,
			WebRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Exception", ex.toString());
		logger.error("springmvc 拦截错误", ex);
		String body = jsonMapper.toJson(map);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return handleExceptionInternal(ex, body, null,
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
