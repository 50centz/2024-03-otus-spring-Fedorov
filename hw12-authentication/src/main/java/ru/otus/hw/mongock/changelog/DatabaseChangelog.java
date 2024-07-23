package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.ArrayList;
import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    private List<Author> authors = new ArrayList<>();

    private List<Genre> genres = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    private List<Book> books = new ArrayList<>();

    @ChangeSet(order = "000", id = "dropDB", author = "50centz", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "50centz", runAlways = true)
    public void initAuthors(AuthorRepository authorRepository) {
        authors.add(authorRepository.save(new Author(null,"Author_1")));
        authors.add(authorRepository.save(new Author(null,"Author_2")));
        authors.add(authorRepository.save(new Author(null,"Author_3")));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "50centz", runAlways = true)
    public void initGenres(GenreRepository genreRepository) {
        genres.add(genreRepository.save(new Genre(null,"Genre_1")));
        genres.add(genreRepository.save(new Genre(null,"Genre_2")));
        genres.add(genreRepository.save(new Genre(null,"Genre_3")));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "50centz", runAlways = true)
    public void initBooks(BookRepository bookRepository) {
        books.add(bookRepository.save(new Book(null, "BookTitle_1", genres.get(0),
                authors.stream().limit(1).toList())));
        books.add(bookRepository.save(new Book(null, "BookTitle_2", genres.get(1),
                authors.stream().skip(1).limit(1).toList())));
        books.add(bookRepository.save(new Book(null, "BookTitle_3", genres.get(2),
                authors.stream().skip(2).limit(1).toList())));
    }

    @ChangeSet(order = "004", id = "initComments", author = "50centz", runAlways = true)
    public void initComments(CommentRepository commentRepository) {
        comments.add(commentRepository.save(new Comment(null, "Comment 1", books.get(0))));
        comments.add(commentRepository.save(new Comment(null, "Comment 2", books.get(1))));
        comments.add(commentRepository.save(new Comment(null, "Comment 3", books.get(2))));
        comments.add(commentRepository.save(new Comment(null, "Comment 4", books.get(0))));
    }

}
