package com.endava.petclinic;

import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PetTest {

    @Test
    public void petTest() {

        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .log().all()    //printeaza toate informatiile direct din request
                .when()
                .get("api/pets")
                .prettyPeek()   //printeaza response
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

/*    @Test
    public void createPet() {

        PetType dog = new PetType(3L);
        Owner owner = new Owner(2L);

        Pet pet = new Pet("Misi", "2021/10/14", dog, owner);
        System.out.println(pet);

        given().baseUri("http://bhdtest.endava.com")
                .port(8080)
                .basePath("petclinic")
                .contentType(ContentType.JSON)
                .body(pet)
                .log().all()
        .when()
                .post("api/pets")
                .prettyPeek()
        .then()
                .statusCode(HttpStatus.SC_CREATED);
    }*/

/*
    @Test
    public void createPet() {

        PetType dog = new PetType(3L);
        Owner owner = new Owner(2L);

        Pet pet = new Pet("Misi", "2021/10/14", dog, owner);
        System.out.println(pet);

        given().baseUri("http://bhdtest.endava.com")
                .port(8080)
                .basePath("petclinic")
                .contentType(ContentType.JSON)
                .body(pet)
                .log().all()
        .when()
                .post("api/pets")
                .prettyPeek()
        .then()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("name", is(pet.getName()))
                .body("birthDate", is(pet.getBirthDate()))
                .body("type.id", is(pet.getType().getId().intValue()))
                .body("owner.id", is(pet.getOwner().getId().intValue()))
                .body("visits", empty());
    }
*/


    //deserializare
    @Test
    public void createPet() {

        //GIVEN
        PetType dog = new PetType();
        Owner owner = new Owner();

        Pet pet = new Pet("Misi", "2021/10/14", dog, owner);
        System.out.println(pet);

        //WHEN
        Response response = given().baseUri("http://bhdtest.endava.com")
                .port(8080)
                .basePath("petclinic")
                .contentType(ContentType.JSON)
                .body(pet)
                .log().all()
                .when()
                .post("api/pets")
                .prettyPeek();

        //THEN
        response.then()
                .statusCode(HttpStatus.SC_CREATED)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("name", is(pet.getName()))
                .body("birthDate", is(pet.getBirthDate()))
                .body("type.id", is(pet.getType().getId().intValue()))
                .body("owner.id", is(pet.getOwner().getId().intValue()))
                .body("visits", empty());

        Pet actualPet = response.as(Pet.class);
        assertThat(actualPet, is(pet));

    }


    @Test
    public void getPetById() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .pathParam("petId", 32)
                .log().all()
                .when()
                .get("api/pets/{petId}")  //am nevoie de pathParam
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void deletePetById() {
        given().baseUri("http://bhdtest.endava.com/")
                .port(8080)
                .basePath("petclinic")
                .pathParam("petId", 33)
                .log().all()
                .when()
                .delete("api/pets/{petId}")  //am nevoie de pathParam
                .prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

    }


}