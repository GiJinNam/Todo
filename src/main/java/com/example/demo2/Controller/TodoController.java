package com.example.demo2.Controller;

import com.example.demo2.Service.TodoService;
import com.example.demo2.dto.ResponseDTO;
import com.example.demo2.dto.TodoDTO;
import com.example.demo2.model.TodoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testControllerResponseEntity(){
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String > response = ResponseDTO.<String>builder().data(list).build();
        //return ResponseEntity.badRequest().body(response); http code 400반환(안돼)_
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user"; // 일시적 유저 아이디
            //(1). TodoEntity 변환
            TodoEntity entity = TodoDTO.toEntity(dto);
            //(2) id null로 초기화 생성 당시에는 id가 없어야됨
            entity.setId(null);
            //(3) 임시 사용자 아이디 설정
            entity.setUserId(temporaryUserId);
            //(4) 서비스 사용해서 Todo엔티티 생성
            List<TodoEntity> entities = service.create(entity);
            //(5) 자바 스트림을 이용해 리턴된 엔티티 리스트들 TodoDTO리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            //(6) 변환된 TodoDTO 리스트 이용해 ResponceDTO초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            //(7) RespoceDTO 리턴
            return ResponseEntity.ok().body(response);
        }   catch (Exception e) {
            //(8) 예외 있으면 dto대신 error출력
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user";
        //(1) 서비스 메서드의 retrieve() 메소드를 이용해 Todo리스트 가져오기
        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        //(2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        //(3) 변환된 TodoDTO리스트를 이용해 ResponseDTO초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        //(4) ResponseDTO 리턴
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> UpdateTodo(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user";
        //(1) dto entity로 변환
        TodoEntity entity = TodoDTO.toEntity(dto);
       //(2) id를 temporaryUserId로 변환
       entity.setUserId(temporaryUserId);
       //(3) 서비스를 이용해 entity변환
        List<TodoEntity> entities = service.update(entity);
        //(4) 자바스트림 이용 리턴된 엔티티 리스트를 TodoDTO리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        //(5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        //(6) ResponseDTO 리턴
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping
    public ResponseEntity<?> delecteTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user";
            //TodoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(dto);
            //임시사용자 설정 인증기능 아직 미구현이므로 "temporary-user만 로그인없이 사용가능 시스템"
            entity.setUserId(temporaryUserId);
            //서비스 이용 entity삭제
            List<TodoEntity> entities = service.delete(entity);
            //자바 스트림 이용 리턴된 엔티티 리스트를 TodoDto리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            //변환된 TodoDTO리스트를 이용해 리스톤디티오 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            //ResponseDTO리턴
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }




}
