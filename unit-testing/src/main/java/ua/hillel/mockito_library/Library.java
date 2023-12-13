package ua.hillel.mockito_library;

import ua.hillel.library.Book;

import java.util.List;

public class Library {

    private final BookRepository bookRepository;

    public Library(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) {
        bookRepository.addBook(book);
    }

    public boolean removeBook(Book book) {
        return bookRepository.removeBook(book);
    }

    public Book getBookByTitle(String title) {
        return bookRepository.getBookByTitle(title);
    }

    public List<Book> getBooks() {
        return bookRepository.getBooks();
    }

    public int getBookCount() {
        return bookRepository.getBookCount();
    }
}
