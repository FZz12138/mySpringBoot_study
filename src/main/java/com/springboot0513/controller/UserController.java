package com.springboot0513.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot0513.entity.UserDO;
import com.springboot0513.service.IUserService;
import com.springboot0513.util.ReturnCode;
import com.springboot0513.util.ReturnVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

import static com.springboot0513.util.ReturnCode.NullResponseException;


@RestController
@RequestMapping(value ="/user" )
@Api(value = "用户类控制器",tags="用户类控制器")
@Slf4j
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userService;

    @GetMapping("/get")
    public List<UserDO> get(){
        List<UserDO> users = new ArrayList<>();
        try {
            for (int i=0,j=5;i<j;i++){
                UserDO userDO = new UserDO();
                userDO.setUsername("vi");
                userDO.setPassword("password");
                userService.insert(userDO);
            }
            users = userService.findAll();
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
        return  users;
    }
    @RequestMapping("/findAll")
    @ApiOperation(value = "获取用户列表",notes = "获取用户列表")
    public Object findAll(){
        PageHelper.startPage(1, 2);
        return PageInfo.of(userService.findAll());
    }
    @RequestMapping("/test")
    public ReturnVO test(){
        try {
            //省略
            //省略
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return new ReturnVO();
    }
    @RequestMapping("/testEx")
    public ReturnVO testEx(){
       return  new ReturnVO(NullResponseException);
    }

    @PostMapping(value = "/testEx2")
    public Object testEx2() {
        throw new RuntimeException("ddd");
    }

    @RequestMapping(value = "/testEx3")
    public ReturnVO testEx3(UserDO userDO) {
        System.out.println(userDO);
        return new ReturnVO(userService.findAll());
    }

    @RequestMapping(value = "/testEx4")
    public ReturnVO testEx4() {
        throw new RuntimeException("测试非自定义运行时异常");
    }
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @RequestMapping("/getById")
    public UserDO get(@ApiParam(name="id",value="用户id",required=true) Long id) {
        log.info("GET..{}...方法执行。。。",id);
        return userService.get(10000000L);
    }
}
