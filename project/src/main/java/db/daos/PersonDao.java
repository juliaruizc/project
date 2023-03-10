package db.daos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import db.entities.Person;


public class PersonDao { 
	
	public List<Person> listPersons() {
		String sqlQUERY = "SELECT * FROM person";
		List<Person> listOfPerson = new ArrayList<>();

		try(Connection connection = DataSourceFactory.getDataSource().getConnection()){
			try(Statement statement = connection.createStatement()){
				try(ResultSet results = statement.executeQuery(sqlQUERY)){
					while(results.next()) {
						Person person = new Person(results.getInt("idperson"), results.getString("lastname"), 
								results.getString("firstname"), results.getString("nickname"), 
								results.getString("phone_number"), results.getString("address"),
								results.getString("email_address"), results.getDate("birth_date").toLocalDate());
						listOfPerson.add(person);
					}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listOfPerson;
	}
	
   public Person getPerson(String nickname) {
		try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	        try (PreparedStatement statement = connection.prepareStatement(
	                    "select * from person where nickname=?")) {
	            statement.setString(1, nickname);
	            try (ResultSet results = statement.executeQuery()) {
	                if (results.next()) {
	                    return new Person(results.getInt("idperson"), 
	                    		results.getString("lastname"), results.getString("firstname"), 
	                    		results.getString("nickname"), results.getString("phone_number"), 
	                    		results.getString("address"),results.getString("email_address"), 
	                    		results.getDate("birth_date").toLocalDate());
	                }
	            }
	        }
	    } catch (SQLException e) {
	        // Manage Exception
	        e.printStackTrace();
	    }
		return null;
	}
	
	public Person addPerson(Person person) {
		 try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
		        String sqlQuery = "INSERT INTO person(idperson, lastname, firstname, nickname, phone_number, address, email_address, birth_date) "
		        		+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		        try (PreparedStatement statement = connection.prepareStatement(
		                        sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
		            statement.setInt(1, person.getIdPerson());
		            statement.setString(2, person.getLastName());
		            statement.setString(3, person.getFirstName());
		            statement.setString(4, person.getNickName());
		            statement.setString(5, person.getPhone_number());
		            statement.setString(6, person.getAddress());
		            statement.setString(7, person.getEmail_address());
		            statement.setDate(8, Date.valueOf(person.getBirth_date()));
		            
		            statement.executeUpdate();
		            ResultSet ids = statement.getGeneratedKeys();
		            if (ids.next()) {
		                return new Person(ids.getInt(1), person.getLastName(), person.getFirstName(), 
		                		person.getNickName(), person.getPhone_number(), person.getAddress(), 
		                		person.getEmail_address(), person.getBirth_date());
		            }
		        }
		    }catch (SQLException e) {
		        // Manage Exception
		        e.printStackTrace();
		    }
		    return null;
	}
	
	public void deletePerson(String nickname) throws SQLException {
		 try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
			 try (PreparedStatement statement = connection.prepareStatement(
	                 "delete from person where nickname = ?")) {
				 statement.setString(1, nickname);
		         statement.executeUpdate();
		        }
		    }catch (SQLException e) {
		        // Manage Exception
		        e.printStackTrace();
		    }
	}

	
	public void updatePerson(Person person) throws SQLException {
		String sqlQuery = "UPDATE person SET lastName=?, firstName=?, nickName=?, phone_number=?, address=?, email_address=?, birth_date=? WHERE idperson=?";
	    try (Connection connection = DataSourceFactory.getDataSource().getConnection()) {
	    	try (PreparedStatement statement = connection.prepareStatement(
                    sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
		    	statement.setInt(1, person.getIdPerson());
	            statement.setString(2, person.getLastName());
	            statement.setString(3, person.getFirstName());
	            statement.setString(4, person.getNickName());
	            statement.setString(5, person.getPhone_number());
	            statement.setString(6, person.getAddress());
	            statement.setString(7, person.getEmail_address());
	            statement.setDate(8, Date.valueOf(person.getBirth_date()));
	    	} catch (SQLException e) {
	    		System.out.println(e.getMessage());
	    	}
	    }
	}
}