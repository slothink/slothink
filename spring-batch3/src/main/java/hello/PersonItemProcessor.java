package hello;

import org.springframework.batch.item.ItemProcessor;

/**
 * Created by slothink on 2014-09-29.
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(Person person) throws Exception {
        Person to = new Person(person.getFirstName().toUpperCase(), person.getLastName().toUpperCase());
        System.out.println(to);
        return to;
    }
}
