package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.mapper.BookMapper;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final BookMapper bookMapper;

    private final MutableAclService mutableAclService;


    @Transactional(readOnly = true)
    @Override
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));
    }

    @Transactional(readOnly = true)
//    @PostFilter("hasPermission(filterObject, 'READ')")
    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
    }


    @Transactional
    @Override
    public BookDto create(BookCreateDto bookCreateDto) {

        Long authorId = bookCreateDto.getAuthor();

        Long genreId = bookCreateDto.getGenre();

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(genreId)));

        Book book = bookRepository.save(new Book(0L, bookCreateDto.getTitle(), author, genre));

        addAce(book);

        return bookMapper.toDto(book);
    }

    private void addAce(Book book) {

        ObjectIdentity oid = new ObjectIdentityImpl(book);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Sid sid = new PrincipalSid(authentication);
        Sid admin = new PrincipalSid("god");
        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(sid);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.CREATE, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, admin, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.ADMINISTRATION, admin, true);
        mutableAclService.updateAcl(acl);


    }

    @Transactional
    @PreAuthorize("hasPermission(#book, 'WRITE')")
    @Override
    public BookDto update(Book book) {

        Long authorId = book.getAuthor().getId();

        Long genreId = book.getGenre().getId();

        var author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));
        var genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(genreId)));

        book.setTitle(book.getTitle());
        book.setGenre(genre);
        book.setAuthor(author);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    @Override
    @PreAuthorize("hasPermission(#book, 'DELETE')")
    public void deleteById(long id, Book book) {

        commentRepository.deleteAllByBookId(id);
        bookRepository.deleteById(id);
    }

}
