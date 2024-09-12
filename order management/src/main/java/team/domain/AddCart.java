package team.domain;

import java.util.*;
import lombok.*;
import team.domain.*;
import team.infra.AbstractEvent;

@Data
@ToString
public class AddCart extends AbstractEvent {

    private Long orderId;
    private String menuId;
    private Date createdAt;
    private String orderStatus;
    private Integer quantity;
    private Integer tableIdx;
}
