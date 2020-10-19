package cz.tut.rohlik.rohlikdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "item")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
public class Item extends AbstractAuditing{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @PositiveOrZero
    @Column(name = "price")
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    @Column(name = "count")
    private Integer count;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_category_id")
    private ItemCategory itemCategory;

    @OneToMany(mappedBy = "item", orphanRemoval = true,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItem> items = new ArrayList<>();
}

