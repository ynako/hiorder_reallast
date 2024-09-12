package team.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import team.domain.*;
import team.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class OrderDeleted extends AbstractEvent {

    private Long orderId;

    public OrderDeleted(Ordermenu aggregate) {
        super(aggregate);
    }

    public OrderDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
