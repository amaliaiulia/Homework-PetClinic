package com.endava.petclinic;

import com.endava.petclinic.model.Owner;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FirstTest {

    @Test
    public void getOwner() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .log().all()
                .when()
                .get("api/owners")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createOwner() {

        //GIVEN
        Owner owner = new Owner("John", "Doe", "12 Oak Blvd.", "DC", "0744458744");
        System.out.println(owner);

        //WHEN
        Response response = given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .contentType(ContentType.JSON)
                .body(owner)
                .log().all()
                .when()
                .post("api/owners")
                .prettyPeek();

        //THEN
        response.then()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("lastName", is(owner.getLastName()))
                .body("firstName", is(owner.getFirstName()))
                .body("address", is(owner.getAddress()))
                .body("city", is(owner.getCity()))
                .body("telephone", is(owner.getTelephone()))
                .body("pets", empty());

        Owner actualOwner = response.as(Owner.class);
        assertThat(actualOwner, is(owner));
    }

    @Test
    public void getOwnerById() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .pathParam("ownerId", 71)
                .log().all()
                .when()
                .get("/api/owners/{ownerId}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deleteOwnerById() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .pathParam("ownerId", 71)
                .log().all()
                .when()
                .delete("/api/owners/{ownerId}")
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }

}
