package ru.job4j.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.question.User;

import javax.xml.bind.annotation.*;
import java.io.IOException;
import java.util.Arrays;

@XmlRootElement(name = "computer")
@XmlAccessorType(XmlAccessType.FIELD)
public class Computer {
    private static final Logger LOG = LoggerFactory.getLogger(Computer.class.getName());
    private static final long serialVersionUID = 1L;
    @XmlAttribute
    private boolean isTurnOn;
    @XmlAttribute
    private int age;
    @XmlAttribute
    private String color;
    @XmlElement(name = "user")
    private User user;
    @XmlElementWrapper(name = "programs")
    @XmlElement(name = "program")
    private String[] programs;

    public Computer() {
    }

    public Computer(boolean isTurnOn, int age, String color, User user, String[] programs) {
        this.isTurnOn = isTurnOn;
        this.age = age;
        this.color = color;
        this.user = user;
        this.programs = programs;
    }

    @Override
    public String toString() {
        return "Computer{" + "isTurnOn=" + isTurnOn + ", age=" + age + ", name='" + color + '\'' + ", user=" + user
                + ", programs=" + Arrays.toString(programs) + '}';
    }

    public boolean isTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(boolean turnOn) {
        isTurnOn = turnOn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String[] getPrograms() {
        return programs;
    }

    public void setPrograms(String[] programs) {
        this.programs = programs;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final Computer computer = new Computer(true, 5, "Silver", new User(0, "Anton"), new String[] {"Chrome", "IDEA"});
        final Gson gson = new GsonBuilder().create();
        LOG.debug(gson.toJson(computer));

        final String computerJson =
                "{"
                        + "\"isTurnOn\":true,"
                        + "\"age\":5,"
                        + "\"color\":\"Silver\","
                        + "\"user\":"
                        + "{"
                        + "\"id\":0,"
                        + "\"name\":\"Anton\""
                        + "},"
                        + "\"programs\":"
                        + "[\"Chrome\",\"IDEA\"]"
                        + "}";
        final Computer computerMod = gson.fromJson(computerJson, Computer.class);
        LOG.debug(computerMod.toString());
    }
}
