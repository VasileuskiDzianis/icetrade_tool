package by.vasilevsky.ice_tooll.web.error;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionHandlerController {
	private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public @ResponseBody String handleGlobalExceptions(Exception e) {
		LOGGER.error(e);
		
		return "Internal error occured";
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody String handleValidationExceptions(NumberFormatException e) {
		LOGGER.error(e);
		
		return "Bad request";
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public @ResponseBody String handleResourceNotFoundException(NoHandlerFoundException e) {
		LOGGER.error(e);
		
		return "Resource wasn't found";
	}
}
