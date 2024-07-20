package com.courseproject.firstspringproj;

import java.time.LocalDate;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@Controller
@ComponentScan
@SessionAttributes("name")
public class TodoControllerJpa {
	
	
	public TodoControllerJpa( TodoRepository todoRepository) {
		super();
		this.todoRepository=todoRepository;
	}
	
	private TodoRepository todoRepository;

	@RequestMapping("list-todos")
	public String listTodos(ModelMap map) {
		String username=getLoggedInUsername(map);
		List<Todo> todos = todoRepository.findByUsername(username);
		map.addAttribute("todos",todos);
		return "listTodos";
	}



	private String getLoggedInUsername(ModelMap map) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication.getName();
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.GET)
	public String showNewTodoPage(ModelMap map) {
		String username=getLoggedInUsername(map);
		Todo todo=new Todo(0,username,"",LocalDate.now().plusYears(1),false);
		map.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method=RequestMethod.POST)
	public String addNewTodo( ModelMap map,@Valid @ModelAttribute("todo") Todo todo,BindingResult res) {
		if(res.hasErrors())
		{
			return "todo";
		}
		String username=getLoggedInUsername(map);
		todo.setUsername(username);
		todoRepository.save(todo);
		return "redirect:list-todos";
		
	}
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		todoRepository.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id,ModelMap model) {
		
		Todo todo=todoRepository.findById(id).get();
		model.addAttribute("todo",todo);
		return "todo";
	}
	
	@RequestMapping(value="update-todo", method=RequestMethod.POST)
	public String updateTodo( ModelMap map,@Valid @ModelAttribute("todo") Todo todo,BindingResult res) {
		if(res.hasErrors())
		{
			return "todo";
		}
		String username=getLoggedInUsername(map);
		todo.setUsername(username);
		todoRepository.save(todo);
		return "redirect:list-todos";
	}
	
}