package com.endava.petclinic.owner;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.withArgs;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class GetOwnerListTest extends TestBaseClass {

    @Test
    public void shouldGetOwnerList() {

        //GIVEN
        Owner owner = testDataProvider.getOwner();
        Response createOwnerResponse = ownerClient.createOwner(owner);
        createOwnerResponse.then().statusCode(HttpStatus.SC_CREATED);
        Long ownerId = createOwnerResponse.body().jsonPath().getLong("id");

        //WHEN
        Response response = ownerClient.getOwnerList();

        //THEN - trebuie sa verific ca ownerul creat, ultimul owner, se afla in lista; avem 3 alternative sa putem valida o lista de jsoane

        //1. validez fiecare field in parte folosind body
        response.prettyPeek().then().statusCode(HttpStatus.SC_OK)
                .body("find{ it -> it.id == %s }.firstName", withArgs(ownerId), is(owner.getFirstName()));
        // incearca sa gaseasca entitatea cu un anumit id si de entitate sa ia firstName
        // it reprezinta fiecare json in parte; vreau sa caut it-ul care are id-ul ownerului creat
        // %s se inlocuieste cu ownerId prin withArgs


        //2. caut entitatea care imi trebuie mie din acel json/lista si validez ca e ceea ce trebuie
        Owner actualOwner = response.body().jsonPath().param("id", ownerId).getObject("find{ it -> it.id == id}", Owner.class);
        assertThat(actualOwner, is(owner));

        //3. serializez intr-o lista de obiecte si verific ca lista contine ceea ce imi trebuie mie
        List<Owner> ownerList = response.body().jsonPath().getList("", Owner.class);
        assertThat(ownerList, hasItem(owner));

    }

}