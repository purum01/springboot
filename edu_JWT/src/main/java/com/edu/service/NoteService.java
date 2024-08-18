package com.edu.service;

import java.util.List;

import com.edu.dto.NoteDTO;
import com.edu.entity.ClubMember;
import com.edu.entity.Note;

public interface NoteService {

	Long register(NoteDTO noteDTO);

	NoteDTO get(Long num);

	void modify(NoteDTO noteDTO);

	void remove(Long num);

	List<NoteDTO> getAllWithWriter(String writerEmail);

	default Note dtoToEntity(NoteDTO noteDTO) {
		Note note = Note.builder().num(noteDTO.getNum()).content(noteDTO.getContent())
				.writer(ClubMember.builder().email(noteDTO.getWriterEmail()).build()).build();
		return note;
	}

	default NoteDTO entityToDTO(Note note) {

		NoteDTO noteDTO = NoteDTO.builder().num(note.getNum()).title(note.getTitle()).content(note.getContent())
				.writerEmail(note.getWriter().getEmail()).regDate(note.getRegDate()).modDate(note.getModDate()).build();

		return noteDTO;
	}
}
