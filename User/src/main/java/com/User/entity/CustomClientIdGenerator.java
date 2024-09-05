package com.User.entity;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomClientIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String prefix = "IS";
        final String[] nextId = {"IS101"}; // Default start ID if no records are found

        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                // Query to find the latest client ID
                String query = "SELECT client_id FROM users ORDER BY client_id DESC LIMIT 1";
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    String lastId = rs.getString(1);
                    int id = Integer.parseInt(lastId.substring(2)); // Remove "IS" prefix and parse the number
                    nextId[0] = prefix + (id + 1); // Increment the number and create new ID
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return nextId[0];
    }
}
