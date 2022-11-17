package com.example.demo2.Controller;

import com.example.demo2.dto.*;
import com.example.demo2.dto.TestRequestBodyDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("test")

public class TestController {
    @GetMapping
    public String TestController() {
        return "응애에요";

    }
    @GetMapping("/testgetMapping")
    public String testControllerWithPath() {
        return "testControllerWithPath";
    }

    @GetMapping("/id")
    public String testControllerWithPathVariables(@PathVariable(required = false) int id) {
        return "hello ID : " + id;
    }

    @GetMapping("testRequsetParam")
    public String testControllerRequestParam(@RequestParam(required = false) int id) {
        return "hello ID : " + id;
    }

    @GetMapping("/testRequestBody")
    public String testControllerRequsetBody(@RequestBody TestRequestBodyDTO testRequestBodyDTO) {
        return "ID : " + TestRequestBodyDTO.getId() + "  Message" + TestRequestBodyDTO.getMessage();
    }

    @GetMapping("/testResponseBody")
    public ResponseDTO<String> testControllerResponseBody() {
        List<String> list = new ArrayList<>();
        list.add("hello");
        ResponseDTO<String > response = ResponseDTO.<String>builder().data(list).build();
        return response;
    }

    @GetMapping("/testResponseEntity")
    public ResponseEntity<?> testControllerResponseEntity(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        ResponseDTO<String > response = ResponseDTO.<String>builder().data(list).build();
        //return ResponseEntity.badRequest().body(response); http code 400반환(안돼)_
        return ResponseEntity.ok().body(response);
    }

}
