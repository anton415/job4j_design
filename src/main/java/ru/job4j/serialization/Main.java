package ru.job4j.serialization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.question.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Computer.class.getName());

    public static void main(String[] args) throws Exception {
        Computer computer = new Computer(true, 5, "Silver", new User(0, "Anton"), new String[] {"Chrome", "IDEA"});
        JAXBContext context = JAXBContext.newInstance(Computer.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        String xml = "";
        try (StringWriter writer = new StringWriter()) {
            marshaller.marshal(computer, writer);
            xml = writer.getBuffer().toString();
            LOG.debug(xml);
        }
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (StringReader reader = new StringReader(xml)) {
            Computer result = (Computer) unmarshaller.unmarshal(reader);
            LOG.debug(result.toString());
        }
    }
}