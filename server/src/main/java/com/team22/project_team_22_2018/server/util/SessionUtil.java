package com.team22.project_team_22_2018.server.util;

import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * created by Greffort 10.05.2019
 */
public class SessionUtil {
    private Session session;
    private Transaction transaction;

    public Session getSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Session openSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }

    public Session openTransactionSession() {
        session = openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public void closeTransactionSesstion() {
        transaction.commit();
        closeSession();
    }
}
