package team.infra;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import team.domain.*;

@Component
public class MenuHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Menu>> {

    @Override
    public EntityModel<Menu> process(EntityModel<Menu> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "//menudelete")
                .withRel("/menudelete")
        );

        return model;
    }
}
