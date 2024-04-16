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
import validation.ValidationException;
import validation.Validator;

public class AppTest {

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
    }

    @Test
    public void testAddStudent() {
        service.deleteStudent("1001");
        assert service.saveStudent("1001", "a", 911) == 1;
    }

    @Test
    public void testAddStudentInvalidGroupSmall() {
        service.deleteStudent("1001");
        assert service.saveStudent("1001", "nume", 110) == 0;
    }

    @Test
    public void testAddStudentOkGroupSmall() {
        service.deleteStudent("1001");
        assert service.saveStudent("1001", "nume", 111) == 1;
    }

    @Test
    public void testAddStudentInvalidGroupBig() {
        service.deleteStudent("1001");
        assert service.saveStudent("1001", "nume", 938) == 0;
    }

    @Test
    public void testAddStudentOkGroupBig() {
        service.deleteStudent("1001");
        assert service.saveStudent("1001", "nume", 937) == 1;
    }

    @Test
    public void testAddStudentInvalidName() {
        service.deleteStudent("1001");
        assert service.saveStudent("1001", "", 911) == 0;
    }

    @Test
    public void testAddStudentOkName() {
        service.deleteStudent("1001");
        assert service.saveStudent("1001", "a", 911) == 1;
    }

    @Test
    public void testAddStudentWithExistingId() {
        service.deleteStudent("1");
        assert service.saveStudent("1", "Andrei", 911) == 1;
        assert service.saveStudent("1", "Andrei", 911) == 0;
    }

    @Test
    public void testAddStudentWithEmptyId() {
        assert service.saveStudent("", "Andrei", 911) == 0;
    }

    @Test
    public void testAddAssignmentValid() {
        service.deleteTema("10");
        assert service.saveTema("10", "desc", 11, 1) == 1;
    }

    @Test
    public void testAddAssignmentExistentId() {
        service.deleteTema("10");
        assert service.saveTema("10", "desc", 11, 1) == 1;

        try {
            service.saveTema("10", "desc", 11, 1);
            assert false;
        } catch (ValidationException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testAddAssignmentInvalidId() {
        try {
            service.saveTema("", "desc", 11, 1);
            assert false;
        } catch (ValidationException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testAddAssignmentInvalidDescription() {
        try {
            service.saveTema("10", "", 11, 1);
            assert false;
        } catch (ValidationException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testAddAssignmentInvalidDeadline() {
        try {
            service.saveTema("10", "desc", 0, 1);
            assert false;
        } catch (ValidationException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testAddAssignmentInvalidStartline() {
        try {
            service.saveTema("10", "desc", 11, 0);
            assert false;
        } catch (ValidationException e) {
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }
}
