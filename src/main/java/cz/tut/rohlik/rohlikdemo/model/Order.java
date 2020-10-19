package cz.tut.rohlik.rohlikdemo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Table(name = "\"order\"")
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(callSuper = false)
@Data
public class Order extends AbstractAuditing {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @NotNull
    @PositiveOrZero
    @Column(name = "price_sum")
    private BigDecimal priceSum;

    @ToString.Exclude
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "deleted",columnDefinition = "boolean default false")
    private Boolean deleted;

    @OneToMany(mappedBy = "order", orphanRemoval = true,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItem> items = new ArrayList<>();

    public void updateItems(List<OrderItem> updatedItems) {
        Map<String, OrderItem> orderItemMap =
                updatedItems.stream().collect(Collectors.toMap(OrderItem::getId, ui -> ui));
        List<OrderItem> orderItemToRemove =
                this.items.stream().filter(oi -> !orderItemMap.containsKey(oi.getId())).collect(
                        Collectors.toList());
        this.items.removeAll(orderItemToRemove);
        for (OrderItem item : items) {
            OrderItem updatedOrderItem = orderItemMap.get(item.getId());
            BeanUtils.copyProperties(updatedOrderItem, item);
        }
        updatedItems.removeAll(items);
        items.addAll(updatedItems);
    }
}
