package repository;

import competition.Participant;
import org.apache.logging.log4j.core.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


public class ParticipantDbRepositoryORM implements ParticipantRepository {

    static SessionFactory sessionFactory;

    public ParticipantDbRepositoryORM(){
        initialize();
    }

    static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    static void close(){
        if(sessionFactory != null){
            sessionFactory.close();
        }
    }

    @Override
    public Participant getParticipantByCredentials(String username, String password) {
        Participant participant = null;
        try(Session session = sessionFactory.openSession()){
            Transaction transaction = null;
            try{
                transaction = session.beginTransaction();

                Query qq = session.createQuery("From Participant where username=?1 and password=?2",Participant.class);

                qq.setParameter(1,username).setParameter(2,password);
                System.out.println(qq);
                participant = (Participant) qq.uniqueResult();
                System.out.println(participant);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();

                if(transaction !=null)
                    transaction.rollback();
            }
        }
        return participant;
    }

    @Override
    public Participant add(Participant entity) {
//        try(Session session = sessionFactory.openSession()){
//            Transaction transaction = null;
//            try{
//                transaction = session.beginTransaction();
//
//                Query qq = session.createQuery("From Participant where username=?1 and password=?2",Participant.class);
//
//                qq.setParameter(1,username).setParameter(2,password);
//                System.out.println(qq);
//                participant = (Participant) qq.uniqueResult();
//                System.out.println(participant);
//                transaction.commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//
//                if(transaction !=null)
//                    transaction.rollback();
//            }
//        }
//        return participant;
        return null;
    }

    @Override
    public Iterable<Participant> findAll() {
        return null;
    }

    @Override
    public Participant update(Long aLong, Participant newEntity) {
        return null;
    }

    @Override
    public Participant delete(Long aLong) {
        return null;
    }

    @Override
    public Participant findOne(Long aLong) {
        return null;
    }

//    @Override
//    public Organizer findOneByUsernameAndPassword(String username, String password) {
//
//        Organizer organizer = null;
//        try(Session session = sessionFactory.openSession()){
//            Transaction transaction = null;
//            try{
//                transaction = session.beginTransaction();
//                System.out.println("inainte de create organizer");
//                System.out.println("sesion factory = "+sessionFactory);
//                System.out.println("sesion = "+session);
//                System.out.println("tranzaction = "+session);
//
//                Query qq = session.createQuery("From Organizer where username = ? and password = ?");
//
//                qq.setParameter(0,username).setParameter(1,password).uniqueResult();
//                System.out.println(qq);
//                organizer = (Organizer) qq.uniqueResult();
//                System.out.println(organizer);
//                transaction.commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//
//                if(transaction !=null)
//                    transaction.rollback();
//            }
//        }
//        return organizer;
//
//    }
//
//
//    @Override
//    public Organizer add(Organizer e) {
//        try(Session session = sessionFactory.openSession()){
//            Transaction transaction = null;
//            try{
//                transaction  =session.beginTransaction();
//                session.save(e);
//                transaction.commit();
//            } catch (Exception exception) {
//                if(transaction!=null){
//                    transaction.rollback();
//                }
//            }
//        }
//        return null;
//
//    }
}

