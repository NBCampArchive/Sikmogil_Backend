package com.examle.sikmogilbackend.community.board.domain;

import lombok.Getter;

@Getter
public enum Category {
    DIET("다이어트"),
    WORKOUT("운동"),
    FREE("자유");

    private final String name;

    Category(String name) {
        this.name = name;
    }

}
