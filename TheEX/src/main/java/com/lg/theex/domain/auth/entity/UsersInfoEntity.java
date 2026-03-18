package com.lg.theex.domain.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "users_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UsersInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_phone")
    private String userPhone;

    @Column(name = "password", nullable = false)
    private String password;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "machine_no", columnDefinition = "JSON")
    private String machineNo;

    @Column(name = "user_nickname", nullable = false)
    private String userNickname;

    @Builder
    private UsersInfoEntity(
            Long userId,
            String userName,
            String userEmail,
            String userPhone,
            String password,
            String machineNo,
            String userNickname
    ) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.password = password;
        this.machineNo = machineNo;
        this.userNickname = userNickname;
    }
}