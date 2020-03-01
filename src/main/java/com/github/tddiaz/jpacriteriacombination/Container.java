package com.github.tddiaz.jpacriteriacombination;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Container {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String moveType;

    private String status;

    public Container(String number, String moveType, String status) {
        this.number = number;
        this.moveType = moveType;
        this.status = status;
    }

    public static Container create(String number, String moveType, String status) {
        return new Container(number, moveType, status);
    }
}
