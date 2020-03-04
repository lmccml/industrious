package spring_boot.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author lmc
 * @date 2020/3/4 10:47
 */
@Slf4j
@RestController
public class QueryTestController {
    @RequestMapping("/query")
    public String query(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        log.info("查询开始", JSON.toJSONString(params));
        return "hello " + params.get("userName").toString() + "!";
    }
}
