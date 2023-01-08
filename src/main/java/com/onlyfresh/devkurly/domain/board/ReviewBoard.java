package com.onlyfresh.devkurly.domain.board;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("Rev")
@Getter @Setter
public class ReviewBoard extends Board{

    private Integer revwLike;
    private Integer likeNo;
    private String revwImg;

    public void increaseRevwLike() {
        this.revwLike += 1;
    }
}
