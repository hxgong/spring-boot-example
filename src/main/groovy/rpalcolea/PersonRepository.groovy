package rpalcolea

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository extends MongoRepository<Person, BigInteger> {
    Person findByName(String name)
}