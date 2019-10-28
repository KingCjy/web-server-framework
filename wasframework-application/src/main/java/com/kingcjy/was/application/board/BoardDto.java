package com.kingcjy.was.application.board;

import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

public class BoardDto {

    @Getter
    @Setter
    public static class BoardResponseDto {
        private Long id;
        private String title;
        private String contents;
        private String createdDateTime;
        private String updatedDateTime;

        public BoardResponseDto(Board entity) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            this.id = entity.getId();
            this.title = entity.getTitle();
            this.contents = entity.getContents();
            this.createdDateTime = entity.getCreatedDateTime().format(formatter);
            this.updatedDateTime = entity.getUpdatedDateTime().format(formatter);
        }
    }

    @Getter
    @Setter
    public static class BoardRequestDto {
        private String title;
        private String contents;
    }
}
