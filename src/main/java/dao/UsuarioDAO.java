package dao;

import hibernate.HibernateUtil;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UsuarioDAO implements DAO<Usuario> {

    @Override
    public void guardar(Usuario u) {
        Session session = HibernateUtil.openSession();
        Transaction trx = null;

        try {
            trx = session.beginTransaction();
            session.save(u);
            trx.commit();
        } catch(Exception e) {
            if(trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public Usuario obtener(int id) {
        Session session = HibernateUtil.openSession();
        Usuario u = null;

        try {
            u = session.get(Usuario.class, id);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return u;
    }

    @Override
    public void actualizar(Usuario u) {
        Session session = HibernateUtil.openSession();
        Transaction trx = null;

        try {
            trx = session.beginTransaction();
            session.update(u);
            trx.commit();
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void eliminar(int id) {
        Session session = HibernateUtil.openSession();
        Transaction trx = null;

        try {
            trx = session.beginTransaction();
            Usuario u = session.get(Usuario.class, id);
            if (u != null) {
                session.delete(u);
            }
            trx.commit();
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Usuario> listar() {
        Session session = HibernateUtil.openSession();
        List<Usuario> usuarios = null;

        try {
            usuarios = session.createQuery("FROM Usuario", Usuario.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return usuarios;
    }
}
