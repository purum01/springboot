package com.edu.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.edu.entity.ClubMember;
import com.edu.entity.Note;

@SpringBootTest
public class NoteRepositoryTests {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ClubMemberRepository clubMemberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void insertNotes() {
        // Assume we have a ClubMember with id 1 in the database
        ClubMember writer = clubMemberRepository.findById("guest@kt.com").orElseThrow(() -> new IllegalArgumentException("Writer not found"));

        // Insert 10 notes
        IntStream.rangeClosed(1, 10).forEach(i -> {
            Note note = Note.builder()
                    .title("Sample Title " + i)
                    .content("Sample Content " + i)
                    .writer(writer)
                    .build();

            noteRepository.save(note);
        });
    }
}
