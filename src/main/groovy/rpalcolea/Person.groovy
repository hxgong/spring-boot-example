package rpalcolea
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Person {
    @Id BigInteger id
    String name
}