package com.courseproject.firstspringproj;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoService {
	private static List<Todo> todos=new ArrayList<>();
	private static int todoCount=0;
	static {
		todos.add(new Todo(++todoCount,"CodeCooks","DSA with Java 1",LocalDate.now().plusYears(1),false));
		todos.add(new Todo(++todoCount,"CodeCooks","Learn React.JS 1",LocalDate.now().plusYears(2),false));
		todos.add(new Todo(++todoCount,"CodeCooks","C++ 1",LocalDate.now().plusYears(3),false));
		todos.add(new Todo(++todoCount,"CodeCooks","Data Science with R 1",LocalDate.now().plusYears(4),false));
		
	}
	
	public List<Todo> findByUsername(String username){
		Predicate<? super Todo> predicate = todo -> todo.getUsername()==username;

		return todos.stream().filter(predicate).toList();
	}
	
	public void addCourse(String username, String description, LocalDate targetDate, boolean done) {
		Todo todo = new Todo(++todoCount,username,description,targetDate,done);
		todos.add(todo);
		
	}
	
	public void deleteById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId()==id;
		todos.removeIf(predicate);
		
	}

	public Todo findById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId()==id;
		Todo todo=todos.stream().filter(predicate).findFirst().get();
		return todo;
	}
	public void updateTodo(@Valid Todo todo) {
		deleteById(todo.getId());
		todos.add(todo);
		
	}
}
