package springcourse.models;


import javax.validation.constraints.*;


public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Size of name should be between 2 and 30")
    private String name;


    @Min(value = 1900, message = "Year of birth is not correct")
    private int yearOfBirth;


    public Person(){}

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.yearOfBirth = year;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int year) {
        this.yearOfBirth = year;
    }

}