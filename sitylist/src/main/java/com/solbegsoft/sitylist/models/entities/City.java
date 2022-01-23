package com.solbegsoft.sitylist.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * City entity
 */
@Entity
@Table(name = "cities")
// created index c_name on name column
@Data
@NoArgsConstructor
public class City implements Serializable {

    /**
     * id
     */
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    @GeneratedValue
    private UUID id;

    /**
     * city name
     */
    @Column(name = "name")
    private String name;

    /**
     * photo path
     */
    @Column(name = "photo_path", length = 800)
    private String photoPath;
}