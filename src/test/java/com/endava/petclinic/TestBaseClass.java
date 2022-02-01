package com.endava.petclinic;

import com.endava.petclinic.client.OwnerClient;
import com.endava.petclinic.client.PetClient;
import com.endava.petclinic.testData.TestDataProvider;

public class TestBaseClass {

    protected OwnerClient ownerClient = new OwnerClient();
    protected TestDataProvider testDataProvider = new TestDataProvider();
    protected PetClient petClient = new PetClient();
}
