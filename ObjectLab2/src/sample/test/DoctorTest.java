package sample.test;

import org.junit.*;
import sample.entity.Doctor;
import sample.exception.NoFileException;
import sample.report.PdfReport;


public class DoctorTest {

    @BeforeClass // Фиксируем начало тестирования
    public static void allTestsStarted() {
        System.out.println("Начало тестирования");
    }
    @AfterClass // Фиксируем конец тестирования
    public static void allTestsFinished() {
        System.out.println("Конец тестирования");
    }
    @Before // Фиксируем запуск теста
    public void testStarted() {
        System.out.println("    Запуск теста");
    }
    @After // Фиксируем завершение теста
    public void testFinished() {
        System.out.println("    Завершение теста");
    }

    @Test(expected = NoFileException.class)
    public void createReport() throws NoFileException {
        PdfReport report = new PdfReport(null,null,null);
        report.pdfSave();
    }

    @Test
    public void emptyDoctorWithId(){
        Doctor doc = new Doctor(1);
        Assert.assertEquals("", doc.getSpecialty());
    }

}
