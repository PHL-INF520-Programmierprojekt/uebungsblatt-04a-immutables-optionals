# Exercise Sheet: Immutables und Optionals

[Link to German Version](./README.md)

In this exercise sheet, you will learn how to implement *immutable* classes and objects and attributes with *optional* values.

In Java, there are two important concepts for this that you will practice:

- **Immutable Classes**: An object is *immutable* if it cannot be changed after it has been created.
  - Typical: `private final` fields, a constructor to set these fields, **no setter methods**.
  - Advantage: More safety and predictability in the code, as objects cannot be changed “secretly”.

- **Optional**: Sometimes a value is not necessarily present. Instead of using `null`, you can use `Optional`.
  - Example: Not every student provides a **profileimage URL**. In this case, we can use an `Optional<String>` to safely encapsulate this value.
  - With methods like `orElse(...)`, you can specify a default value if the `Optional` is empty.

As an example, we will create a `Student` class that always has a name and a matriculation number, but only **optionally** a link to the student's profile picture.

## Exercise

### Tasks

1. Extend the class [`Student`](src/main/java/de/phl/programmingproject/Student.java) in the package [`de.phl.programmingproject`](src/main/java/de/phl/programmingproject/). The class should be **immutable**, which means: All fields are `private final`, there are **no setter methods**, and the values are set exclusively via the constructor. The class should have two required fields: (1) `String name` and (2) `String studentId`.
2. Extend the `Student` class with an optional field `Optional<String> profileImageUrl` (e.g., a link to an online profile image). In the constructor, `profileImageUrl` should be set with `Optional.ofNullable(profileImageUrl)`, so that `null` automatically becomes `Optional.empty()`. Implement getter methods for all fields.
3. In the `Main` class, create the helper method `private static void printStudentInfo(Student student)` that prints the information of a student, and create two `Student` objects in the `main` method:
    - Student **Anna** with matriculation number `"12345"` and profile URL `"https://example.com/anna"`
    - Student **Tom** with matriculation number `"67890"` and **without** a profileimage URL
4. Call `printStudentInfo(...)` for both students. The output should look like this:

   ```
   Name: Anna
   Matriculation Number: 12345
   Profile Image URL: https://example.com/anna.jpg

   Name: Tom
   Matriculation Number: 67890
   Profile Image URL: not provided
   ```
