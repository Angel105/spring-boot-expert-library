package com.dap_studio.spring_boot_expert_library.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
public class Message {


    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "question")
    private String question;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "response")
    private String response;

    @Column(name = "closed")
    private boolean closed;


    public Message(String title, String question) {
        this.title = title;
        this.question = question;
    }
}
