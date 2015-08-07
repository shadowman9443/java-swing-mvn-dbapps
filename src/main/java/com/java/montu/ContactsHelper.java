package com.java.montu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContactsHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(ContactsHelper.class);

  private static final ContactsHelper INSTANCE = new ContactsHelper();

  public static ContactsHelper getInstance() {
    return ContactsHelper.INSTANCE;
  }

  public List<Contact> getContacts() throws SQLException {
    ContactsHelper.LOGGER.debug("Loading contacts");
    final List<Contact> contacts = new ArrayList<>();

    final String sql = "SELECT * FROM contacts ORDER BY id";
    try (Connection connection = DbHelper.getConnection();
        PreparedStatement psmt = connection.prepareStatement(sql);
        ResultSet rs = psmt.executeQuery()) {

      while (rs.next()) {
        final Contact contact = new Contact();
        contact.setId(rs.getLong("id"));
        contact.setName(rs.getString("name"));
        contact.setContacts(rs.getString("contacts"));
        contacts.add(contact);
      }
    }

    ContactsHelper.LOGGER.debug("Loaded {} contacts", contacts.size());
    return contacts;
  }
}