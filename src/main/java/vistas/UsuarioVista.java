package vistas;

import com.formdev.flatlaf.FlatLightLaf;
import controller.UsuarioController;
import model.Usuario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.concurrent.Flow;

public class UsuarioVista {
    private JFrame frame;
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;
    private UsuarioController usuarioController;

    public UsuarioVista(UsuarioController usuarioController) {
        this.usuarioController = usuarioController;
    }

    public void mostrar() {
        frame.setVisible(true);
    }

    public void inicializar() {
        frame = new JFrame();
        frame.setTitle("CRUD JAVA");
        frame.setBounds(100,100,800,600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setHgap(85);

        //panel titulo

        JPanel panelTitulo = new JPanel();
        JLabel titulo = new JLabel("Gestion de Usuarios");

        panelTitulo.add(titulo);

        //panel botones

        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Nuevo usuario");
        btnAgregar.setPreferredSize(new Dimension(120,30));
        JButton btnActualizar = new JButton("Actualizar usuario");
        btnActualizar.setPreferredSize(new Dimension(120,30));
        JButton btnEliminar = new JButton("Eliminar usuario");
        btnEliminar.setPreferredSize(new Dimension(120,30));

        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        //panel principal

        JPanel panelPrincipal = new JPanel(flowLayout);

        panelPrincipal.add(panelTitulo);
        panelPrincipal.add(panelBotones);

        frame.getContentPane().add(panelPrincipal, BorderLayout.NORTH);

        //tabla

        modeloTabla = new DefaultTableModel(new Object[] {"id", "nombre", "apellido", "contraseña", "correo", "direccion", "telefono"},0);

        tablaUsuarios = new JTable(modeloTabla){
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaUsuarios.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        //evento boton Nuevo usuario

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                formularioNuevoUsuario();
            }
        });

        //evento boton Actualizar

        btnActualizar.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               boolean seleccionTabla = tablaUsuarios.getSelectionModel().isSelectionEmpty();

