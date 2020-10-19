package cz.tut.rohlik.rohlikdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Entity
@Table(name = "item_category")
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class ItemCategory {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotBlank
    private String categoryName;

    @ToString.Exclude
    @OneToMany(mappedBy = "itemCategory")
    private List<Item> items;
}
