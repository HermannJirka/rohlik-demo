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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_item")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode
public class OrderItem {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotNull
    @PositiveOrZero
    @Column(name = "count")
    private Integer count;

    @NotNull
    @PositiveOrZero
    @Column(name = "item_price")
    private BigDecimal itemPrice;

    @NotNull
    @PositiveOrZero
    @Column(name = "item_price_sum")
    private BigDecimal itemPriceSum;

    @ToString.Exclude
    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ToString.Exclude
    @NotNull
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
