package com.kanban.back.entity;

import com.kanban.back.dto.reponseDTO.detailpageDTO.UserTableDetailDTO;
import com.kanban.back.dto.reponseDTO.joinpageDTO.UserTableJoinDTO;
import com.kanban.back.dto.reponseDTO.mainpageDTO.UserTableMainDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user_table")
@Builder
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserTable {
    @CreatedDate
    private LocalDateTime u_date_join;
    @Column(unique = true) // 중복 방지
    private String u_name;
    private String u_email;
    private String u_password;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String u_id;
    @OneToMany(mappedBy = "userTable", cascade = CascadeType.REMOVE)
    private List<CardPartner> cardPartners;

    @OneToMany(mappedBy = "userTable", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "userTable", cascade = CascadeType.REMOVE)
    private List<TmpTable> tmpTables;

    @OneToMany(mappedBy = "userTable", cascade = CascadeType.REMOVE)
    private List<BoardUser> boardUsers;

    public UserTableMainDTO toMainDTO(){
        return UserTableMainDTO.builder()
                .u_date_join(u_date_join)
                .u_name(u_name)
                .u_email(u_email)
                .u_id(u_id)
                .cardPartners(cardPartners.stream().map(s-> s.toMainDTO()).toList())
                .comments(comments.stream().map(s-> s.toMainDTO()).toList())
                .tmpTables(tmpTables.stream().map(s-> s.toMainDTO()).toList())
                .boardUsers(boardUsers.stream().map(s-> s.toMainDTO()).toList())
                .build();
    }

    public UserTableDetailDTO toDetailDTO(){
        return UserTableDetailDTO.builder()
                .u_name(u_name)
                .u_email(u_email)
                .u_id(u_id)
                .build();
    }



}