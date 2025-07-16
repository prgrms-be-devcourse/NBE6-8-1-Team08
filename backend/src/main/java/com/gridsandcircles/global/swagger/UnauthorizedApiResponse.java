package com.gridsandcircles.global.swagger;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gridsandcircles.global.ResultResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ApiResponse(
    responseCode = "401",
    description = "인증 실패",
    content = @Content(
        mediaType = APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = ResultResponse.class),
        examples = @ExampleObject(value = """
            {
              "msg": "Password does not match",
              "data": null
            }
            """
        )
    )
)
public @interface UnauthorizedApiResponse {

}
