package com.drkiet.vertx.dictbuilder.repository;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.drkiet.vertx.dictbuilder.model.Term;

public class DictionaryDatabase {
	private static final String GET_LIST_OF_KEYS = "SELECT THE_KEY FROM KV";
	private static final String DICTIONARY_CREATE_KV_TABLE = "CREATE TABLE KV (THE_KEY VARCHAR(255) NOT NULL, VALUE VARCHAR(10240) NOT NULL, PRIMARY KEY(THE_KEY))";
	private static final String DERBY_DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String JDBC_DERBY_URL = "jdbc:derby:%s;create=true";
	private static final String JDBC_DERBY_URL_READ_ONLY = "jdbc:derby:%s";
	private static final Logger LOGGER = LoggerFactory.getLogger(DictionaryDatabase.class);
	public static final String CREATE_TERM = "INSERT INTO KV (THE_KEY, VALUE) VALUES(?,?)";
	private static final String DELETE_TERM = "DELETE FROM KV WHERE THE_KEY=?";
	private static final String UPDATE_TERM = "UPDATE KV SET VALUE=? WHERE THE_KEY=?";
	private static final String READ_TERM = "SELECT VALUE FROM KV WHERE THE_KEY=?";

	private Connection conn;
	private String jdbcUrl;

	/**
	 * Constructor of the database access object.
	 * 
	 * @param databaseName
	 * @param readOnly
	 * 
	 */
	public DictionaryDatabase(String databaseName, boolean readOnly) {

		if (readOnly) {
			jdbcUrl = JDBC_DERBY_URL_READ_ONLY;
			openDatabase(databaseName);
		} else {
			jdbcUrl = JDBC_DERBY_URL;
			openDatabase(databaseName);
			createTableIfNotExist();
		}
	}

	private void openDatabase(String databaseName) {
		String dbUrl = String.format(jdbcUrl, databaseName);
		try {
			LOGGER.info("Create connection for {}", databaseName);
			Class.forName(DERBY_DRIVER_CLASS);
			conn = DriverManager.getConnection(dbUrl);
		} catch (ClassNotFoundException e) {
			LOGGER.info("Unable to find Driver Class {}", e);
		} catch (SQLException e) {
			LOGGER.info("Unable to get database connection {}", e);
		}
	}

	/**
	 * If KV table does not exist create it.
	 * 
	 */
	private void createTableIfNotExist() {
		LOGGER.info("Check & Create database table");
		DatabaseMetaData metas;
		Statement stat = null;
		ResultSet rs = null;
		try {
			metas = conn.getMetaData();
			stat = conn.createStatement();
			rs = metas.getTables(conn.getCatalog(), null, "KV", null);
			if (!rs.next()) {
				LOGGER.info("Creating terms table ...");
				stat.execute(DICTIONARY_CREATE_KV_TABLE);
			} else {
				LOGGER.info("KV table exists ...");
			}
		} catch (SQLException e) {
			LOGGER.info("ERROR: {}", e);
		} finally {
			closeStatementAndResultSet(stat, rs);
		}

	}

	/**
	 * Get a list of all terms from the database
	 * 
	 * @return
	 */
	public List<String> getTerms() {
		LOGGER.info("Get a list of terms from database");
		Statement stat = null;
		ResultSet rs = null;

		List<String> terms = new ArrayList<String>();

		try {
			stat = conn.createStatement();
			rs = stat.executeQuery(GET_LIST_OF_KEYS);
			while (rs.next()) {
				terms.add(rs.getString(1));
			}
		} catch (SQLException e) {
			LOGGER.info("ERROR: {}", e);
		} finally {
			closeStatementAndResultSet(stat, rs);
		}
		return terms;
	}

	/**
	 * Inserting a term into the database. Assuming the term does not exist.
	 * 
	 * @param term
	 */
	public void create(Term term) {
		LOGGER.info("Get a list of terms from database \n{}", term.toString());

		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(CREATE_TERM);
			prep.setString(1, term.getTerm());
			prep.setString(2, term.toString());
			prep.executeUpdate();

		} catch (SQLException e) {
			LOGGER.info("ERROR: {}", e);
		} finally {
			closeStatementAndResultSet(prep, rs);
		}
	}

	/**
	 * Close statement & ResultSet
	 * 
	 * @param stat
	 * @param rs
	 */
	private void closeStatementAndResultSet(Statement stat, ResultSet rs) {
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				LOGGER.info("ERROR: {}", e);
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				LOGGER.info("ERROR: {}", e);
			}
		}
	}

	public void delete(Term term) {
		LOGGER.info("Delete a term from database {}", term.getTerm());

		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(DELETE_TERM);
			prep.setString(1, term.getTerm());
			prep.executeUpdate();

		} catch (SQLException e) {
			LOGGER.info("ERROR: {}", e);
		} finally {
			closeStatementAndResultSet(prep, rs);
		}
	}

	public void update(Term term) {
		LOGGER.info("Update a term from database {}", term.toString());

		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(UPDATE_TERM);
			prep.setString(1, term.toString());
			prep.setString(2, term.getTerm());
			prep.executeUpdate();

		} catch (SQLException e) {
			LOGGER.info("ERROR: {}", e);
		} finally {
			closeStatementAndResultSet(prep, rs);
		}
	}

	public Term read(Term term) {
		LOGGER.info("Delete a term from database {}", term.getTerm());

		PreparedStatement prep = null;
		ResultSet rs = null;

		try {
			prep = conn.prepareStatement(READ_TERM);
			prep.setString(1, term.getTerm());
			rs = prep.executeQuery();
			if (rs.next()) {
				String value = rs.getString(1);

				LOGGER.info("value: \n{}", value);
				return Term.parse(value);
			}
		} catch (SQLException e) {
			LOGGER.info("ERROR: {}", e);
		} finally {
			closeStatementAndResultSet(prep, rs);
		}
		return null;
	}

}
