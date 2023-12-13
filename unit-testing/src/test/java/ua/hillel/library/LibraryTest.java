package ua.hillel.library;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTest {

    private static Library library;

    @BeforeAll
    public static void setup() {
        library = new Library();
    }

    @AfterEach
    public void init() {
        library.getBooks().clear();
    }

    @Test
    public void whenAddBookThenBookIsAdded() {
        // given
        Book book = new Book("High Performance Java Persistence", "Vlad Mihalcea");
        int expectedBooksCount = 1;

        // then
        library.addBook(book);

        // assertions & verification
        assertEquals(expectedBooksCount, library.getBookCount(), "Library should have 1 book!");
    }

    @Test
    public void whenAddNullBookThenThrowException() {
        // given
        Book invalidBook = null;
        String expectedExceptionMessage = "Parameter [book] must not be null!";

        // then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> library.addBook(invalidBook));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Incorrect exception message!");
    }

    @Test
    public void whenRemoveExistingBookThenBookIsRemoved() {
        // given
        Book book = new Book("Spring in Action", "Craig Walls");
        int expectedBookCount = 0;

        // when
        library.addBook(book);

        // then
        boolean isBookRemoved = library.removeBook(book);

        // assertions & verification
        assertTrue(isBookRemoved, "Result should be 'true'!");
        assertEquals(expectedBookCount, library.getBookCount(), "Library shouldn't have anu books!");
    }

    @Test
    public void whenRemoveNonExistingBookThenBookIsNotRemoved() {
        // given
        Book book = new Book("Spring Microservices in Action", "John Carnell");
        Book toRemove = new Book("Reactive Spring", "Josh Long");
        int expectedBookCount = 1;

        // when
        library.addBook(book);

        // then
        boolean isBookRemoved = library.removeBook(toRemove);

        // assertions & verification
        assertFalse(isBookRemoved, "Result should be 'false'!");
        assertEquals(expectedBookCount, library.getBookCount(), "Library should have 1 book!");
    }

    @Test
    public void whenRemoveNullBookThenThrowException() {
        // given
        Book toRemove = null;
        String expectedExceptionMessage = "Parameter [book] must not be null!";

        // then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> library.removeBook(toRemove));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Incorrect exception message!");
    }

    @Test
    public void whenGetBookByTitleThenReturnBook() {
        // given
        String cleanCodeTitle = "Clean Code: A Handbook of Agile Software Craftsmanship";
        Book cleanCodeBook = new Book(cleanCodeTitle, "Robert Martin");
        String codeCompleteTitle = "Code Complete";
        Book codeCompleteBook = new Book(codeCompleteTitle, "Steve McConnell");

        // when
        library.addBook(cleanCodeBook);
        library.addBook(codeCompleteBook);

        // then
        Book book = library.getBookByTitle(cleanCodeTitle);

        // assertions & verification
        assertNotNull(book, "Book must not be null!");
        assertEquals(book.getTitle(), cleanCodeTitle, "Title must be equal to '%s'".formatted(cleanCodeTitle));
        assertEquals(book.getAuthor(), cleanCodeBook.getAuthor(), "Author must be equal to '%s'".formatted(cleanCodeBook.getAuthor()));
    }

    @Test
    public void whenGetBookByNullTitleThenThrowException() {
        // given
        String invalidTitle = null;
        String expectedExceptionMessage = "Parameter [title] must not be null!";

        // then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> library.getBookByTitle(invalidTitle));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Incorrect exception message!");
    }

    @Test
    public void whenGetBookByTitleThenThrowException() {
        // given
        String cleanArchitectureTitle = "Clean Architecture: A Craftsman's Guide to Software Structure and Design";
        Book cleanArchitectureBook = new Book(cleanArchitectureTitle, "Robert Martin");
        String codeCompleteTitle = "Code Complete";
        String expectedExceptionMessage = "Book '%s' is not found in library.".formatted(codeCompleteTitle);

        // when
        library.addBook(cleanArchitectureBook);

        // then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> library.getBookByTitle(codeCompleteTitle));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Incorrect exception message!");
    }
}
