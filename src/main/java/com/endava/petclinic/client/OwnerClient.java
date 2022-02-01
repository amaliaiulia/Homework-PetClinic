package com.endava.petclinic.client;

import com.endava.petclinic.model.Owner;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.endava.petclinic.util.EnvReader.*;
import static io.restassured.RestAssured.given;

public class OwnerClient {

    public Response createOwner(Owner owner) {

        return given().baseUri(getBaseUri())
                .port(getPort())
                .basePath(getBasePath())
                .contentType(ContentType.JSON)
                .body(owner)
                .post("/api/owners");

    }

    public Response getOwnerById(Long ownerId) {

        return given().baseUri(getBaseUri())
                .port(getPort())
                .basePath(getBasePath())
                .pathParam("ownerId", ownerId)
                .get("api/owners/{ownerId}");
    }

    public Response getOwnerList() {
        return given().baseUri(getBaseUri())
                .port(getPort())
                .basePath(getBasePath())
                .get("api/owners");
    }

    public Response deleteOwnerById(Long ownerId) {
        return given().baseUri(getBaseUri())
                .port(getPort())
                .basePath(getBasePath())
                .pathParam("ownerId", ownerId)
                .delete("api/owners/{ownerId}");
    }

    public Response updateOwnerById(Long ownerId, Owner owner) {
        return given().baseUri(getBaseUri())
                .port(getPort())
                .basePath(getBasePath())
                .pathParam("ownerId", ownerId)
                .body(owner)
                .contentType(ContentType.JSON)
                .put("api/owners/{ownerId}");
    }






}
