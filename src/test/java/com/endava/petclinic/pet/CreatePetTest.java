package com.endava.petclinic.pet;

import com.endava.petclinic.TestBaseClass;
import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class CreatePetTest extends TestBaseClass {

    @Test
    public void shouldCreatePet() {
        //GIVEN

        //Pt a crea un pet, prima data va trebui sa cream un owner!! se va genera owner fara id
        Owner owner = testDataProvider.getOwner();
        //apelam api-ul de add owner (createOwner), deci creez owner in baza de date
        Response createOwnerResponse = ownerClient.createOwner(owner);
        //validez ca createOwnerResponse este creat
        createOwnerResponse.then().statusCode(HttpStatus.SC_CREATED);
        //iau id-ul de pe body si sa-l adaug pe owner-ul creat
        long id = createOwnerResponse.body().jsonPath().getLong("id");
        //setez id-ul pe owner
        owner.setId(id);

        //voi crea pet-ul folosindu-ma de owner-ul creat; pt asta trebuie sa cream un PetType pe care sa il asignam pet-ului nostru
        //nu avem inca mapat api-ul de createPetType, vom face o instanta de PetType
        PetType petType = new PetType();
        //am luat un petType existent din baza de date; id-ul 1->cat
        petType.setId(1L);

        Pet pet = testDataProvider.getPet(owner,petType);

        //WHEN - se apeleaza petClient -> a fost instantiat in TestBaseClass
        Response response = petClient.createPet(pet);

        //THEN - verificam ca s-a creat pet
        response.prettyPeek().then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    public void shouldFailToCreatePetGivenEmptyName() {
        //GIVEN
        Owner owner = testDataProvider.getOwner();
        Response responseOwner = ownerClient.createOwner(owner);
        responseOwner.then().statusCode(HttpStatus.SC_CREATED);
        long id = responseOwner.body().jsonPath().getLong("id");
        owner.setId(id);

        PetType petType = new PetType();
        petType.setId(3L);

        Pet pet =testDataProvider.getPet(owner, petType);
        pet.setName("");
        //WHEN
        Response response = petClient.createPet(pet);
        //THEN
        response.prettyPeek().then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }



}
