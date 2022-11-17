package com.example.demo2.Service;


import com.example.demo2.model.TodoEntity;
import com.example.demo2.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public String testService() {
        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("첫번째 해야할 것").build();
        //TodoEntity 저장
        repository.save(entity);
        //TodoEntity 검색
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return  savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity) {
        /* 리팩토링 전

        //검증
        if(entity == null) {
            log.warn("Entity가 null입니다.");
            throw new RuntimeException("Entity cannot be null");
        }
        if(entity.getUserId() == null) {
            log.warn("알려지지 않은 유저입니다.");
            throw new RuntimeException("Unknown User");
        }

         */

        validate(entity);

        repository.save(entity);

        log.info("Entity Id : {} is saved",entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

        //검증
    private void validate(final TodoEntity entity) {
        if(entity == null) {
            log.warn("Entity가 null입니다.");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("알려지지 않은 유저입니다.");
            throw new RuntimeException("Unknown User");
        }
    }

    public List<TodoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<TodoEntity> update (final TodoEntity entity) {
        //(1) 받은 엔티티가 유효한지 확인.
        validate(entity);
        //(2) 넘겨받은 엔티티 id 이용 TodoEntity 가져오기.
        //존재하기 엔티티는 변경할 수 없기때문.
        final Optional<TodoEntity> original = repository.findById(entity.getId());
        //(3) 반환된 엔티티가 있으면 새 엔티티값으로 덮어씌우기
        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            repository.save(todo);
        });
        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> delete (final TodoEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            //exception 발생시 id 보여주기
            log.error("error deleting entity :" + entity.getId(), e);
            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        return retrieve(entity.getId());
    }

}
