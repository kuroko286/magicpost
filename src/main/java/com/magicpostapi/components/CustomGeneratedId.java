package com.magicpostapi.components;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class CustomGeneratedId implements IdentifierGenerator {
    private String prefix = ""; // Một giá trị mặc định cho prefix.
    private String field = "";

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) {
        // Kiểm tra nếu có tham số "prefix" trong @Parameter thì lấy giá trị.
        if (params != null) {
            String prefixParam = params.getProperty("prefix");
            String fieldParam = params.getProperty("field");
            if (prefixParam != null) {
                prefix = prefixParam;
            }
            if (field != null) {
                field = fieldParam;
            }
        }
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Connection connection = null;
        try {
            ConnectionProvider connectionProvider = session.getFactory().getServiceRegistry()
                    .getService(ConnectionProvider.class);
            connection = connectionProvider.getConnection();

            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT max(" + field + ") as maxId FROM sequence_id");
            if (rs.next()) {
                int maxId = rs.getInt("maxId");
                statement.executeUpdate("SET SQL_SAFE_UPDATES = 0");
                statement.executeUpdate("update  sequence_id SET " + field + " = " + field + " + 1");
                return prefix + maxId;
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ theo ý muốn của bạn.
            System.out.println(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    // Xử lý ngoại lệ khi đóng kết nối.
                }
            }
        }
        return null;
    }
}
