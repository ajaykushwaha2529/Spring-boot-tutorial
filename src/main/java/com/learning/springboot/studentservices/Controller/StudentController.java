package com.learning.springboot.studentservices.Controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learning.springboot.studentservices.StudentService;
import com.learning.springboot.studentservices.model.Course;

@RestController
@RequestMapping("/students/{studentId}/courses")
public class StudentController {
    
     @Autowired
    private StudentService studentService;

    @GetMapping()
    public List<Course> retrieveCoursesForStudent(@PathVariable String studentId) {
        return studentService.retrieveCourses(studentId);
    }

    @GetMapping("/{courseId}")
    public Course retrieveDetailsForCourse(
            @PathVariable String studentId,
            @PathVariable String courseId) {

        return studentService.retrieveCourse(studentId, courseId);
    }

    @PostMapping()
    public ResponseEntity<Void> registerStudentForCourse(
            @PathVariable String studentId,
            @RequestBody Course newCourse) {

        Course course = studentService.addCourse(studentId, newCourse);

        if (course == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.id())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    // Update a Course

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> updateCourse(
        @PathVariable String studentId,
        @PathVariable String courseId,
        @RequestBody Course updatedCourse){
            // Course removeCourse = studentService.retrieveCourse(studentId, courseId);
            studentService.removeCourse(studentId, courseId);
            Course course = studentService.addCourse(studentId, updatedCourse);

        if (course == null)
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(course);
        
            
        }
                
               
    // Delete a Course
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Course> deleteCourse(
        @PathVariable String studentId,
        @PathVariable String courseId){
            Course deletedCourse = studentService.removeCourse(studentId, courseId);
            if(deletedCourse != null){
                return ResponseEntity.ok(deletedCourse);}
                else{return ResponseEntity.notFound().build();}

        }





            
            
    
}
