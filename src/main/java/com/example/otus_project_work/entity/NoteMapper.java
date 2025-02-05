package com.example.otus_project_work.entity;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteMapper {
    Note toEntity(NoteDto noteDto);

    NoteDto toNoteDto(Note note);
}