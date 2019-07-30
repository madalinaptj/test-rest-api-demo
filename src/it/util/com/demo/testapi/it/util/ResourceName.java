package com.demo.testapi.it.util;

public enum ResourceName {

    ALLERGY_INTOLERANCE("AllergyIntolerance"),

    CARE_TEAM("CareTeam"),

    CONDITION("Condition"),

    EPISODE_OF_CARE("EpisodeOfCare"),

    PATIENT("Patient"),

    ENCOUNTER("Encounter"),

    ORGANIZATION("Organization"),

    PRACTITIONER("Practitioner"),

    LOCATION("Location");

    private final String value;

    ResourceName(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
