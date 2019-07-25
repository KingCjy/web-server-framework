package com.kingcjy.was.application.board;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "board")
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String title;

    public String contents;

    @CreationTimestamp
    public LocalDateTime createdDateTime;
    @UpdateTimestamp
    public LocalDateTime updatedDateTime;

    @Builder
    public Board(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
