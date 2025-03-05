package controller;

import dao.UsuarioDAO;
import model.Usuario;
import service.UsuarioService;

import java.util.List;

public class UsuarioController {
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioDAO UD) {
        this.usuarioService = new UsuarioService(UD);
    }

    public void guardarUsuario(String nombre, String apellido, String correo, String contrasena, String direccion, String telefono) {
        try {
            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCorreo(correo);
            u.setContrasena(contrasena);
            u.setDireccion(direccion);
            u.setTelefono(telefono);

            this.usuarioService.guardarUsuario(u);
            System.out.println("usuario guardado");
        } catch(IllegalArgumentException e) {
            throw e;
        }

    }

    public Usuario obtenerUsuario(int id) {
        try {
            Usuario u = this.usuarioService.obtenerUsuario(id);

            if(u != null) {
                System.out.println("usuario encontrado");
            } else {
                System.out.println("usuario no encontrado");
            }
            return u;
        } catch(Exception e) {
            System.out.println("error intentando obtener usuario");
            return null;
        }
    }

    public void actualizarUsuario(int id,String nombre, String apellido, String correo, String contrasena, String direccion, String telefono) {
        try {
            Usuario u = this.usuarioService.obtenerUsuario(id);

            if (u == null) {
                System.out.println("Usuario no encontrado");
                return;
            }

            u.setNombre(nombre);
            u.setApellido(apellido);
            u.setCorreo(correo);
            u.setContrasena(contrasena);
            u.setDireccion(direccion);
            u.setTelefono(telefono);

            this.usuarioService.actualizarUsuario(u);
            System.out.println("usuario actualizado");
        } catch(IllegalArgumentException e) {
            throw e;
        }
    }

    public void eliminarUsuario(int id) {
        Usuario u = this.usuarioService.obtenerUsuario(id);

        try {
            if(u != null) {
                this.usuarioService.eliminarUsuario(id);
                System.out.println("usuario eliminado");
            } else {
                System.out.println("usuario no encontrado");
            }
        } catch(IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("error: " + e.getMessage());
        }
    }

    public List<Usuario> listarUsuarios() {
        return this.usuarioService.listarUsuarios();
    }

}
