package vistas;

import com.formdev.flatlaf.FlatLightLaf;
import controller.UsuarioController;
import dao.UsuarioDAO;

import javax.swing.*;

public class VistaPrincipal {
    public static void main(String[] args) {
        UsuarioDAO dao = new UsuarioDAO();
        UsuarioController controller = new UsuarioController(dao);

        UsuarioVista vista = new UsuarioVista(controller);

        //flatLaf look and feel

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch(Exception ex) {
            System.err.println("No se pudo inicializar flatLaf");
        }

        vista.inicializar();
        vista.mostrar();
    }
}
