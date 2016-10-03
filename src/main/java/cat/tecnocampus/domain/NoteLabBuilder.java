package cat.tecnocampus.domain;

import java.time.LocalDateTime;

public class NoteLabBuilder {
    private String title;
    private String content;
    private LocalDateTime time;
    private LocalDateTime timeEdit;

    public NoteLabBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public NoteLabBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public NoteLabBuilder setTime(LocalDateTime time) {
        this.time = time;
        return this;
    }

    public NoteLabBuilder setTimeEdit(LocalDateTime timeEdit) {
        this.timeEdit = timeEdit;
        return this;
    }

    public NoteLab createNoteLab() {
        return new NoteLab(title,content,time,timeEdit);
    }
}