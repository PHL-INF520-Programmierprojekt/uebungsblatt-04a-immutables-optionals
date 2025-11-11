# Übungsblatt: Immutables und Optionals

[Link to English version](./README_en.md)

In diesem Übungsblatt lernen Sie, wie Sie *unveränderliche* (immutable) Klassen und Objekte implementieren und Attribute mit *optionalen* (optional) Werten.

In Java gibt es hierfür zwei wichtige Konzepte, die Sie üben werden:

- **Immutable Klassen**: Ein Objekt ist *immutable*, wenn es nach seiner Erstellung nicht mehr verändert werden kann.  
  - Typisch: `private final` Felder, ein Konstruktor zum Setzen dieser Felder, **keine Setter-Methoden**.  
  - Vorteil: Mehr Sicherheit und Vorhersagbarkeit im Code, da Objekte nicht „heimlich“ verändert werden können.  

- **Optional**: Manchmal ist ein Wert nicht zwingend vorhanden. Anstatt dafür `null` zu verwenden, kann man `Optional` einsetzen.  
  - Beispiel: Nicht jede\*r Student\*in gibt eine **Profilbild-URL** an. In diesem Fall können wir ein `Optional<String>` verwenden, um diesen Wert sicher zu kapseln.  
  - Mit Methoden wie `orElse(...)` kann ein Ersatzwert angegeben werden, falls das `Optional` leer ist.  


Wir erstellen als Beispiel eine Klasse [`Student`](src/main/java/de/phl/programmingproject/Student.java) im Paket [`de.phl.programmingproject`](src/main/java/de/phl/programmingproject/), die immer einen Namen, eine Matrikelnummer, aber nur **optional** einen Link zum Profilbild der/des Studierenden hat.  

## Übung

### Aufgaben

1. Erweitere die Klasse [`Student`](src/main/java/de/phl/programmingproject/Student.java). Die Klasse soll **immutable** sein, das bedeutet: Alle Felder sind `private final`, es gibt **keine Setter-Methoden** und die Werte werden ausschließlich über den Konstruktor gesetzt. Die Klasse soll zwei Pflichtfelder haben: (1) `String name` und (2) `String studentId`.
2. Erweitere die Klasse `Student` um ein optionales Feld `Optional<String> profileImageUrl` (z. B. ein Link zu einem Online-Profilbild). Im Konstruktor soll `profileImageUrl` mit `Optional.ofNullable(profileImageUrl)` gesetzt werden, damit bei `null` automatisch ein `Optional.empty()` entsteht. Implementiere Getter-Methoden für alle Felder.
3. Erstelle in der `Main`-Klasse die Hilfsmethode `private static void printStudentInfo(Student student)`, die die Informationen eines Studenten ausgibt und erzeuge zwei `Student`-Objekte in der `main`-Methode:
    - Studentin **Anna** mit Matrikelnummer `"12345"` und Profil-URL `"https://example.com/anna"`  
    - Student **Tom** mit Matrikelnummer `"67890"` und **ohne** Profilbild-URL
4. Rufe für beide Studierende `printStudentInfo(...)` auf. Die Ausgabe soll so aussehen:  

   ```
   Name: Anna
   Matrikelnummer: 12345
   Profilbild-URL: https://example.com/anna.jpg

   Name: Tom
   Matrikelnummer: 67890
   Profilbild-URL: keine Angabe
   ```  