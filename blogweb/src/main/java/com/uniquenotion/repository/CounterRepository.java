package com.uniquenotion.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.w3c.dom.css.Counter;

public interface CounterRepository extends MongoRepository<Counter, ObjectId> {

}