package net.engineeringdigest.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Document(collection="users")
public class UserEntry {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private List<String > roles;

    @DBRef
    private List<JournalEntry> entries = new ArrayList<>();

}