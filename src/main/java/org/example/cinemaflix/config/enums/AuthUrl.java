package org.example.cinemaflix.config.enums;

import lombok.Getter;

@Getter
public enum AuthUrl {

    ROLE_ADMIN(new String[]{
            "/reservations",
            "/customer",
            "/customer/**",
            "/payments",
            "/paymentMovies"
    }),
    ROLE_USER(new String[]{
            "/paymentMovies/pay"
    }),
    PERMIT_ALL(new String[]{
            "/api/v1/auth/",
            "/forgotPassword/**",
            "/reservations/reserve",
            "/reservations/change/**",
            "/authenticate",
            "/authenticate/**",
            "/movies",
            "/movies/info/**",
            "/movies/upcoming/**",
            "/movies/category/**",
            "/schedules",
            "/schedules/{id}",
            "/schedules/byCinema/**",
            "/schedules/byTime/**",
            "/payments/pay",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/swagger-ui/**",
            "/swagger-ui.html"
    });

    private final String[] url;

    AuthUrl(String[] url){
        this.url=url;
    }

}
