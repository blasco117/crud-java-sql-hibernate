package service;

import dao.UsuarioDAO;
import hibernate.HibernateUtil;
import model.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

import java.util.regex.Pattern;

public class UsuarioService {
    private UsuarioDAO usuariodao;

    public UsuarioService(UsuarioDAO usuariodao) {
        this.usuariodao = usuariodao;
    }

    public void guardarUsuario(Usuario u) {
        if(u == null) {
            throw new IllegalArgumentException("Usuario nulo.");
        }

        if(u.getContrasena() == null || u.getContrasena().isEmpty()) {
            throw new IllegalArgumentException("contraseña no puede ser nula");
        }

        if(u.getNombre() == null || u.getNombre().isEmpty()) {
            throw new IllegalArgumentException("nombre no puede ser nulo");
        }

        if(u.getApellido() == null || u.getApellido().isEmpty()) {
            throw new IllegalArgumentException("apellido no puede ser nulo");
        }

        if(u.getDireccion() == null || u.getDireccion().isEmpty()) {
            throw new IllegalArgumentException("direccion no puede ser nulo");
        }

        //revisar si el correo no es nulo, es unico y tiene formato correcto

        if(u.getCorreo() == null || u.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("correo no puede ser nulo");
        }

        String correo = u.getCorreo();
        String formatoCorreo = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (!Pattern.matches(formatoCorreo, correo)) {
            throw new IllegalArgumentException("el correo no tiene un formato válido.");
        }

        Boolean c = correoExisteNuevo(u.getCorreo());

        if(c == true) {
            throw new IllegalArgumentException("El correo ya esta registrado en otra cuenta.");
        }

        //revisar si el telefono no es nulo y tiene formato correcto

        if(u.getTelefono() == null || u.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("telefono no puede ser nulo");
        }

        String telefono = u.getTelefono();
        String formatoTelefono = "^\\+?[0-9]{10,20}$";

        if (!Pattern.matches(formatoTelefono, telefono)) {
            throw new IllegalArgumentException("el numero de telefono tiene un formato no valido");
        }

        //finalmente guardar el usuario

        usuariodao.guardar(u);
    }

    public Usuario obtenerUsuario(int id) {
        Usuario u = null;

        try {
            u = usuariodao.obtener(id);
        } catch(Exception e) {
            System.out.println("error: " + e.getMessage());
            e.printStackTrace();
        }

        return u;
    }

    public void actualizarUsuario(Usuario u) {
        if(u == null) {
            throw new IllegalArgumentException("Usuario nulo.");
        }

        if(u.getContrasena() == null || u.getContrasena().isEmpty()) {
            throw new IllegalArgumentException("contraseña no puede ser nula");
        }

        if(u.getNombre() == null || u.getNombre().isEmpty()) {
            throw new IllegalArgumentException("nombre no puede ser nulo");
        }

        if(u.getApellido() == null || u.getApellido().isEmpty()) {
            throw new IllegalArgumentException("apellido no puede ser nulo");
        }

        if(u.getDireccion() == null || u.getDireccion().isEmpty()) {
            throw new IllegalArgumentException("direccion no puede ser nulo");
        }

        //revisar si el correo no es nulo, es unico y tiene formato correcto

        if(u.getCorreo() == null || u.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("correo no puede ser nulo");
        }

        String correo = u.getCorreo();
        String formatoCorreo = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        if (!Pattern.matches(formatoCorreo, correo)) {
            throw new IllegalArgumentException("el correo no tiene un formato válido.");
        }

        Boolean c = correoExisteActualizar(u.getCorreo(), u.getUsuario_id());

        if(c == true) {
            throw new IllegalArgumentException("El correo ya esta registrado en otra cuenta.");
        }

        //revisar si el telefono no es nulo y tiene formato correcto

        if(u.getTelefono() == null || u.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("telefono no puede ser nulo");
        }

        String telefono = u.getTelefono();
        String formatoTelefono = "^\\+?[0-9]{10,20}$";

        if (!Pattern.matches(formatoTelefono, telefono)) {
            throw new IllegalArgumentException("el numero de telefono tiene un formato no valido");
        }

        //actualizar

        usuariodao.actualizar(u);
    }

    public void eliminarUsuario(int id) {
        try {
            usuariodao.eliminar(id);
        } catch(Exception e) {
            System.out.println("error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> listaUsuarios = null;

        try {
            listaUsuarios = usuariodao.listar();
        } catch(Exception e) {
            System.out.println("error: " + e.getMessage());
            e.printStackTrace();
        }

        return listaUsuarios;
    }

    public boolean correoExisteNuevo(String correo) {
        Session session = HibernateUtil.openSession();
        String consulta = "SELECT count(u) FROM Usuario u WHERE u.correo = :correo";
        Query<Long> query = session.createQuery(consulta, Long.class);
        query.setParameter("correo", correo);

        Long count = query.uniqueResult();

        session.close();

        return count > 0;
    }

    public boolean correoExisteActualizar(String correo, int id) {
        Session session = HibernateUtil.openSession();
        String consulta = "SELECT count(u) FROM Usuario u WHERE u.correo = :correo AND u.id != :id";
        Query<Long> query = session.createQuery(consulta, Long.class);
        query.setParameter("correo", correo);
        query.setParameter("id", id);

        Long count = query.uniqueResult();

        session.close();

        return count > 0;
    }

}
