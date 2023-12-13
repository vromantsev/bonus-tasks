package ua.hillel.mockito_library;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.hillel.library.Book;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MockitoLibraryTest {

    @Mock
    private BookRepository bookRepositoryMock;

    private Library library;

    private AutoCloseable autoCloseable;

    @BeforeEach
    public void setup() {
        this.autoCloseable = MockitoAnnotations.openMocks(this);
        this.library = new Library(bookRepositoryMock);
    }

    @AfterEach
    public void tearDown() throws Exception {
        this.autoCloseable.close();
    }

    @Test
    public void whenAddBookThenBookIsAdded() {
        // given
        Book book = new Book("High Performance Java Persistence", "Vlad Mihalcea");

        // when
        doNothing().when(this.bookRepositoryMock).addBook(eq(book));

        // then
        library.addBook(book);

        // assertions & verification
        verify(this.bookRepositoryMock, times(1)).addBook(book);
    }

    @Test
    public void whenAddNullBookThenThrowException() {
        // given
        Book invalidBook = null;
        String expectedExceptionMessage = "Parameter [book] must not be null!";

        // when
        doThrow(new NullPointerException(expectedExceptionMessage)).when(this.bookRepositoryMock).addBook(eq(invalidBook));

        // then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> library.addBook(invalidBook));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Incorrect exception message!");
        verify(this.bookRepositoryMock, times(1)).addBook(invalidBook);
    }

    @Test
    public void whenRemoveExistingBookThenBookIsRemoved() {
        // given
        Book book = new Book("Spring in Action", "Craig Walls");

        // when
        doCallRealMethod().when(this.bookRepositoryMock).addBook(eq(book));
        when(this.bookRepositoryMock.removeBook(eq(book))).thenReturn(true);

        // then
        boolean isBookRemoved = library.removeBook(book);

        // assertions & verification
        assertTrue(isBookRemoved, "Result should be 'true'!");
        verify(this.bookRepositoryMock, times(1)).removeBook(book);
    }

    @Test
    public void whenRemoveNonExistingBookThenBookIsNotRemoved() {
        // given
        Book book = new Book("Spring Microservices in Action", "John Carnell");
        Book toRemove = new Book("Reactive Spring", "Josh Long");

        // when
        doCallRealMethod().when(this.bookRepositoryMock).addBook(eq(book));
        when(this.bookRepositoryMock.removeBook(eq(toRemove))).thenReturn(false);

        // then
        boolean isBookRemoved = library.removeBook(toRemove);

        // assertions & verification
        assertFalse(isBookRemoved, "Result should be 'false'!");
        verify(this.bookRepositoryMock, times(1)).removeBook(toRemove);
    }

    @Test
    public void whenRemoveNullBookThenThrowException() {
        // given
        Book toRemove = null;
        String expectedExceptionMessage = "Parameter [book] must not be null!";

        // when
        when(this.bookRepositoryMock.removeBook(eq(toRemove))).thenThrow(new NullPointerException(expectedExceptionMessage));

        // then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> library.removeBook(toRemove));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Incorrect exception message!");
        verify(this.bookRepositoryMock, times(1)).removeBook(toRemove);
    }

    @Test
    public void whenGetBookByTitleThenReturnBook() {
        // given
        String cleanCodeTitle = "Clean Code: A Handbook of Agile Software Craftsmanship";
        Book cleanCodeBook = new Book(cleanCodeTitle, "Robert Martin");
        String codeCompleteTitle = "Code Complete";
        Book codeCompleteBook = new Book(codeCompleteTitle, "Steve McConnell");

        // when
        doCallRealMethod().when(this.bookRepositoryMock).addBook(cleanCodeBook);
        doCallRealMethod().when(this.bookRepositoryMock).addBook(codeCompleteBook);
        when(this.bookRepositoryMock.getBookByTitle(eq(cleanCodeTitle))).thenReturn(cleanCodeBook);

        // then
        Book book = library.getBookByTitle(cleanCodeTitle);

        // assertions & verification
        assertNotNull(book, "Book must not be null!");
        assertEquals(book.getTitle(), cleanCodeTitle, "Title must be equal to '%s'".formatted(cleanCodeTitle));
        assertEquals(book.getAuthor(), cleanCodeBook.getAuthor(), "Author must be equal to '%s'".formatted(cleanCodeBook.getAuthor()));
        verify(this.bookRepositoryMock, times(1)).getBookByTitle(cleanCodeTitle);
    }

    @Test
    public void whenGetBookByNullTitleThenThrowException() {
        // given
        String invalidTitle = null;
        String expectedExceptionMessage = "Parameter [title] must not be null!";

        // when
        when(this.bookRepositoryMock.getBookByTitle(eq(invalidTitle))).thenThrow(new NullPointerException(expectedExceptionMessage));

        // then
        NullPointerException exception = assertThrows(NullPointerException.class, () -> library.getBookByTitle(invalidTitle));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Incorrect exception message!");
        verify(this.bookRepositoryMock, times(1)).getBookByTitle(invalidTitle);
    }

    @Test
    public void whenGetBookByTitleThenThrowException() {
        // given
        String cleanArchitectureTitle = "Clean Architecture: A Craftsman's Guide to Software Structure and Design";
        Book cleanArchitectureBook = new Book(cleanArchitectureTitle, "Robert Martin");
        String codeCompleteTitle = "Code Complete";
        String expectedExceptionMessage = "Book '%s' is not found in library.".formatted(codeCompleteTitle);

        // when
        doCallRealMethod().when(this.bookRepositoryMock).addBook(eq(cleanArchitectureBook));
        when(this.bookRepositoryMock.getBookByTitle(eq(codeCompleteTitle))).thenThrow(new NoSuchElementException(expectedExceptionMessage));

        // then
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> library.getBookByTitle(codeCompleteTitle));

        // assertions & verification
        assertEquals(expectedExceptionMessage, exception.getMessage(), "Incorrect exception message!");
        verify(this.bookRepositoryMock, times(1)).getBookByTitle(codeCompleteTitle);
    }
}
