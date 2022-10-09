package ch.keepcalm.demo.archunit

import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.config.EnableHypermediaSupport
import org.springframework.hateoas.server.reactive.WebFluxLinkBuilder
import org.springframework.hateoas.support.WebStack
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlinx.coroutines.reactive.awaitSingle

@RestController
@RequestMapping(produces = [MediaTypes.HAL_JSON_VALUE])
@EnableHypermediaSupport(stacks = [WebStack.WEBFLUX], type = [EnableHypermediaSupport.HypermediaType.HAL])
class IndexController() {
    companion object REL {
        const val REL_SPRING_INITIALIZR = "start-spring"
    }

    @GetMapping("/")
    suspend fun index(): EntityModel<Unit> {
        return EntityModel.of(Unit, WebFluxLinkBuilder.linkTo(WebFluxLinkBuilder.methodOn(IndexController::class.java).index()).withSelfRel().toMono().awaitSingle())
            .add(Link.of("http://start.spring.io").withRel(REL_SPRING_INITIALIZR))
    }
}
