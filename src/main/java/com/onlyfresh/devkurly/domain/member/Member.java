package com.onlyfresh.devkurly.domain.member;

import com.onlyfresh.devkurly.domain.Address;
import com.onlyfresh.devkurly.domain.board.MemberLikeNo;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NotEmpty
    @Column(updatable = false, nullable = false)
    private String userNm;

    @NotEmpty
    @Email
    @Column(updatable = false, unique = true, nullable = false)
    private String userEmail;

    @NotEmpty
    private String pwd;

    private String telno;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberAuthoritiesMapping> memberAuthoritiesMappingList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @Builder.Default
    private List<Address> addressList = new ArrayList<>();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date subsDt;

    private String gender;
    private boolean prvcArge; //선택 이용 약관 동의

    @Builder.Default
    private Character userClsCd = 'U'; //유저 분류 코드

    private String rcmdrEmail; //추천인 이메일
    @Builder.Default
    private Integer pnt = 0; //페이 포인트

    public Member() {

    }
}