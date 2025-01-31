package com.fdmgroup.backend_streamhub.watchpartysession.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fdmgroup.backend_streamhub.authenticate.model.Account;
import com.fdmgroup.backend_streamhub.videostream.model.Video;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WatchParty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private boolean reminderEmailSent = false;
    private String partyName;
    private String code;

    // Add password to authenticate users joining a watchparty
    private String password;

    private String scheduledDate;
    private String scheduledTime;

    private LocalDate createdDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "video_id")
    @JsonIgnore
    private Video video;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Account account;

    @OneToOne(mappedBy = "watchParty")
    @JsonIgnore
    private Poll poll;

    @PrePersist
    protected void onCreate() {
        this.code = generateCode();
        this.createdDate = LocalDate.now();
    }

    private String generateCode() {
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

}