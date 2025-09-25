package lk.ijse.dao.custom.impl;


import lk.ijse.config.FactoryConfig;
import lk.ijse.dao.custom.QueryDAO;
import org.hibernate.Session;
import org.hibernate.query.Query;


public class QueryDAOImpl implements QueryDAO {

    private final FactoryConfig factoryConfig = FactoryConfig.getInstance();


    @Override
    public int getStudentCountForLesson(String lessonId) {
        Session session = factoryConfig.getSession();
        try {
            // Assuming there is a mapping between Lessons and Students (e.g., a Set<Student> in Lessons entity)
            String hql = "SELECT COUNT(s.studentId) FROM Students s JOIN s.lessons l WHERE l.lessonId = :lessonId";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("lessonId", lessonId);
            Long count = query.uniqueResult();
            return count != null ? count.intValue() : 0;
        } finally {
            session.close();
        }
    }
}
