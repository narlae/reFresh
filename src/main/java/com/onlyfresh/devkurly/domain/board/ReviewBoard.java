package com.onlyfresh.devkurly.domain.board;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue("Rev")
@Getter
public class ReviewBoard extends Board{

    private Integer revw_like;
    private Integer like_no;
    private String revw_img;

}
