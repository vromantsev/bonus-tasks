package ua.hillel.mockito_library;

import ua.hillel.library.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class BookRepository {

    private final List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        Objects.requireNonNull(book, "Parameter [book] must not be null!");
        books.add(book);
    }

    public boolean removeBook(Book book) {
        Objects.requireNonNull(book, "Parameter [book] must not be null!");
        return books.remove(book);
    }

    public Book getBookByTitle(String title) {
        Objects.requireNonNull(title, "Parameter [title] must not be null!");
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Book '%s' is not found in library.".formatted(title)));
    }

    public List<Book> getBooks() {
        return books;
    }

    public int getBookCount() {
        return books.size();
    }
}
