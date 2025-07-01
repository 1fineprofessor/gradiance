package com.Demo.webAppDemo2.demo.controller;

import com.Demo.webAppDemo2.demo.model.Question;
import com.Demo.webAppDemo2.demo.model.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class InstructorController {

    @GetMapping("instructor")
    public String getInstructor(Map<String, Object> model) {
        List<Student> students = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        //This will be replaced with database calls
        for (int i = 0; i < 10; i++) {
            students.add(new Student("John Doe", "CS 252", "Knowledge Review"));
            students.add(new Student("Jane Smith", "CS 500", "Wiki Contribution"));
        }
        for (int i = 0; i < 10; i++) {
            questions.add(new Question("When would you use a linked list instead of an array list","12345","Medium","Linked-List","5 Minutes" ));
        }

        model.put("students", students);
        model.put("questions", questions);
        model.put("test", "test");
        return "instructor";
    }
}
