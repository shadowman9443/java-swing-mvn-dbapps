package com.java.montu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Contact {

  private static final Logger LOGGER = LoggerFactory.getLogger(Contact.class);

  private long id = -1L;
  private String name;
  private String contacts;

  public void delete() throws SQLException {
    if (id == -1) {
      // Can throw an exception
    } else {
      Contact.LOGGER.debug("Deleting contact: {}", this);
      final String sql = "DELETE FROM contacts WHERE id = ?";
      try (Connection connection = DbHelper.getConnection(); PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setLong(1, id);
        pstmt.execute();
        id = -1;
      }
    }
  }

  @Override
  public boolean equals(final Object object) {
    if (object instanceof Contact) {
      final Contact other = (Contact) object;
      return id != -1 && id == other.id;
    }

    return false;
  }

  public String getContacts() {
    return contacts;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    return 31 * 1 + (int) (id ^ id >>> 32);
  }

  public void save() throws SQLException {
    try (Connection connection = DbHelper.getConnection()) {
      if (id == -1) {
        Contact.LOGGER.debug("Adding new contact: {}", this);
        final String sql = "INSERT INTO contacts(name, contacts) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
          pstmt.setString(1, name);
          pstmt.setString(2, contacts);
          pstmt.execute();

          try (final ResultSet rs = pstmt.getGeneratedKeys()) {
            rs.next();
            id = rs.getLong(1);
          }
        }
      } else {
        Contact.LOGGER.debug("Updating existing contact: {}", this);
        final String sql = "UPDATE contacts SET name = ?, contacts = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
          pstmt.setString(1, name);
          pstmt.setString(2, contacts);
          pstmt.setLong(3, id);
          pstmt.execute();
        }
      }
    }
  }

  public void setContacts(final String contacts) {
    this.contacts = contacts;
  }

  public void setId(final long id) {
    this.id = id;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuilder formatted = new StringBuilder();
    if (id == -1) {
      formatted.append("[No Id] ");
    } else {
      formatted.append("[").append(id).append("] ");
    }

    if (name == null) {
      formatted.append("no name");
    } else {
      formatted.append(name);
    }

    return formatted.toString();
  }
}