package com.edu.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edu.dto.NoteDTO;
import com.edu.service.NoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {

    private final NoteService service;

    @PostMapping("/add")
    public ResponseEntity<Long> register(@RequestBody NoteDTO noteDTO) {
        Long num = service.register(noteDTO);
        return ResponseEntity.ok(num);
    }

    // 특정 번호의 Note 확인하기
    @GetMapping("/{num}")
    public ResponseEntity<NoteDTO> read(@PathVariable Long num) {
        NoteDTO noteDTO = service.get(num);
        return ResponseEntity.ok(noteDTO);
    }

    // 특정 회원의 모든 Note 확인하기
    @GetMapping("/all")
    public ResponseEntity<List<NoteDTO>> getList(@RequestParam String email) {
        List<NoteDTO> list = service.getAllWithWriter(email);
        return ResponseEntity.ok(list);
    }

    // Note 삭제
    @DeleteMapping("/{num}")
    public ResponseEntity<Void> remove(@PathVariable Long num) {
        service.remove(num);
        return ResponseEntity.ok().build();
    }

    // Note 수정
    @PutMapping("/{num}")
    public ResponseEntity<Void> modify(@RequestBody NoteDTO noteDTO) {
        service.modify(noteDTO);
        return ResponseEntity.ok().build();
    }
}