               if(!seleccionTabla) {
                   int idUsuario = (int) tablaUsuarios.getModel().getValueAt(tablaUsuarios.getSelectedRow(),0);
                   formularioActualizarUsuario(idUsuario);
               } else {
                   JOptionPane.showMessageDialog(frame,"Debe seleccionar un usuario");
               }
           }
        });

        //evento boton Eliminar

        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean seleccionTabla = tablaUsuarios.getSelectionModel().isSelectionEmpty();

                if(!seleccionTabla) {
                    UIManager.put("OptionPane.yesButtonText", "Sí");
                    UIManager.put("OptionPane.noButtonText", "No");

                    int confirmacion = JOptionPane.showConfirmDialog(frame,"Desea eliminar el usuario?","Confirmacion",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

                    if(confirmacion == JOptionPane.YES_OPTION) {
                        int idUsuario = (int) tablaUsuarios.getModel().getValueAt(tablaUsuarios.getSelectedRow(),0);
                        usuarioController.eliminarUsuario(idUsuario);
                        tablaUsuarios.getSelectionModel().clearSelection();
                        cargarUsuarios();
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,"Debe seleccionar un usuario");
                    tablaUsuarios.getSelectionModel().clearSelection();
                }
            }
        });

        //cargar usuarios en la tabla

        cargarUsuarios();

    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = usuarioController.listarUsuarios();
        modeloTabla.setRowCount(0);

        for (Usuario u : usuarios) {
            modeloTabla.addRow(new Object[]{u.getUsuario_id(), u.getNombre(), u.getApellido(), u.getContrasena(), u.getCorreo(), u.getDireccion(), u.getTelefono()});
        }
    }

    private void formularioNuevoUsuario() {
        JDialog formulario = new JDialog(frame, "Nuevo usuario", true);
        formulario.setSize(400,400);
        formulario.setLocationRelativeTo(frame);
        formulario.setResizable(false);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(null);

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setBounds(20,20,100,25);
        JLabel labelApellido = new JLabel("Apellido:");
        labelApellido.setBounds(20,60,100,25);
        JLabel labelCorreo = new JLabel("Correo:");
        labelCorreo.setBounds(20,100,100,25);
        JLabel labelContrasena = new JLabel("Contrasena:");
        labelContrasena.setBounds(20,140,100,25);
        JLabel labelDireccion = new JLabel("Direccion:");
        labelDireccion.setBounds(20,180,100,25);
        JLabel labelTelefono = new JLabel("Telefono:");
        labelTelefono.setBounds(20,220,100,25);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(130,20,240,25);
        JTextField txtApellido = new JTextField();
        txtApellido.setBounds(130,60,240,25);
        JTextField txtCorreo = new JTextField();
        txtCorreo.setBounds(130,100,240,25);
        JTextField txtContrasena = new JTextField();
        txtContrasena.setBounds(130,140,240,25);
        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(130,180,240,25);
        JTextField txtTelefono = new JTextField();
        txtTelefono.setBounds(130,220,240,25);

        //panels

        panelFormulario.add(labelNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(labelApellido);
        panelFormulario.add(txtApellido);
        panelFormulario.add(labelCorreo);
        panelFormulario.add(txtCorreo);
        panelFormulario.add(labelContrasena);
        panelFormulario.add(txtContrasena);
        panelFormulario.add(labelDireccion);
        panelFormulario.add(txtDireccion);
        panelFormulario.add(labelTelefono);
        panelFormulario.add(txtTelefono);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds((400 - 100) / 2, 280,100,30);
        btnGuardar.setHorizontalAlignment(SwingConstants.CENTER);
        btnGuardar.setVerticalAlignment(SwingConstants.CENTER);

        panelFormulario.add(btnGuardar);

        formulario.add(panelFormulario);

        //evento boton Guardar

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String correo = txtCorreo.getText();
                String contrasena = txtContrasena.getText();
                String direccion = txtDireccion.getText();
                String telefono = txtTelefono.getText();

                try {
                    usuarioController.guardarUsuario(nombre, apellido, correo, contrasena, direccion, telefono);
                    JOptionPane.showMessageDialog(formulario,"usuario creado con exito");
                    formulario.dispose();
                    tablaUsuarios.getSelectionModel().clearSelection();
                    cargarUsuarios();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(formulario,"Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formulario.setVisible(true);

    }

    public void formularioActualizarUsuario(int idUsuario) {
        Usuario u = usuarioController.obtenerUsuario(idUsuario);

        JDialog formulario = new JDialog(frame, "Actualizar usuario", true);
        formulario.setSize(400,400);
        formulario.setLocationRelativeTo(frame);
        formulario.setResizable(false);

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(null);

        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setBounds(20,20,100,25);
        JLabel labelApellido = new JLabel("Apellido:");
        labelApellido.setBounds(20,60,100,25);
        JLabel labelCorreo = new JLabel("Correo:");
        labelCorreo.setBounds(20,100,100,25);
        JLabel labelContrasena = new JLabel("Contrasena:");
        labelContrasena.setBounds(20,140,100,25);
        JLabel labelDireccion = new JLabel("Direccion:");
        labelDireccion.setBounds(20,180,100,25);
        JLabel labelTelefono = new JLabel("Telefono:");
        labelTelefono.setBounds(20,220,100,25);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(130,20,240,25);
        JTextField txtApellido = new JTextField();
        txtApellido.setBounds(130,60,240,25);
        JTextField txtCorreo = new JTextField();
        txtCorreo.setBounds(130,100,240,25);
        JTextField txtContrasena = new JTextField();
        txtContrasena.setBounds(130,140,240,25);
        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(130,180,240,25);
        JTextField txtTelefono = new JTextField();
        txtTelefono.setBounds(130,220,240,25);

        txtNombre.setText(u.getNombre());
        txtApellido.setText(u.getApellido());
        txtCorreo.setText(u.getCorreo());
        txtContrasena.setText(u.getContrasena());
        txtDireccion.setText(u.getDireccion());
        txtTelefono.setText(u.getTelefono());

        //panels

        panelFormulario.add(labelNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(labelApellido);
        panelFormulario.add(txtApellido);
        panelFormulario.add(labelCorreo);
        panelFormulario.add(txtCorreo);
        panelFormulario.add(labelContrasena);
        panelFormulario.add(txtContrasena);
        panelFormulario.add(labelDireccion);
        panelFormulario.add(txtDireccion);
        panelFormulario.add(labelTelefono);
        panelFormulario.add(txtTelefono);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds((400 - 100) / 2, 280,100,30);
        btnGuardar.setHorizontalAlignment(SwingConstants.CENTER);
        btnGuardar.setVerticalAlignment(SwingConstants.CENTER);

        panelFormulario.add(btnGuardar);

        formulario.add(panelFormulario);

        //evento boton Guardar

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String correo = txtCorreo.getText();
                String contrasena = txtContrasena.getText();
                String direccion = txtDireccion.getText();
                String telefono = txtTelefono.getText();

                try {
                    usuarioController.actualizarUsuario(u.getUsuario_id(),nombre, apellido, correo, contrasena, direccion, telefono);
                    JOptionPane.showMessageDialog(formulario,"usuario actualizado con exito");
                    formulario.dispose();
                    tablaUsuarios.getSelectionModel().clearSelection();
                    cargarUsuarios();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(formulario,"Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        formulario.setVisible(true);

    }

}
