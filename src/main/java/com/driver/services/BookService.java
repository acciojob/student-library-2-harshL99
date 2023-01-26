package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;

    @Autowired
    AuthorRepository authorRepository2;

    public void createBook(Book book){
        //Author object will be provided to book child and we need to add it to author object after updating the
        //child and update author object...
        int authorId=book.getAuthor().getId();
        Author author=authorRepository2.findById(authorId).get();
        List<Book> bookList=author.getBooksWritten();
        bookList.add(book);
        author.setBooksWritten(bookList);

        //Updated book as we added in author's bookList...
        book.setAuthor(author);
        bookRepository2.save(book);

        //Update author as we have updated it's bookList
        authorRepository2.save(author);
    }

    public List<Book> getBooks(String genre, boolean available, String author){
        List<Book> books = new ArrayList<>(); //find the elements of the list...

        if(genre!=null && author!=null){
            books=bookRepository2.findBooksByGenreAuthor(genre,author,available);
        }
        else if(genre!=null){
            books=bookRepository2.findBooksByGenre(genre,available);
        }
        else if(author!=null){
            books=bookRepository2.findBooksByAuthor(author,available);
        }
        else{
            books=bookRepository2.findByAvailability(available);
        }
        return books;
    }
}
