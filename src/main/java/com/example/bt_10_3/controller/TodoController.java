package com.example.bt_10_3.controller;

import com.example.bt_10_3.entity.Todo;
import com.example.bt_10_3.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
                           BindingResult result,
                           RedirectAttributes ra) {
        if (result.hasErrors()) {
            return "todo-form";
        }
        todoRepository.save(todo);
        ra.addFlashAttribute("message", "Lưu dữ liệu thành công!"); // Flash Attribute (20đ)
        return "redirect:/todos";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không tồn tại: " + id));

        model.addAttribute("todo", todo);
        return "todo-form";
    }
    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable("id") Long id, RedirectAttributes ra) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            ra.addFlashAttribute("message", "Đã xóa công việc thành công!");
        } else {
            ra.addFlashAttribute("message", "Lỗi: Không tìm thấy ID để xóa!");
        }
        return "redirect:/todos";
    }
}