package org.example.inminute_demo.api.controller;

import lombok.RequiredArgsConstructor;
import org.example.inminute_demo.api.dto.note.request.CreateNoteRequest;
import org.example.inminute_demo.api.dto.note.request.UpdateNoteRequest;
import org.example.inminute_demo.api.dto.note.response.CreateNoteResponse;
import org.example.inminute_demo.api.dto.note.response.NoteDetailResponse;
import org.example.inminute_demo.api.dto.note.response.NoteListResponse;
import org.example.inminute_demo.api.dto.note.response.UpdateNoteResponse;
import org.example.inminute_demo.api.service.NoteService;
import org.example.inminute_demo.global.apipayload.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/notes")
    public ApiResponse<CreateNoteResponse> createNote(@RequestBody CreateNoteRequest createNoteRequest) {
        return ApiResponse.onSuccess(noteService.createNote(createNoteRequest));
    }

    @PatchMapping("/notes/{noteId}")
    public ApiResponse<UpdateNoteResponse> updateNote(@PathVariable Long noteId, @RequestBody UpdateNoteRequest updateNoteRequest) {
        return ApiResponse.onSuccess(noteService.updateNote(noteId, updateNoteRequest));
    }

    @GetMapping("/notes/all")
    public ApiResponse<NoteListResponse> getNoteList() {
        return ApiResponse.onSuccess(noteService.getNoteList());
    }

    @GetMapping("/notes")
    public ApiResponse<NoteListResponse> getNoteListByFolder(@RequestParam Long folderId) {
        return ApiResponse.onSuccess(noteService.getNoteListByFolder(folderId));
    }

    @GetMapping("/notes-detail/{noteId}")
    public ApiResponse<NoteDetailResponse> getNote(@PathVariable Long noteId) {
        return ApiResponse.onSuccess(noteService.getNote(noteId));
    }

    @DeleteMapping("/notes/{noteId}")
    public ApiResponse<?> deleteNote(@PathVariable Long noteId) {
        noteService.deleteNote(noteId);
        return ApiResponse.onSuccess("회의록 삭제됨");
    }
}