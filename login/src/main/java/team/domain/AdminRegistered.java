package team.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import team.domain.*;
import team.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AdminRegistered extends AbstractEvent {

    private String userId;
    private String type;
    private String password;
    private String userName;
    private Integer tableCnt;

    public AdminRegistered(User aggregate) {
        super(aggregate);
    }

    public AdminRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
