package com.endava.petclinic.owner;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class DeleteOwnerTest extends TestBaseClass {

    @Test
    public void shouldDeleteOwner() {

        //GIVEN - ca sa sterg owner, trebuie sa creez owner
        Owner owner = new Owner("John", "Cena", "TX", "Texas", "086564455");
        Response createOwnerResponse = ownerClient.createOwner(owner);
        createOwnerResponse.then().statusCode(HttpStatus.SC_CREATED);
        //iau id-ul de pe body
        Long ownerId = createOwnerResponse.body().jsonPath().getLong("id");

        //WHEN - sterg ownerul nou creat
        Response response1 = ownerClient.deleteOwnerById(ownerId);

        //THEN
        response1.then().statusCode(HttpStatus.SC_NO_CONTENT);

    }


}
