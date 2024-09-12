package team.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import team.domain.*;
import team.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MenuDeleted extends AbstractEvent {

    private Long menuId;
    private String menuName;
    private String description;
    private Integer price;
    private Boolean isAvailable;
    private String category;
    private Integer discount;
    private Integer todayOrderCount;
    private Integer accOrderCount;
    private Boolean isHot;
    private String imageUrl;

    public MenuDeleted(Menu aggregate) {
        super(aggregate);
    }

    public MenuDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
