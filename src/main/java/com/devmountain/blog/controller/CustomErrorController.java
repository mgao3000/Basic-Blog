package com.devmountain.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String errorHandler(HttpServletRequest httpServletRequest) {
        Object status = httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if( status != null ){
            Integer statusCode = Integer.valueOf(status.toString());
            if( statusCode.equals(HttpStatus.NOT_FOUND.value())) {
                return "error/404";
            } else if( statusCode.equals(HttpStatus.FORBIDDEN)) {
                return "error/403";
            } else if( statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR.value())) {
                return "error/500";
            }
        }
        // Do something like logging
        loggingError(httpServletRequest, status);

        return "error/default";
    }

    private void loggingError(HttpServletRequest httpServletRequest, Object errorStatus) {
        if(errorStatus != null) {
            StringBuilder errorBuilder = new StringBuilder();
            errorBuilder.append("StatusCode->")
                    .append(RequestDispatcher.ERROR_STATUS_CODE)
                    .append("\n ErrorException->")
                    .append(RequestDispatcher.ERROR_EXCEPTION.toString())
                    .append("\n ErrorException->")
                    .append(RequestDispatcher.ERROR_MESSAGE.toString());
            log.error("<-------------------------------->");
            log.error("An error occured!!!");
            log.error(errorBuilder.toString());
            log.error("<-------------------------------->");
        }
    }

    public String getErrorPath() {
        return "/error";
    }
}
