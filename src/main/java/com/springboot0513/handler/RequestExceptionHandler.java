package com.springboot0513.handler;

import com.springboot0513.util.ReturnCode;
import com.springboot0513.util.ReturnVO;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestExceptionHandler implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }
    @RequestMapping("/error")
    public ReturnVO errorPage(){
        return new ReturnVO(ReturnCode.UrlError);
    }
}
