package team.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import team.domain.*;

@Component
public class OrdermenuHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Ordermenu>> {

    @Override
    public EntityModel<Ordermenu> process(EntityModel<Ordermenu> model) {
        return model;
    }
}
