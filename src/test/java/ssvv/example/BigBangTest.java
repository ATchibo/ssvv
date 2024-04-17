package ssvv.example;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

public class BigBangTest {

    Service service;

    @BeforeEach
    public void setup() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

        service = new Service(fileRepository1, fileRepository2, fileRepository3);

        service.deleteStudent("1001");
        service.deleteTema("10");

        fileRepository1.delete("1");
        Student student = new Student("1", "a", 911);
        fileRepository1.save(student);

        fileRepository2.delete("2");
        Tema tema = new Tema("2", "a", 10, 5);
        fileRepository2.save(tema);
    }

    @Test
    public void testAddStudent() {
        assert service.saveStudent("1001", "a", 911) == 1;
    }

    @Test
    public void testAddAssignment() {
        assert service.saveTema("10", "a", 10, 5) == 1;
    }

    @Test
    public void testAddGrade() {
        assert service.saveNota("1", "2", 10, 5, "a") == 1;
    }

    @Test
    public void testAll() {
        testAddStudent();
        testAddAssignment();
        assert service.saveNota("1001", "10", 10, 5, "a") == 1;
    }
}
