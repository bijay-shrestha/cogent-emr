package com.cogent.contextserver.model;

import com.cogent.contextserver.config.Auditable;
import com.cogent.contextserver.config.FileEntityListener;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@EntityListeners(FileEntityListener.class)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class File extends Auditable<String> {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String content;

    public File(String name, String content) {
        this.name = name;
        this.content = content;
    }
}