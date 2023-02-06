package com.onlyfresh.devkurly.web.controller;

import com.onlyfresh.devkurly.repository.ProductRepository;
import com.onlyfresh.devkurly.web.dto.MainDto;
import com.onlyfresh.devkurly.web.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final HomeService homeService;

    @RequestMapping("/")
    public String Home(HttpServletRequest request) {
        log.info("==================================HOME=======================================");
        return "main/index";
    }

    @GetMapping("/home")
    @ResponseBody
    public List<List<MainDto>> getHomeProducts() {
        return homeService.getMainInfo(2L, 6L, 9L);
    }
}
