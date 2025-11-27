package hu.unideb.inf.ingredius.data.model;

import hu.unideb.inf.ingredius.data.model.util.Categories;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "foods")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Categories name;

    @Column(length = 100)
    private String description;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Food> foods;
}
