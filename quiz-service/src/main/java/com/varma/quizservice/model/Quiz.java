package com.varma.quizservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

/**
 * The Quiz class represents a quiz entity.
 */
@Entity
@Data
/**
*The @Data annotation is from the Lombok library
*  and automatically generates getter and setter methods,
*  toString(), equals(), and hashCode() methods for the class.
*/
public class Quiz {
    /**
     * id: The ID of the quiz. It is annotated with @Id to indicate that it is the primary key of the entity.
     * The @GeneratedValue annotation specifies the strategy for generating the ID values, in this case, using an identity column
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    /**
     * @ManyToMany to indicate a many-to-many relationship with the Question entity.
     */
    @ElementCollection
    private List<Integer> questions;
}