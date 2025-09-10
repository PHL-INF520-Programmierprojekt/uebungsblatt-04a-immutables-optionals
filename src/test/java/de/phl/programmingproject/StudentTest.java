package de.phl.programmingproject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest extends TestBase {

    @BeforeEach
    void setUp() {
        // Umleitung einschalten
        redirectSystemOut();
        // (optional) evtl. alten Inhalt leeren, falls eure Base reused:
        // byteArrayOutputStream.reset();
    }

    @AfterEach
    void tearDown() {
        resetSystemOut();
    }

    // --- Aufgabe 1: Immutable Felder + Getter ---

    @Test
    void task_1_student_has_private_final_fields_for_name_and_matrikelnummer() throws Exception {
        Field nameField = Student.class.getDeclaredField("name");
        Field matrikelField = Student.class.getDeclaredField("matrikelnummer");

        assertTrue(Modifier.isPrivate(nameField.getModifiers()), "Field 'name' must be private.");
        assertTrue(Modifier.isFinal(nameField.getModifiers()), "Field 'name' must be final.");

        assertTrue(Modifier.isPrivate(matrikelField.getModifiers()), "Field 'matrikelnummer' must be private.");
        assertTrue(Modifier.isFinal(matrikelField.getModifiers()), "Field 'matrikelnummer' must be final.");
    }

    @Test
    void task_1_constructor_sets_required_fields_and_getters_return_values() {
        Student s = new Student("Anna", "12345", null);
        assertEquals("Anna", s.getName(), "getName() should return the constructor value.");
        assertEquals("12345", s.getMatrikelnummer(), "getMatrikelnummer() should return the constructor value.");
    }

    // --- Aufgabe 2: Optional profileImageUrl ---

    @Test
    void task_2_profileImageUrl_optional_present_when_non_null() {
        Student s = new Student("Anna", "12345", "https://example.com/anna.jpg");
        Optional<String> opt = s.getProfileImageUrl();
        assertTrue(opt.isPresent(), "profileImageUrl should be present when non-null is passed.");
        assertEquals("https://example.com/anna.jpg", opt.get());
    }

    @Test
    void task_2_profileImageUrl_optional_empty_when_null() {
        Student s = new Student("Tom", "67890", null);
        Optional<String> opt = s.getProfileImageUrl();
        assertFalse(opt.isPresent(), "profileImageUrl should be Optional.empty() when null is passed.");
    }

    // --- Aufgabe 3a: Konsolenausgabe über Main.main(...) (mit Flush) ---

     @Test
    void task_3_prints_expected_output_from_main() {
        Main.main(null);
        System.out.flush();

        String output = normalizeOutput(getSystemOut());

        String expected = normalizeOutput(String.join("\n",
                "Name: Anna",
                "Matrikelnummer: 12345",
                "Profilbild-URL: https://example.com/anna.jpg",
                "",
                "Name: Tom",
                "Matrikelnummer: 67890",
                "Profilbild-URL: keine Angabe"
        ));

        assertEquals(expected, output,
                "The console output of Main.printStudentInfo(...) does not match the expected format/content.");
    }

    @Test
    void task_3_printStudentInfo_reflection_invocation_produces_expected_output() throws Exception {
        Method m = Main.class.getDeclaredMethod("printStudentInfo", Student.class);
        m.setAccessible(true);

        Student anna = new Student("Anna", "12345", "https://example.com/anna.jpg");
        Student tom  = new Student("Tom",  "67890", null);

        byteArrayOutputStream.reset();
        m.invoke(null, anna);
        // Leerzeile optional, daher bewusst NICHT erzwungen
        m.invoke(null, tom);

        System.out.flush();
        String output = normalizeOutput(getSystemOut());

        String expected = normalizeOutput(String.join("\n",
                "Name: Anna",
                "Matrikelnummer: 12345",
                "Profilbild-URL: https://example.com/anna.jpg",
                "",
                "Name: Tom",
                "Matrikelnummer: 67890",
                "Profilbild-URL: keine Angabe"
        ));

        assertEquals(expected, output,
                "The console output of printStudentInfo(Student) does not match the expected format/content.");
    }

    // ------------------------ Hilfsfunktion zur Normalisierung ------------------------

    /**
     * Normalisiert die Ausgabe:
     * - vereinheitlicht Zeilenenden auf '\n'
     * - entfernt führende/abschließende Whitespaces der gesamten Ausgabe
     * - entfernt komplett leere Zeilen (dadurch sind Varianten mit/ohne Leerzeile erlaubt)
     * - normalisiert das Label 'Profilbild-URL:' case-insensitiv auf exakt 'Profilbild-URL:'
     */
    private static String normalizeOutput(String raw) {
        if (raw == null) return "";
        String s = raw.replace("\r\n", "\n").replace("\r", "\n").trim();

        String[] lines = s.split("\n");
        List<String> kept = new ArrayList<>(lines.length);
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                // leere Zeilen ignorieren -> erlaubt mit/ohne Leerzeile
                continue;
            }
            // Case-Insensitive Normalisierung des Labels am Zeilenanfang
            // Ersetzt Varianten wie 'Profilbild-URL:', 'Profilbild-URL:', 'Profilbild-URL:' etc. durch 'Profilbild-URL:'
            String normalized = trimmed.replaceFirst("(?i)^profilbild-url\\s*:", "Profilbild-URL:");
            kept.add(normalized);
        }
        return String.join("\n", kept);
    }
}
