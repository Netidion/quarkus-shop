package com.vnet.lab.resource;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Neti on 3/17/2024
 */
@Path("/user")
@Authenticated
@Tag(name = "user", description = "All the user methods")
public class UserResource {

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/current/info")
    public JsonWebToken getCurrentUserInfo(){
        return jwt;
    }

    @GET
    @Path("/current/info/claims")
    public Map<String, Object> getCurrentUserInfoClaims(){
        return jwt.getClaimNames()
                .stream()
                .map(name-> Map.entry(name, jwt.getClaim(name)))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue)
                );
    }

}
