package tez.messaging.streams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class StreamsApplication {

	public static void main(String[] args) {
		SpringApplication.run(StreamsApplication.class, args);
	}

    @Bean
    public Consumer<Person> log() {
        return person -> {
            System.out.println("Received: " + person);
        };
    }

    @Bean
    public Supplier<Person> stringSupplier() {
        return () -> {
            List<String> list = Stream.of("Anjali", "Bipin", "Catherine")
                    .collect(Collectors.toList());
            Person person = new Person();
            Random random = new Random();
            int nextName = random.nextInt(3);
            person.setName(list.get(nextName));
            System.out.println("Publishing: " + person.getName());
            return person;
        };
    }

    @Bean
    public Function<Person, Person> upperCase() {
        return inPerson -> {
            System.out.println("Consumed " + inPerson + " Changing to Upper case");
            System.out.println("Converted and publishing: " + inPerson.getName().toUpperCase());
            inPerson.setName(inPerson.getName().toUpperCase());
            return inPerson;
        };
    }

    public static class Person {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String toString() {
            return this.name;
        }
    }

}
