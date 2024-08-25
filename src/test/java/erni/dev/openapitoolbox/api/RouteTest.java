package erni.dev.openapitoolbox.api;

import erni.dev.openapitoolbox.model.HikingSegment;
import erni.dev.openapitoolbox.model.Route;
import erni.dev.openapitoolbox.model.RouteSegment;
import net.jqwik.api.Property;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RouteTest {

    OpenapiValidator VALIDATOR = new OpenapiValidator("C:\\Users\\pfpa\\Code\\openapi-toolbox\\src\\main\\openapi\\hiking-routes-api.yml");

    @Test
    void testRequiredVsNullable() {

        RouteSegment hikingSegment1 = HikingSegment.builder()
                .from("Wissifluh")
                .to("Vitznauerstock")
                .build();

        Route testRoute = Route.builder()
                .id("1")
                .name("Test Route")
                .routeSegments(Collections.singletonList(hikingSegment1))
//                .mostDifficultSegment(hikingSegment1)
                .build();

        boolean result = VALIDATOR.validate(testRoute);

        assertThat(result).isTrue();
    }

    @Test
    void asdf() {

        RouteSegment hikingSegment1 = HikingSegment.builder()
                .from("Wissifluh")
                .to("Vitznauerstock")
                .build();

        Route testRoute = Route.builder()
                .id("1")
                .name("Test Route")
                .routeSegments(Collections.singletonList(hikingSegment1))
//                .mostDifficultSegment(hikingSegment1)
                .build();

        System.out.println(testRoute);
    }
}
