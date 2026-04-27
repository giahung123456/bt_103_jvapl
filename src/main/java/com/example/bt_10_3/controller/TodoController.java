package com.example.bt_10_3.controller;

import com.example.bt_10_3.entity.Todo;
import com.example.bt_10_3.repository.TodoRepository;
import jakarta.servlet.http.HttpSession;
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
    public String listTodos(Model model, HttpSession session) {
        String owner = (String) session.getAttribute("ownerName");
        if (owner == null) {
            return "redirect:/todos/login";
        }
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
        ra.addFlashAttribute("message", "Lưu dữ liệu thành công!");
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
    @GetMapping("/login")
    public String showLogin() {
        return "welcome";
    }

    @PostMapping("/login")
    public String login(@RequestParam("ownerName") String name, HttpSession session) {
        if (name == null || name.trim().isEmpty()) {
            return "redirect:/todos/login";
        }
        session.setAttribute("ownerName", name);
        return "redirect:/todos";
    }

}