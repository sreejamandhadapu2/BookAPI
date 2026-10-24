
package com.bookapi.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bookapi.config.DatabaseConfig;
import com.bookapi.model.Book;

@Repository
public class BookRepository {

    // Fetch one book by ID
    public Book findById(int id) {

        String sql =
            "SELECT id, title, author FROM books WHERE id = ?";

        try (
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author")
                    );
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error retrieving book with ID: " + id, e);
        }

        return null;
    }

    // Fetch all books
    public List<Book> findAll() {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT id, title, author FROM books";

        try (
            Connection connection = DatabaseConfig.getConnection();
            PreparedStatement statement =
                connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery()
        ) {
            while (rs.next()) {

                Book book = new Book(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("author")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                "Error retrieving all books", e);
        }

        return books;
    }
    public int save(Book book) {
    	String sql = "insert into books (title,author) values(?,?)";
    	try(
    			Connection connection = DatabaseConfig.getConnection();
    			PreparedStatement st = connection.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS)){
    		st.setString(1,book.getTitle());
    		st.setString(2, book.getAuthor());
    		int rowsexecuted = st.executeUpdate();
    		if(rowsexecuted == 0) {
    			throw new SQLException("Inserting book failed.");
    		}
    		try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }

            throw new SQLException("No generated ID returned.");

        } catch (SQLException e) {
            throw new RuntimeException("Error saving book", e);
        }
    }
    public boolean update(Book book) {
    	String sql = "update books set title = ?,author = ? where id = ?";
    	try(Connection connection = DatabaseConfig.getConnection();
    			PreparedStatement statement = connection.prepareStatement(sql)
    			){
    		statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setInt(3, book.getId());
    	
        int rowsUpdated = statement.executeUpdate();
        return rowsUpdated > 0;
    	
    }
    catch (SQLException e) {
        throw new RuntimeException(
            "Error updating book with ID: " + book.getId(), e);
    }   }
    public boolean deleteById(int id) {
    	String sql = "delete from books where id = ?";
    	try(
    			Connection connection = DatabaseConfig.getConnection();
    			PreparedStatement statement = connection.prepareStatement(sql)
    					
    			){
    		statement.setInt(1,id);
    		int rowsdeleted = statement.executeUpdate();
    		return rowsdeleted > 0;
    		
    	}
    	catch (SQLException e) {
            throw new RuntimeException(
                "Error deleting book with ID: " + id, e);
        }
    }
}
    	
    
   

