package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.demo.model.Person;

// the given annotation is same as @Component
@Repository("fakeDao")
public class FakePersonDao implements PersonDao {

	List<Person> db=new ArrayList<>();
	@Override
	public int insertPerson(UUID id, Person person) {
		db.add(new Person(id,person.getName()));
		return 1;
	}
	@Override
	public List<Person> selectAll() {
		return db;
	}
	@Override
	public Optional<Person> selectPersonById(UUID id) {
		return db.stream().filter(person->person.getId().equals(id)).findFirst();
	}

	@Override
	public int UpdatePersonById(UUID id, Person person) {
		return selectPersonById(id)
				.map(p->
				{
					int indexfound=db.indexOf(p);
					if(indexfound>=0)
					{
						db.set(indexfound,new Person(id,person.getName()));
						return 1;
					}
					return 0;
				}).orElse(null);
	}
	@Override
	public int deletePerson(UUID id) {
		Optional<Person> isthere= selectPersonById(id);
		if(isthere.isPresent())
		{
			db.remove(isthere.get());
			return 1;
		}
		return 0;
	}

}
