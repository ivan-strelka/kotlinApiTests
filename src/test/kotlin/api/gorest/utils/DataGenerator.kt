package api.gorest.utils

import api.gorest.constants.Gender
import api.gorest.constants.Status

fun createRandomEmail(): String {
    return faker.name().firstName() + faker.random().nextInt(1, Int.MAX_VALUE) + "@" + faker.name().username() + ".com"
}

fun createRandomInt(): Int {
    return faker.number().numberBetween(1, Int.MAX_VALUE)
}

fun createRandomName(): String {
    return faker.name().firstName()
}

fun getGenderFemale(): String {
    return Gender.female.toString()
}

fun getGenderMale(): String {
    return Gender.male.toString()
}

fun getActiveStatus(): String {
    return Status.active.toString()
}

fun getInactiveStatus(): String {
    return Status.inactive.toString()
}