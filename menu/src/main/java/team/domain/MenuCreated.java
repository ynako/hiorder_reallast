package team.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import team.domain.*;
import team.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class MenuCreated extends AbstractEvent {

    private Long menuId;
    private String description;
    private Integer price;
    private Boolean isAvailable;
    private String category;
    private Integer discount;
    private Integer todayOrderCount;
    private Integer accOrderCount;
    private Boolean isHot;
    private String imageUrl;
    private String menuName;

    public MenuCreated(Menu aggregate) {
        super(aggregate);
    }

    public MenuCreated() {
        super();
    }
}
//>>> DDD / Domain Event
