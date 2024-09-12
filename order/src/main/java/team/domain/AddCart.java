package team.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import team.domain.*;
import team.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AddCart extends AbstractEvent {

    private Long orderId;
    private String menuId;
    private Date createdAt;
    private String orderStatus;
    private Integer quantity;
    private Integer tableIdx;

    public AddCart(Order aggregate) {
        super(aggregate);
    }

    public AddCart() {
        super();
    }
}
//>>> DDD / Domain Event
