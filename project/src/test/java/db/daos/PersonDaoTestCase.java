package db.daos;

import db.entities.Person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



public class PersonDaoTestCase {
		
	private PersonDao personDao = new PersonDao();

	@Before
	public void initDatabase() throws Exception {
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS person (\r\n"
			    +"idperson INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\r\n"
			    +"lastname VARCHAR(45) NOT NULL,\r\n"  
			    +"firstname VARCHAR(45) NOT NULL,\r\n"
			    +"nickname VARCHAR(45) NOT NULL,\r\n"
			    +"phone_number VARCHAR(15) NULL,\r\n"
			    +"address VARCHAR(200) NULL,\r\n"
			    +"email_address VARCHAR(150) NULL,\r\n"
			    +"birth_date DATE NULL);");
		stmt.executeUpdate("DELETE FROM person");	
		stmt.executeUpdate("INSERT INTO person(idperson, lastname, firstname, nickname, phone_number, address,"
				+ " email_address, birth_date) VALUES (1, 'Ruiz', 'Julia', 'juls', '65058', '117 rue de conde',"
				+ " 'julia@gmail.com', '1998-01-26 12:00:00.000')");
		stmt.executeUpdate("INSERT INTO person(idperson, lastname, firstName, nickname, phone_number, address,"
				+ " email_address, birth_date) VALUES (2, 'Jimenez', 'Carlos', 'carlo', '65059', '117 rue de conde',"
				+ " 'carlos@gmail.com', '1998-07-15 12:00:00.000')");
		stmt.executeUpdate("INSERT INTO person(idperson, lastname, firstname, nickname, phone_number, address,"
				+ " email_address, birth_date) VALUES (3, 'Tsoy', 'Yekaterina', 'eka', '65060', '117 rue de conde',"
				+ " 'ekaterina@gmail.com', '1996-03-25 12:00:00.000')");
		stmt.close();
		connection.close();
	}

	
	@Test
	public void shouldGetPersonByNickName() {
		// WHEN
		Person person = personDao.getPerson("juls");
		// THEN
		assertThat(person.getIdPerson()).isEqualTo(1);
		assertThat(person.getFirstName()).isEqualTo("Julia");
	}
	
	@Test
	 public void shouldListPersons() {
		Person person = new Person();
		String lastname, firstname, nickname, phone_number, address, email_address;
		LocalDate birth_date;
		
		 
		// WHEN
		List<Person> persons = personDao.listPersons();
		// THEN
		assertTrue(persons.size()==3);

		person=persons.get(0);
		
		lastname = person.getLastName();
		firstname = person.getFirstName();
		nickname = person.getNickName();
		phone_number = person.getPhone_number();
		address = person.getAddress();
		email_address = person.getEmail_address();
		birth_date = person.getBirth_date();
  
        
		assertTrue(lastname.equals("Ruiz"));
		assertTrue(firstname.equals("Julia"));
		assertTrue(nickname.equals("juls"));
		assertTrue(phone_number.equals("65058"));
		assertTrue(address.equals("117 rue de conde"));
		assertTrue(email_address.equals("julia@gmail.com"));
		assertTrue(birth_date.equals(LocalDate.parse("1998-01-26")));
  
  
		person=persons.get(1);
		
		lastname = person.getLastName();
		firstname = person.getFirstName();
		nickname = person.getNickName();
		phone_number = person.getPhone_number();
		address = person.getAddress();
		email_address = person.getEmail_address();
		birth_date = person.getBirth_date();
 
		assertTrue(lastname.equals("Jimenez"));
		assertTrue(firstname.equals("Carlos"));
		assertTrue(nickname.equals("carlo"));
		assertTrue(phone_number.equals("65059"));
		assertTrue(address.equals("117 rue de conde"));
		assertTrue(email_address.equals("carlos@gmail.com"));
		assertTrue(birth_date.equals(LocalDate.parse("1998-07-15")));
 

		person=persons.get(2);
		
		lastname = person.getLastName();
		firstname = person.getFirstName();
		nickname = person.getNickName();
		phone_number = person.getPhone_number();
		address = person.getAddress();
		email_address = person.getEmail_address();
		birth_date = person.getBirth_date();

		assertTrue(lastname.equals("Tsoy"));
		assertTrue(firstname.equals("Yekaterina"));
		assertTrue(nickname.equals("eka"));
		assertTrue(phone_number.equals("65060"));
		assertTrue(address.equals("117 rue de conde"));
		assertTrue(email_address.equals("ekaterina@gmail.com"));
		assertTrue(birth_date.equals(LocalDate.parse("1996-03-25")));
			
	 }
	
	@Test
	public void shouldNotGetUnknownPerson() {
		// WHEN
		Person person = personDao.getPerson("Unknown");
		// THEN
		assertThat(person).isNull();
	}
	
	@Test
	public void shouldAddPerson() throws Exception {
		// WHEN 
		personDao.addPerson(new Person(4, "Fernandes", "Igor", "igorsinho", "667788", "117 rue de conde","igor@gmail.com", LocalDate.parse("2000-06-23")));
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE nickname='igorsinho'");
		assertThat(resultSet.next()).isTrue();
		assertThat(resultSet.getInt("idperson")).isNotNull();
		assertThat(resultSet.getString("nickname")).isEqualTo("igorsinho");
		assertThat(resultSet.next()).isFalse();
		resultSet.close();
		statement.close();
		connection.close();
	}

	
	
	@Test
	public void shouldDeletePerson() throws Exception{
		// WHEN
		personDao.deletePerson("eka");
		// THEN
		Connection connection = DataSourceFactory.getDataSource().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT * FROM person WHERE nickname='eka'");
		assertThat(resultSet.next()).isFalse();
		assertThat(resultSet.getInt("idperson")).isEqualTo(0);
		resultSet.close();
		statement.close();
		connection.close();
	}
	
	@Test
	public void shouldUpdatePerson() throws SQLException {
		//WHEN
		Person person = new Person();
		person.setIdPerson(1);
		person.setLastName("Ruiz");
		person.setFirstName("Julia");
		person.setNickName("juliaruizc");
		person.setPhone_number("65058");
		person.setAddress("117 Rue de conde");
		person.setEmail_address("julia@gmail.com");
		person.setBirth_date(LocalDate.parse("2020-01-26"));
		
		PersonDao personDao = new PersonDao();
		personDao.updatePerson(person);
		
		// Check that the person was updated correctly
		Person updatedPerson = personDao.getPerson("juliaruizc");
		assertEquals("Ruiz", updatedPerson.getLastName());
		assertEquals("Julia", updatedPerson.getFirstName());
		assertEquals("juliaruizc", updatedPerson.getNickName());
		assertEquals("65058", updatedPerson.getPhone_number());
		assertEquals("117 Rue de conde", updatedPerson.getAddress());
		assertEquals("julia@gmail.com", updatedPerson.getEmail_address());
		assertEquals(LocalDate.parse("2020-01-26"), updatedPerson.getBirth_date());
		
	}
		
}
		









	
	


