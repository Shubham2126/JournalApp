package net.engineeringdigest.journalApp.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document
public class JournalEntry {
    @Id
    private Long id;
    private String title;
    private String content;
    private LocalDateTime date;

}