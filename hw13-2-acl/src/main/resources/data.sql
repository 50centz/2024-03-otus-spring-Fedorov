insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(comment, book_id)
values ('Comment_1', 1), ('Comment_2', 2), ('Comment_3', 3);



insert into users(username, password, authority)
values('admin', '$2a$10$7X6F0GNRxiNAo6AiVUz8u.nMMRdB2gTxpOtMqPRiKDis3PzsNjv7.', 'ADMIN'),
      ('user', '$2a$10$7X6F0GNRxiNAo6AiVUz8u.nMMRdB2gTxpOtMqPRiKDis3PzsNjv7.', 'USER'),
      ('god', '$2a$10$7X6F0GNRxiNAo6AiVUz8u.nMMRdB2gTxpOtMqPRiKDis3PzsNjv7.', 'ROLE_GOD');

INSERT INTO acl_sid (id, principal, sid) VALUES
(1, true, 'admin'),
(2, true, 'user'),
(3, true, 'god'),
(4, false, 'ROLE_GOD');

INSERT INTO acl_class (id, class) VALUES
(1, 'ru.otus.hw.model.Book');
--
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(1, 1, 1, NULL, 3, true),
(2, 1, 2, NULL, 3, true),
(3, 1, 3, NULL, 3, true);
--
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask,
                       granting, audit_success, audit_failure) VALUES
(1, 1, 0, 1, 1, true, true, true),
(2, 1, 1, 2, 1, true, true, true),
(3, 1, 2, 3, 1, true, true, true),
(4, 1, 3, 3, 2, true, true, true),
(5, 1, 4, 3, 8, true, true, true),
(6, 2, 0, 2, 1, true, true, true),
(7, 2, 1, 3, 1, true, true, true),
(8, 2, 2, 3, 2, true, true, true),
(9, 2, 3, 3, 8, true, true, true),
(10, 2, 4, 2, 2, true, true, true),
(11, 3, 0, 1, 1, true, true, true),
(12, 3, 1, 1, 2, true, true, true),
(13, 3, 2, 3, 1, true, true, true),
(14, 3, 3, 3, 2, true, true, true),
(15, 3, 4, 3, 4, true, true, true),
(16, 3, 5, 3, 8, true, true, true),
(17, 3, 6, 3, 16, true, true, true);

