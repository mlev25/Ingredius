package hu.unideb.inf.ingredius.data.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "allergens")
@Data
@EqualsAndHashCode(exclude = "foods")
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Severities severity;

    @ManyToMany(mappedBy = "allergens")
    private Set<Food> foods;
}
