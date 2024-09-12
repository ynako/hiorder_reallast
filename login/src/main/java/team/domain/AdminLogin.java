package team.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import team.domain.*;
import team.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AdminLogin extends AbstractEvent {

    private String userId;
    private String type;
    private String password;
    private String userName;
    private Integer tableCnt;

    public AdminLogin(User aggregate) {
        super(aggregate);
    }

    public AdminLogin() {
        super();
    }
}
//>>> DDD / Domain Event
