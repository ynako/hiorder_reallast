package team.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import team.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "ordermenus",
    path = "ordermenus"
)
public interface OrdermenuRepository
    extends PagingAndSortingRepository<Ordermenu, Long> {}
