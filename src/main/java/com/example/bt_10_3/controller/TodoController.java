package com.example.bt_10_3.controller;

import com.example.bt_10_3.entity.Todo;
import com.example.bt_10_3.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public String listTodos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "todo-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "todo-form";
    }

    @PostMapping("/add")
    public String saveTodo(@Valid @ModelAttribute("todo") Todo todo,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "todo-form";
        }
        todoRepository.save(todo);
        return "redirect:/todos";
    }
}