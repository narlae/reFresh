package com.onlyfresh.devkurly.domain.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Inq")
@Getter @Setter
public class InqBoard extends Board{

    private boolean isSecret;
    private boolean isReplied;
}
