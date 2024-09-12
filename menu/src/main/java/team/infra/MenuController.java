package team.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/menus")
@Transactional
public class MenuController {

    @Autowired
    MenuRepository menuRepository;

    @RequestMapping(
        value = "/menus/{id}//menudelete",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public Menu menuDelete(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /menu/menuDelete  called #####");
        Optional<Menu> optionalMenu = menuRepository.findById(id);

        optionalMenu.orElseThrow(() -> new Exception("No Entity Found"));
        Menu menu = optionalMenu.get();
        menu.menuDelete();

        menuRepository.delete(menu);
        return menu;
    }
}
//>>> Clean Arch / Inbound Adaptor
