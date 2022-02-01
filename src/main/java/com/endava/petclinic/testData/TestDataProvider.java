package com.endava.petclinic.testData;

import com.endava.petclinic.model.Owner;
import com.endava.petclinic.model.Pet;
import com.endava.petclinic.model.PetType;
import com.github.javafaker.Faker;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestDataProvider {

    private Faker faker = new Faker();

    //se genereaza o entitate de tip owner
    public Owner getOwner() {
        Owner owner = new Owner();
        owner.setFirstName(faker.name().firstName());
        owner.setLastName(faker.name().lastName());
        owner.setAddress(faker.address().fullAddress());
        owner.setCity(faker.address().city());
        owner.setTelephone(faker.number().digits(faker.number().numberBetween(1, 11)));
        return owner;
    }

    public String getNumberWithDigits(int min, int max) {
        return faker.number().digits(faker.number().numberBetween(min, max));
    }


    //se genereaza o entitate de tip pet
    public Pet getPet(Owner owner, PetType type) {
        //facem formatul pt birthDate; convertim dintr-un date generat de faker intr-un localDate, apoi cu .format convertim in string
        String birthDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        Pet pet = new Pet();
        pet.setName(faker.artist().name());
        pet.setBirthDate(birthDate);
        pet.setOwner(owner);  //intai creez owner, apoi pe acel owner ii asignez un pet
        pet.setType(type);

        return pet;

    }


}
