package rpalcolea

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PersonController {

    @Autowired
    PersonRepository repository

    @RequestMapping("/person/list")
    @ResponseBody
    ResponseEntity<Person> list() {
        new ResponseEntity<List<Person>>(repository.findAll(), HttpStatus.OK)
    }

    @RequestMapping("/person/find/{name}")
    @ResponseBody
    ResponseEntity<Person> find(@PathVariable String name) {
        def person = repository.findByName(name)
        if(!person)
            throw new Exception("Person not found: $name")

        new ResponseEntity<Person>(person, HttpStatus.OK)
    }

    @RequestMapping("/person/add/{name}")
    @ResponseBody
    ResponseEntity<Person> add(@PathVariable String name) {
        if(repository.findByName(name))
            throw new Exception("Person $name already exists")

        def person = repository.save(new Person(name: name))
        if(!person)
            throw new Exception("Person not created: $name")
        new ResponseEntity<Person>(person, HttpStatus.OK)
    }

    @RequestMapping("/person/delete/{id}")
    @ResponseBody
    ResponseEntity<Person> delete(@PathVariable BigInteger id) {
        Person person = repository.findOne(id)
        if(!person)
            throw new Exception("Person with $id doesn't exist")

        repository.delete(person)
        response.setStatus(HttpStatus.OK)
    }

    @ExceptionHandler
    ResponseEntity<String> handle(Exception e) {
        new ResponseEntity<String>(e.message, HttpStatus.NOT_FOUND)
    }

}