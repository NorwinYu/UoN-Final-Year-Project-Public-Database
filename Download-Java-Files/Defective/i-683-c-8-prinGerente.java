/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.DBConnection;
import Model.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author melissa
 */
public class prinGerente extends javax.swing.JFrame {
    
    //la variable botonAceptar indica el uso que se le da al botón de la izquierda para cada boton principal
    //1:Agregar, 2:Modificar, 3:Consultar, 4:Despedir, 0:nada
    private int botonAceptar = 0;
    private DBConnection bD;
    private String[] listaIds;
    
    public prinGerente(DBConnection baseDatos) {
        initComponents();
        
        bD = baseDatos;
        
        //Fecha
        Date fechaSist = new Date(); 
        SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy");
        fecha.setText(formato.format(fechaSist));
        
        //Hora
        Timer tiempo = new Timer(100, new prinGerente.hora());
        tiempo.start();
        
        cambiarVisibilidadCampos(false);
        bAceptar.setVisible(false);
        labEmple.setVisible(false);
        comboxEmple.setVisible(false);
        
        //Elementos del popup menú para modf,agregar y consultar
        this.usuarios.setSelected(true);
        this.sede.setSelected(false);
        
        //Elementos del popup menú para reportes
        this.usuario.setSelected(true);
        this.sedes.setSelected(false);
        this.ventas.setSelected(false);
        
       }

    class hora implements ActionListener{
    
        public void actionPerformed(ActionEvent e){
        
        Date fechaHora = new Date();
        String pmAm = "hh:mm:ss a";
        SimpleDateFormat format = new SimpleDateFormat(pmAm);
        Calendar hoy = Calendar.getInstance();
        hora.setText(String.format(format.format(fechaHora), hoy));
      
        }
    }
    
    public static boolean validarFecha(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
            return true;
    }    
    
    private boolean validarCampos(String nombreUsu, String nombre, String cedula, String correo, String cuenta, String direccion, String telefono, String salario, String fechaNac) {
        boolean validacion = true, fechaValida; // validacion, en un principio, es solo para los campos vacios
        String mensaje = ""; //En caso de hayan campos invalidos
        
        if(nombreUsu.equals("")){ mensaje = mensaje+"- Nombre de Usuario\n"; validacion = false; }       
        if(nombre.equals("")){ mensaje = mensaje+"- Nombre\n"; validacion = false; }
        if(cedula.equals("")){ mensaje = mensaje+"- Cedula\n"; validacion = false; }
        if(correo.equals("")){ mensaje = mensaje+"- Correo\n"; validacion = false; }
        if(cuenta.equals("")){ mensaje = mensaje+"- Cuenta\n"; validacion = false; }
        if(direccion.equals("")){ mensaje = mensaje+"- Direccion\n"; validacion = false; }
        if(telefono.equals("")){ mensaje = mensaje+"- Telefono\n"; validacion = false; }
        if(salario.equals("")){ mensaje = mensaje+"- Salario\n"; validacion = false; }
        fechaValida = validarFecha(fechaNac);
        
        System.out.println(mensaje);
        
        if(!validacion){ //Hay campos vacios
            mensaje = "Los siguientes campos están vacios:\n"+mensaje;
            if(fechaValida) mensaje = "La fecha de nacimiento es invalida\n\n"+mensaje;
        }else{
            if(fechaValida){ //No hay campos vacios, pero la fecha es invalida
                mensaje = "La fecha de nacimiento es invalida";
                validacion = false; //Se cambia ya que la fecha no es valida
            }
        }
        
        if(!mensaje.equals("")) JOptionPane.showMessageDialog(this, mensaje);
        
        return validacion;
    }
    
    
        //Retorna una lista con los Ids de los empleados en listaEmpleados el cual es una lista de strings del
    //tipo {"id1,nombre1,cedula1",..."idN,nombreN,cedulaN"} donde N es la cantiadad de empleados
    private String[] obtenerListaIds(String[] listaEmpleados){
        String[] listaDeIds = new String[listaEmpleados.length];
        String[] empleado;
        
        for(int i=0; i<(listaDeIds.length); i++){
            empleado = listaEmpleados[i].split(",");
            listaDeIds[i] = empleado[0];
        }
        
        return listaDeIds;
    }
    
    //Retorna una lista con las opciones para combobox comboxEmple con el formato
    //{"nombre1 cedula1",..."nombreN cedulaN"} obtenidas de listaEmpleado el cual es una lista de strings del
    //tipo {"id1,nombre1,cedula1",..."idN,nombreN,cedulaN"} donde N es la cantiadad de empleados
    private String[] obtenerOpciones(String[] listaEmpleados){
        String[] opciones = new String[listaEmpleados.length+1];
        String[] empleado;
        opciones[0] = "No seleccionado";
        
        for(int i=0,j=1; i<(listaEmpleados.length); i++,j++){
            empleado = listaEmpleados[i].split(",");
            opciones[j] = empleado[1]+" "+empleado[2].replace("$","");
        }
        
        return opciones;
    }
    
    private void actualizarComboxEmpleVendedores(){
        String empleados = bD.listarVendedores();        
        
        if(empleados.equals("")){ //No Hay empleados
           String[] opciones = { "No seleccionado" };
           comboxEmple.setModel(new DefaultComboBoxModel(opciones));
        }else{ //Hay empleados
            String[] listaEmpleados = empleados.split("\\$");
            listaIds = obtenerListaIds(listaEmpleados);
            String[] opciones = obtenerOpciones(listaEmpleados);
            comboxEmple.setModel(new DefaultComboBoxModel(opciones));
            
        }
    }
    
    
    private void actualizarComboxEmpleJefes(){
        String empleados = bD.listarJefesTaller();        
        
        if(empleados.equals("")){ //No Hay empleados
           String[] opciones = { "No seleccionado" };
           comboxEmple.setModel(new DefaultComboBoxModel(opciones));
        }else{ //Hay empleados
            String[] listaEmpleados = empleados.split("\\$");
            listaIds = obtenerListaIds(listaEmpleados);
            String[] opciones = obtenerOpciones(listaEmpleados);
            comboxEmple.setModel(new DefaultComboBoxModel(opciones));
            
        }
    }
        
        
    //Limpia los campos(jTextfields) de la interfaz
    private void limpiarCamposUsuarios(){
        tNombreUsu.setText("");
        tContra.setText("");
        tNombre.setText("");
        tCedula.setText("");
        tCorreo.setText("");
        tCuentaBan.setText("");
        tDir.setText("");
        tTel.setText("");
        tSal.setText("");
        tIdGerente.setText("");
        comboxDia.setSelectedIndex(0);
        comboxMes.setSelectedIndex(0);
        comboxAno.setSelectedIndex(0);
    }
    
    private void llenarCamposModfVendedor(){
        String id = listaIds[comboxEmple.getSelectedIndex()-1];
        Vendedor ven = bD.leerVendedorPorId(id);
        
        tNombreUsu.setText(ven.getNombreUsuario());
        tContra.setText(ven.getContrasena());
        tNombre.setText(ven.getNombre());
        tCedula.setText(ven.getCedula());
        tCorreo.setText(ven.getCorreo());
        tCuentaBan.setText(ven.getCuentaBancaria());
        comboxGenero.setSelectedIndex(ven.getGenero());
        tDir.setText(ven.getDireccion());
        tTel.setText(ven.getTelefono());
        tSal.setText(Float.toString(ven.getSalario()));
        tIdGerente.setText(ven.getManagerId());
        
        String[] fechaNac = ven.getFechaNacimiento().split("/");
        int diaNac = Integer.parseInt(fechaNac[0]);
        int mesNac = obtenerMesNum(fechaNac[1]);
        int anoNac = Integer.parseInt(fechaNac[2]);
        
        comboxDia.setSelectedIndex(diaNac-1); //El Combobox empieza desde 0
        comboxMes.setSelectedIndex(mesNac-1);
        comboxAno.setSelectedIndex((anoNac-2000)*-1); //El año 2000 es la posición 0, *-1 porque puede dar negativo
    }
    
    private void llenarCamposModfJefeTaller(){
        String id = listaIds[comboxEmple.getSelectedIndex()-1];
        JefeTaller jef = bD.leerJefeTallerPorId(id);
        
        tNombreUsu.setText(jef.getNombreUsuario());
        tContra.setText(jef.getContrasena());
        tNombre.setText(jef.getNombre());
        tCedula.setText(jef.getCedula());
        tCorreo.setText(jef.getCorreo());
        tCuentaBan.setText(jef.getCuentaBancaria());
        comboxGenero.setSelectedIndex(jef.getGenero());
        tDir.setText(jef.getDireccion());
        tTel.setText(jef.getTelefono());
        tSal.setText(Float.toString(jef.getSalario()));
        tIdGerente.setText(jef.getManagerId());
        
        String[] fechaNac = jef.getFechaNacimiento().split("/");
        int diaNac = Integer.parseInt(fechaNac[0]);
        int mesNac = obtenerMesNum(fechaNac[1]);
        int anoNac = Integer.parseInt(fechaNac[2]);
        
        comboxDia.setSelectedIndex(diaNac-1); //El Combobox empieza desde 0
        comboxMes.setSelectedIndex(mesNac-1);
        comboxAno.setSelectedIndex((anoNac-2000)*-1); //El año 2000 es la posición 0, *-1 porque puede dar negativo
    }
    
    //Retorna el valor numerico del mes, la variable mes tiene el formato puesto en el comboxMes
    private int obtenerMesNum(String mes){
        //ene, feb, mar, abr, may, jun, jul, ago, sep, oct, nov, dic
        switch(mes){
            case "ene":
                return 1;
            case "feb":
                return 2;
            case "mar":
                return 3;
            case "abr":
                return 4;
            case "may":
                return 5;
            case "jun":
                return 6;
            case "jul":
                return 7;
            case "ago":
                return 8;
            case "sep":
                return 9;
            case "oct":
                return 10;
            case "nov":
                return 11;
            case "dic":
                return 12;
        }
        return 0;
    }
    
    // Calcula la edad de nacimiento apartir de un string de fecha, ejemplo del string "01/jul/1985"
    private int calcularEdad(String nacimiento){
        int edad,anoN,anoHoy,mesN,mesHoy,diaN,diaHoy;
        Date fechaSist = new Date(); 
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaHoy = formato.format(fechaSist);
        
        String[] fechaAct = fechaHoy.split("/");        
        String[] fechaNac = nacimiento.split("/");
        
        anoN = Integer.parseInt(fechaNac[2]);
        anoHoy = Integer.parseInt(fechaAct[2]);
        mesN = obtenerMesNum(fechaNac[1]);
        mesHoy = Integer.parseInt(fechaAct[1]);
        diaN = Integer.parseInt(fechaNac[0]);
        diaHoy = Integer.parseInt(fechaAct[0]);
        
        edad = anoHoy-anoN;
        
        if(mesHoy<mesN) edad--;
        if(mesHoy==mesN){
            if(diaHoy<diaN){
                edad--;
            }            
        }
        
        return edad;
    }
    
    private void agregarVendedor(){
        String nombreUsu = tNombreUsu.getText();
        String nombre = tNombre.getText();
        String cedula = tCedula.getText();
        String cargo = comboxCargo.getSelectedItem().toString();
        String correo = tCorreo.getText();
        String cuenta = tCuentaBan.getText();
        String idGerente = tIdGerente.getText();
        int genero = comboxGenero.getSelectedIndex();
        String direccion = tDir.getText();
        String telefono = tTel.getText();
        float salario;
        if(tSal.getText().equals("")){
            salario = 0;
        }else{
            salario = Float.valueOf(tSal.getText());
        }
        String diaCumple = comboxDia.getItemAt(comboxDia.getSelectedIndex());
        String mesCumple = comboxMes.getItemAt(comboxMes.getSelectedIndex());
        String anoCumple = comboxAno.getItemAt(comboxAno.getSelectedIndex());
        String fechaNac = diaCumple+"/"+mesCumple+"/"+anoCumple;
                       
        boolean validacion = validarCampos(nombreUsu,nombre,cedula,correo,cuenta,direccion,telefono,tSal.getText(),fechaNac);
        if(validacion){
            //Fecha de reg
            Date fechaSist = new Date(); 
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String fechaReg = formato.format(fechaSist);           
            
            String respuesta = bD.crearVendedor(nombre, cedula,cargo,telefono,direccion,genero,fechaNac,correo,salario, cuenta,fechaReg, nombreUsu,idGerente);
            if(respuesta.contains("La cedula")) limpiarCamposUsuarios();
            JOptionPane.showMessageDialog(this, respuesta);
        }        
    }
    
    private void agregarJefeTaller(){
    String nombreUsu = tNombreUsu.getText();
    String nombre = tNombre.getText();
    String cedula = tCedula.getText();
    String cargo = comboxCargo.getSelectedItem().toString();
    String correo = tCorreo.getText();
    String cuenta = tCuentaBan.getText();
    int genero = comboxGenero.getSelectedIndex();
    String direccion = tDir.getText();
    String telefono = tTel.getText();
    String idGerente = tIdGerente.getText();
    float salario;
    if(tSal.getText().equals("")){
        salario = 0;
    }else{
        salario = Float.valueOf(tSal.getText());
    }
    String diaCumple = comboxDia.getItemAt(comboxDia.getSelectedIndex());
    String mesCumple = comboxMes.getItemAt(comboxMes.getSelectedIndex());
    String anoCumple = comboxAno.getItemAt(comboxAno.getSelectedIndex());
    String fechaNac = diaCumple+"/"+mesCumple+"/"+anoCumple;

    boolean validacion = validarCampos(nombreUsu,nombre,cedula,correo,cuenta,direccion,telefono,tSal.getText(),fechaNac);
    if(validacion){
        //Fecha de reg
        Date fechaSist = new Date(); 
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaReg = formato.format(fechaSist);           

        String respuesta = bD.crearJefeTaller(nombreUsu,nombre,cedula,cargo, telefono,direccion,genero,fechaNac,correo,salario, cuenta, fechaReg, idGerente);
        if(respuesta.contains("La cedula")) limpiarCamposUsuarios();
        JOptionPane.showMessageDialog(this, respuesta);
    }        
}
    
    private void modificarVendedor(){        
        String mensaje = "";
        
        //Datos Modf
        String nombreUsu = tNombreUsu.getText();
        String contrasena = tContra.getText();
        String nombre = tNombre.getText();
        String cedula = tCedula.getText();
        String correo = tCorreo.getText();
        String cuenta = tCuentaBan.getText();
        String cargo = comboxCargo.getSelectedItem().toString();
        int genero = comboxGenero.getSelectedIndex();
        String direccion = tDir.getText();
        String telefono = tTel.getText();
        float salario;
        
        if(tSal.getText().equals("")){
            salario = 0;
        }else{
            salario = Float.valueOf(tSal.getText());
        }
        
        boolean estado;
        if(comboxEstado.getSelectedItem().toString().equals("Activo")){
            estado=true;
        }else{
            estado=false;
        }
        String diaCumple = comboxDia.getItemAt(comboxDia.getSelectedIndex());
        String mesCumple = comboxMes.getItemAt(comboxMes.getSelectedIndex());
        String anoCumple = comboxAno.getItemAt(comboxAno.getSelectedIndex());
        String fechaNac = diaCumple+"/"+mesCumple+"/"+anoCumple;
        
        //Datos Anteriores
        String id = listaIds[comboxEmple.getSelectedIndex()-1];
        Vendedor ven = bD.leerVendedorPorId(id);
        
        //Comparación
        if(!nombreUsu.equals(ven.getNombreUsuario())) mensaje = mensaje+"Nombre Usuario\n";
        if(!contrasena.equals(ven.getContrasena())) mensaje = mensaje+"Contraseña\n";
        if(!nombre.equals(ven.getNombre())) mensaje = mensaje+"Nombre\n";
        if(!cedula.equals(ven.getCedula())) mensaje = mensaje+"Cedula\n";
        if(!correo.equals(ven.getCorreo())) mensaje = mensaje+"Correo\n";
        if(!cuenta.equals(ven.getCuentaBancaria())) mensaje = mensaje+"Cuenta Bancaria\n";
        if(genero != ven.getGenero()) mensaje = mensaje+"Genero\n";
        if(!direccion.equals(ven.getDireccion())) mensaje = mensaje+"Direccion\n";
        if(!telefono.equals(ven.getTelefono())) mensaje = mensaje+"Telefono\n";
        if(salario != ven.getSalario()) mensaje = mensaje+"Salario\n";
        if(!fechaNac.equals(ven.getFechaNacimiento())) mensaje = mensaje+"Fecha de nacimiento\n";
        if(estado != ven.isHabilitado()) mensaje = mensaje+"Estado\n";
        
        if(!mensaje.equals("")){
            mensaje = "Los siguientes campos se van a modificar:\n"+mensaje;
            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "", 0);
            
            if(opcion==0){ //Modificar
                String respuesta = bD.actualizarVendedor(id, nombre,cedula, cargo,telefono,direccion,genero,fechaNac, correo, salario, cuenta, ven.getFechaRegistro(),nombreUsu, contrasena, ven.getManagerId(), estado, ven.getFechaDespido());
                JOptionPane.showMessageDialog(this, respuesta);
            }
        }else{
            mensaje = "Cambie un campo para modificar al Vendedor";
            JOptionPane.showMessageDialog(this, mensaje);
        }
    }
    
    
    private void modificarJefeTaller(){        
        String mensaje = "";
        
        //Datos Modf
        String nombreUsu = tNombreUsu.getText();
        String contrasena = tContra.getText();
        String nombre = tNombre.getText();
        String cedula = tCedula.getText();
        String correo = tCorreo.getText();
        String cuenta = tCuentaBan.getText();
        String cargo = comboxCargo.getSelectedItem().toString();
        int genero = comboxGenero.getSelectedIndex();
        String direccion = tDir.getText();
        String telefono = tTel.getText();
        float salario;
        
        if(tSal.getText().equals("")){
            salario = 0;
        }else{
            salario = Float.valueOf(tSal.getText());
        }
        
        boolean estado;
        if(comboxEstado.getSelectedItem().toString().equals("Activo")){
            estado=true;
        }else{
            estado=false;
        }
        String diaCumple = comboxDia.getItemAt(comboxDia.getSelectedIndex());
        String mesCumple = comboxMes.getItemAt(comboxMes.getSelectedIndex());
        String anoCumple = comboxAno.getItemAt(comboxAno.getSelectedIndex());
        String fechaNac = diaCumple+"/"+mesCumple+"/"+anoCumple;
        
        //Datos Anteriores
        String id = listaIds[comboxEmple.getSelectedIndex()-1];
        JefeTaller jef = bD.leerJefeTallerPorId(id);
        
        //Comparación
        if(!nombreUsu.equals(jef.getNombreUsuario())) mensaje = mensaje+"Nombre Usuario\n";
        if(!contrasena.equals(jef.getContrasena())) mensaje = mensaje+"Contraseña\n";
        if(!nombre.equals(jef.getNombre())) mensaje = mensaje+"Nombre\n";
        if(!cedula.equals(jef.getCedula())) mensaje = mensaje+"Cedula\n";
        if(!correo.equals(jef.getCorreo())) mensaje = mensaje+"Correo\n";
        if(!cuenta.equals(jef.getCuentaBancaria())) mensaje = mensaje+"Cuenta Bancaria\n";
        if(genero != jef.getGenero()) mensaje = mensaje+"Genero\n";
        if(!direccion.equals(jef.getDireccion())) mensaje = mensaje+"Direccion\n";
        if(!telefono.equals(jef.getTelefono())) mensaje = mensaje+"Telefono\n";
        if(salario != jef.getSalario()) mensaje = mensaje+"Salario\n";
        if(!fechaNac.equals(jef.getFechaNacimiento())) mensaje = mensaje+"Fecha de nacimiento\n";
        if(estado != jef.isHabilitado()) mensaje = mensaje+"Estado\n";
        
        if(!mensaje.equals("")){
            mensaje = "Los siguientes campos se van a modificar:\n"+mensaje;
            int opcion = JOptionPane.showConfirmDialog(this, mensaje, "", 0);
            
            if(opcion==0){ //Modificar
                String respuesta = bD.actualizarJefe(id, contrasena,nombreUsu,nombre,cedula,cargo,telefono,direccion,genero, fechaNac,correo,salario,cuenta,jef.getFechaRegistro(),jef.getManagerId(),estado,jef.getFechaDespido());
                JOptionPane.showMessageDialog(this, respuesta);
            }
        }else{
            mensaje = "Cambie un campo para modificar al Jefe de Taller";
            JOptionPane.showMessageDialog(this, mensaje);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        users = new javax.swing.JPopupMenu();
        usuarios = new javax.swing.JCheckBoxMenuItem();
        sede = new javax.swing.JCheckBoxMenuItem();
        opReporte = new javax.swing.JPopupMenu();
        usuario = new javax.swing.JCheckBoxMenuItem();
        sedes = new javax.swing.JCheckBoxMenuItem();
        ventas = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        iconUsu = new javax.swing.JLabel();
        labLogo = new javax.swing.JLabel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        bAgregar = new javax.swing.JButton();
        bModf = new javax.swing.JButton();
        bConsul = new javax.swing.JButton();
        bDespedir = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        labNombreUsu = new javax.swing.JLabel();
        labCedula = new javax.swing.JLabel();
        labCargo = new javax.swing.JLabel();
        labCorreo = new javax.swing.JLabel();
        labCuentaBan = new javax.swing.JLabel();
        labGenero = new javax.swing.JLabel();
        labDir = new javax.swing.JLabel();
        labTel = new javax.swing.JLabel();
        labSal = new javax.swing.JLabel();
        labSede = new javax.swing.JLabel();
        labFechaNac = new javax.swing.JLabel();
        tNombreUsu = new javax.swing.JTextField();
        tCedula = new javax.swing.JTextField();
        tCorreo = new javax.swing.JTextField();
        tCuentaBan = new javax.swing.JTextField();
        tDir = new javax.swing.JTextField();
        labNombre = new javax.swing.JLabel();
        tNombre = new javax.swing.JTextField();
        comboxGenero = new javax.swing.JComboBox<>();
        tTel = new javax.swing.JTextField();
        tSal = new javax.swing.JTextField();
        comboxSedes = new javax.swing.JComboBox<>();
        comboxDia = new javax.swing.JComboBox<>();
        comboxMes = new javax.swing.JComboBox<>();
        comboxAno = new javax.swing.JComboBox<>();
        comboxCargo = new javax.swing.JComboBox<>();
        bAceptar = new javax.swing.JButton();
        labEmple = new javax.swing.JLabel();
        comboxEmple = new javax.swing.JComboBox<>();
        labContra = new javax.swing.JLabel();
        tContra = new javax.swing.JTextField();
        labSede1 = new javax.swing.JLabel();
        tIdGerente = new javax.swing.JTextField();
        labSede2 = new javax.swing.JLabel();
        comboxEstado = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        fechaYhora = new javax.swing.JLabel();
        fecha = new javax.swing.JLabel();
        hora = new javax.swing.JLabel();

        users.setToolTipText("");

        usuarios.setSelected(true);
        usuarios.setText("Usuario");
        usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usuariosMouseReleased(evt);
            }
        });
        users.add(usuarios);

        sede.setSelected(true);
        sede.setText("Sedes");
        sede.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sedeMouseReleased(evt);
            }
        });
        users.add(sede);

        usuario.setSelected(true);
        usuario.setText("Gestion usuarios");
        usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                usuarioMouseReleased(evt);
            }
        });
        opReporte.add(usuario);

        sedes.setSelected(true);
        sedes.setText("Gestion Sedes");
        sedes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sedesMouseReleased(evt);
            }
        });
        opReporte.add(sedes);

        ventas.setSelected(true);
        ventas.setText("Gestion Ventas");
        ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ventasMouseReleased(evt);
            }
        });
        opReporte.add(ventas);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setLayout(new java.awt.BorderLayout());

        iconUsu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ICO menuSuper.png"))); // NOI18N

        labLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logo.png"))); // NOI18N
        labLogo.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(labLogo)
                .addGap(310, 310, 310)
                .addComponent(iconUsu)
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(iconUsu)
                    .addComponent(labLogo))
                .addGap(0, 0, 0))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        bAgregar.setForeground(new java.awt.Color(51, 51, 51));
        bAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ICO addUser.png"))); // NOI18N
        bAgregar.setText("Agregar");
        bAgregar.setBorderPainted(false);
        bAgregar.setContentAreaFilled(false);
        bAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bAgregarMouseReleased(evt);
            }
        });
        bAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAgregarActionPerformed(evt);
            }
        });

        bModf.setForeground(new java.awt.Color(51, 51, 51));
        bModf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ICO modifyUser.png"))); // NOI18N
        bModf.setText("Modificar");
        bModf.setBorderPainted(false);
        bModf.setContentAreaFilled(false);
        bModf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bModfMouseReleased(evt);
            }
        });

        bConsul.setForeground(new java.awt.Color(51, 51, 51));
        bConsul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ICO searchUser.png"))); // NOI18N
        bConsul.setText("Consultar");
        bConsul.setBorderPainted(false);
        bConsul.setContentAreaFilled(false);
        bConsul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bConsulMouseReleased(evt);
            }
        });

        bDespedir.setForeground(new java.awt.Color(51, 51, 51));
        bDespedir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ICO fireUser.png"))); // NOI18N
        bDespedir.setText("Despedir");
        bDespedir.setBorderPainted(false);
        bDespedir.setContentAreaFilled(false);
        bDespedir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bDespedirMouseReleased(evt);
            }
        });

        jToggleButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/gear.png"))); // NOI18N
        jToggleButton1.setText("Reportes");
        jToggleButton1.setBorderPainted(false);
        jToggleButton1.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bAgregar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bModf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bConsul, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bDespedir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(bAgregar)
                .addGap(18, 18, 18)
                .addComponent(bModf)
                .addGap(18, 18, 18)
                .addComponent(bConsul)
                .addGap(18, 18, 18)
                .addComponent(bDespedir)
                .addGap(18, 18, 18)
                .addComponent(jToggleButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(41, 41, 41))
        );

        jSplitPane2.setLeftComponent(jPanel3);

        labNombreUsu.setText("Nombre de usuario:");

        labCedula.setText("Cedula:");

        labCargo.setText("Cargo:");

        labCorreo.setText("Correo:");

        labCuentaBan.setText("Cuenta Bancaria:");

        labGenero.setText("Genero:");

        labDir.setText("Dirección:");

        labTel.setText("Telefono:");

        labSal.setText("Salario:");

        labSede.setText("Sede:");

        labFechaNac.setText("Fecha de nacimiento:");

        tNombreUsu.setToolTipText("");

        tCedula.setToolTipText("");

        tCorreo.setToolTipText("");

        tCuentaBan.setToolTipText("");

        tDir.setToolTipText("");

        labNombre.setText("Nombre:");
        labNombre.setToolTipText("");

        tNombre.setToolTipText("");

        comboxGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino" }));

        tTel.setToolTipText("");

        tSal.setToolTipText("");

        comboxSedes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sin sede" }));

        comboxDia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));

        comboxMes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic" }));

        comboxAno.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961", "1960", "1959", "1958", "1957", "1956", "1955", "1954", "1953", "1952", "1951", "1950", "1949", "1948", "1947", "1946", "1945", "1944" }));

        comboxCargo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No seleccionado", "Vendedor", "Jefe de taller" }));
        comboxCargo.setEnabled(false);

        bAceptar.setText("Agregar");
        bAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bAceptarMouseClicked(evt);
            }
        });
        bAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAceptarActionPerformed(evt);
            }
        });

        labEmple.setText("Empleado");

        comboxEmple.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No seleccionado" }));

        labContra.setText("Contraseña:");

        tContra.setToolTipText("");

        labSede1.setText("Id Gerente:");

        tIdGerente.setToolTipText("");

        labSede2.setText("Estado:");

        comboxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(labSede2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(comboxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labDir)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(labNombreUsu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(labCedula)
                                .addComponent(labCargo)
                                .addComponent(labCorreo)
                                .addComponent(labCuentaBan)
                                .addComponent(labGenero)
                                .addComponent(labNombre)
                                .addComponent(labTel)
                                .addComponent(labSal)
                                .addComponent(labSede)
                                .addComponent(labEmple, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(labContra))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tNombreUsu)
                            .addComponent(tCedula)
                            .addComponent(tCorreo)
                            .addComponent(tCuentaBan)
                            .addComponent(comboxGenero, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tSal)
                            .addComponent(comboxSedes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboxCargo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(comboxEmple, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tContra)
                            .addComponent(tNombre)
                            .addComponent(tDir)
                            .addComponent(tTel, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bAceptar)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(labFechaNac)
                                .addGap(19, 19, 19)
                                .addComponent(comboxDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(comboxMes, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboxAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(labSede1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tIdGerente, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(87, 87, 87))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labEmple)
                    .addComponent(comboxEmple, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labNombreUsu)
                    .addComponent(tNombreUsu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labContra)
                    .addComponent(tContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labNombre)
                    .addComponent(tNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labCedula)
                    .addComponent(tCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labCargo)
                    .addComponent(comboxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labCorreo)
                    .addComponent(tCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tCuentaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labCuentaBan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labGenero)
                    .addComponent(comboxGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labDir)
                    .addComponent(tDir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labTel)
                    .addComponent(tTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labSal, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tSal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labSede)
                    .addComponent(comboxSedes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labSede1)
                    .addComponent(tIdGerente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labSede2)
                    .addComponent(comboxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labFechaNac)
                    .addComponent(comboxDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboxMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboxAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(bAceptar)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(jPanel4);

        jPanel1.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        fechaYhora.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        fechaYhora.setText("Fecha  y hora:");

        fecha.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        fecha.setText("dd-mm-yyyy");

        hora.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        hora.setText("hh:mm:ss");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(fechaYhora)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hora)
                .addGap(0, 386, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fecha)
                    .addComponent(fechaYhora)
                    .addComponent(hora))
                .addGap(0, 1, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 539, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usuariosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usuariosMouseReleased
        if(this.usuarios.isSelected()){
            this.sede.setSelected(false);
        }
    }//GEN-LAST:event_usuariosMouseReleased

    private void sedeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sedeMouseReleased
        if(this.sede.isSelected()){
            this.usuarios.setSelected(false);
        }
    }//GEN-LAST:event_sedeMouseReleased

    private void usuarioMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usuarioMouseReleased
        if(this.usuario.isSelected()){
            this.sedes.setSelected(false);
            this.ventas.setSelected(false);
        }
    }//GEN-LAST:event_usuarioMouseReleased

    private void sedesMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sedesMouseReleased
        if(this.sedes.isSelected()){
            this.usuario.setSelected(false);
            this.ventas.setSelected(false);
        }
    }//GEN-LAST:event_sedesMouseReleased

    private void ventasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ventasMouseReleased
        if(this.ventas.isSelected()){
            this.usuario.setSelected(false);
            this.sedes.setSelected(false);
        }
    }//GEN-LAST:event_ventasMouseReleased

    private void bAgregarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAgregarMouseReleased
       //muestra las opciones (sede o usuarios)
        if(evt.isPopupTrigger()){
         users.show(evt.getComponent(),evt.getX(),evt.getY());
        } 
              
        if(this.usuarios.isSelected()){
            cambiarVisibilidadCamposmodf(false);
            cambiarVisibilidadCampos(true);
        
            botonAceptar = 1;
            bAceptar.setText("Agregar");
            bAceptar.setVisible(true);
            bAceptar.setEnabled(true);
        }
        
        if(this.sede.isSelected()){
            cambiarVisibilidadCamposmodf(false);
            cambiarVisibilidadCampos(true);
            cambiarVisibilidadCamposSede(false);
        
            botonAceptar = 1;
            bAceptar.setText("Agregar");
            bAceptar.setVisible(true);
            bAceptar.setEnabled(true);
        }
        
    }//GEN-LAST:event_bAgregarMouseReleased

    private void bModfMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bModfMouseReleased
       //muestra las opciones (sede o usuarios)
        if(evt.isPopupTrigger()){
         users.show(evt.getComponent(),evt.getX(),evt.getY());
        } 
        
        if(this.usuarios.isSelected()){
            cambiarVisibilidadCampos(true);
            cambiarVisibilidadCamposmodf(true);
        
            botonAceptar = 2;
            bAceptar.setText("Modificar");
            bAceptar.setVisible(true);              
            bAceptar.setEnabled(false);
        
            comboxEmple.setSelectedIndex(0);
        }
        
        if(this.sede.isSelected()){
            cambiarVisibilidadCamposmodf(false);
            cambiarVisibilidadCampos(true);
            cambiarVisibilidadCamposSede(false);
            cambiarVisibilidadCamposSedeModf(false);
        
            botonAceptar = 2;
            bAceptar.setText("Modificar");
            bAceptar.setVisible(true);
            bAceptar.setEnabled(false);
        }
        
    }//GEN-LAST:event_bModfMouseReleased

    private void bConsulMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bConsulMouseReleased
        //muestra las opciones (sede o usuarios)
        if(evt.isPopupTrigger()){
         users.show(evt.getComponent(),evt.getX(),evt.getY());
        }
        
        if(this.usuarios.isSelected()){
            cambiarVisibilidadCamposmodf(false);
            cambiarVisibilidadCampos(false);
        
            botonAceptar = 3;
            bAceptar.setText("Consultar");
            bAceptar.setVisible(true);
            bAceptar.setEnabled(false);
        
            comboxEmple.setSelectedIndex(0);
        }
        
        if(this.sede.isSelected()){
            cambiarVisibilidadCamposmodf(false);
            cambiarVisibilidadCampos(false);
            cambiarVisibilidadCamposSedeModf(false);
            
            botonAceptar = 3;
            bAceptar.setText("Consultar");
            bAceptar.setVisible(true);
            bAceptar.setEnabled(false);
        }
        
    }//GEN-LAST:event_bConsulMouseReleased

    private void bDespedirMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bDespedirMouseReleased
       //muestra las opciones (sede o usuarios)
        if(evt.isPopupTrigger()){
         users.show(evt.getComponent(),evt.getX(),evt.getY());
        }
        
    if(this.usuarios.isSelected()){    
        cambiarVisibilidadCamposmodf(false);
        cambiarVisibilidadCampos(false);
        
        botonAceptar = 4;
        bAceptar.setText("Despedir");
        bAceptar.setVisible(true);
        bAceptar.setEnabled(false);
        
       comboxEmple.setSelectedIndex(0);
    }
    
    if(this.sede.isSelected()){
            cambiarVisibilidadCamposmodf(false);
            cambiarVisibilidadCampos(false);
            cambiarVisibilidadCamposSedeModf(false);
            
            botonAceptar = 4;
            bAceptar.setText("Deshabilitar");
            bAceptar.setVisible(true);
            bAceptar.setEnabled(false);
        }
    }//GEN-LAST:event_bDespedirMouseReleased

    private void bAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bAgregarActionPerformed

    private void bAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAceptarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bAceptarActionPerformed

    private void bAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bAceptarMouseClicked
        // TODO add your handling code here:

        
    }//GEN-LAST:event_bAceptarMouseClicked

    
    //Quita los campos usados en la función agregar, consultar y despedir (ej:nombre,cedula,genero,cargo)
    //varControl: true para indicar que sea visible los campos de agregar, false para los de consultar y despedir
    public void cambiarVisibilidadCampos(boolean varControl){
        labContra.setVisible(false);
        tContra.setVisible(false);
        
        labEmple.setVisible(!varControl);
        comboxEmple.setVisible(!varControl);
        
        labEmple.setText("Empleado:");
        labNombreUsu.setVisible(varControl);
        labNombre.setText("Nombre:");
        labNombre.setVisible(varControl);
        labCedula.setVisible(varControl);
        labCargo.setVisible(varControl);
        labCorreo.setVisible(varControl);
        labCuentaBan.setVisible(varControl);
        labGenero.setVisible(varControl);
        labDir.setVisible(varControl);
        labTel.setVisible(varControl);
        labSal.setVisible(varControl);
        labSede.setVisible(varControl);
        labFechaNac.setText("Fecha de nacimiento:");
        labFechaNac.setVisible(varControl);
        
        comboxCargo.setVisible(varControl);
        comboxCargo.setSelectedIndex(0);
        comboxGenero.setVisible(varControl);
        comboxGenero.setSelectedIndex(0);
        comboxSedes.setVisible(varControl);
        comboxSedes.setSelectedIndex(0);
        comboxDia.setVisible(varControl);
        comboxDia.setSelectedIndex(0);
        comboxMes.setVisible(varControl);
        comboxMes.setSelectedIndex(0);
        comboxAno.setVisible(varControl);
        comboxAno.setSelectedIndex(0);
        
        tNombreUsu.setVisible(varControl);
        tNombreUsu.setText("");
        tNombre.setVisible(varControl);
        tNombre.setText("");
        tCedula.setVisible(varControl);
        tCedula.setText("");
        tCorreo.setVisible(varControl);
        tCorreo.setText("");
        tCuentaBan.setVisible(varControl);
        tCuentaBan.setText("");
        tDir.setVisible(varControl);
        tDir.setText("");
        tTel.setVisible(varControl);
        tTel.setText("");
        tSal.setVisible(varControl);
        tSal.setText("");
    }
    
    //Quita los campos usados en la función consultar y despedir (ej:empleado)
    //varControl: true para indicar que sea visible, false para lo opuesto
    public void cambiarVisibilidadCamposmodf(boolean varControl){           
        labEmple.setVisible(varControl);
        comboxEmple.setVisible(varControl);
        
        labEmple.setText("Empleado:");
        comboxCargo.setEnabled(!varControl);
        comboxGenero.setEnabled(!varControl);
        comboxSedes.setEnabled(!varControl);
        comboxDia.setEnabled(!varControl);
        comboxMes.setEnabled(!varControl);
        comboxAno.setEnabled(!varControl);      
        
        labContra.setVisible(varControl);
        tContra.setVisible(varControl);
        tContra.setEnabled(!varControl);
        tContra.setText("");
        
        tNombreUsu.setEnabled(!varControl);
        tNombreUsu.setText("");
        tNombre.setEnabled(!varControl);
        tNombre.setText("");
        tCedula.setEnabled(!varControl);
        tCedula.setText("");
        tCorreo.setEnabled(!varControl);
        tCorreo.setText("");
        tCuentaBan.setEnabled(!varControl);
        tCuentaBan.setText("");
        tDir.setEnabled(!varControl);
        tDir.setText("");
        tTel.setEnabled(!varControl);
        tTel.setText("");
        tSal.setEnabled(!varControl);
        tSal.setText("");
    }
    
    public void cambiarVisibilidadCamposSede(boolean varControl){
        
        labNombre.setText("Nombre de sede:");
        labFechaNac.setText("Fecha de creación:");
        
        labContra.setVisible(false);
        tContra.setVisible(false);
        
        labEmple.setVisible(varControl);
        comboxEmple.setVisible(varControl);
        
        labNombreUsu.setVisible(varControl);
        labCedula.setVisible(varControl);
        labCargo.setVisible(varControl);
        labCorreo.setVisible(varControl);
        labCuentaBan.setVisible(varControl);
        labGenero.setVisible(varControl);
        labSal.setVisible(varControl);
        labSede.setVisible(varControl);
        
        comboxCargo.setVisible(varControl);
        comboxCargo.setSelectedIndex(0);
        comboxGenero.setVisible(varControl);
        comboxGenero.setSelectedIndex(0);
        comboxSedes.setVisible(varControl);
        comboxSedes.setSelectedIndex(0);
        
        tNombreUsu.setVisible(varControl);
        tNombreUsu.setText("");
        tCedula.setVisible(varControl);
        tCedula.setText("");
        tCorreo.setVisible(varControl);
        tCorreo.setText("");
        tCuentaBan.setVisible(varControl);
        tCuentaBan.setText("");
        tSal.setVisible(varControl);
        tSal.setText("");
  
    }
    
    public void cambiarVisibilidadCamposSedeModf(boolean varControl){
        
        labNombre.setText("Nombre de sede:");
        labFechaNac.setText("Fecha de creación:");
        labEmple.setText("Sede:");
        labEmple.setVisible(!varControl);
        comboxEmple.setVisible(!varControl);
        
        labContra.setVisible(false);
        tContra.setVisible(false);
        
        labNombreUsu.setVisible(varControl);
        labCedula.setVisible(varControl);
        labCargo.setVisible(varControl);
        labCorreo.setVisible(varControl);
        labCuentaBan.setVisible(varControl);
        labGenero.setVisible(varControl);
        labSal.setVisible(varControl);
        labSede.setVisible(varControl);
        
        comboxCargo.setVisible(varControl);
        comboxCargo.setSelectedIndex(0);
        comboxGenero.setVisible(varControl);
        comboxGenero.setSelectedIndex(0);
        comboxSedes.setVisible(varControl);
        comboxSedes.setSelectedIndex(0);
       
        comboxDia.setEnabled(varControl);
        comboxMes.setEnabled(varControl);
        comboxAno.setEnabled(varControl);
        tNombre.setEnabled(varControl);
        tDir.setEnabled(varControl);
        tTel.setEnabled(varControl);
        
        tNombreUsu.setVisible(varControl);
        tNombreUsu.setText("");
        tCedula.setVisible(varControl);
        tCedula.setText("");
        tCorreo.setVisible(varControl);
        tCorreo.setText("");
        tCuentaBan.setVisible(varControl);
        tCuentaBan.setText("");
        tSal.setVisible(varControl);
        tSal.setText("");
  
    }
    
    public static void main(String args[]) {            
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                 
               // new prinGerente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAceptar;
    private javax.swing.JButton bAgregar;
    private javax.swing.JButton bConsul;
    private javax.swing.JButton bDespedir;
    private javax.swing.JButton bModf;
    private javax.swing.JComboBox<String> comboxAno;
    private javax.swing.JComboBox<String> comboxCargo;
    private javax.swing.JComboBox<String> comboxDia;
    private javax.swing.JComboBox<String> comboxEmple;
    private javax.swing.JComboBox<String> comboxEstado;
    private javax.swing.JComboBox<String> comboxGenero;
    private javax.swing.JComboBox<String> comboxMes;
    private javax.swing.JComboBox<String> comboxSedes;
    private javax.swing.JLabel fecha;
    private javax.swing.JLabel fechaYhora;
    private javax.swing.JLabel hora;
    private javax.swing.JLabel iconUsu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JLabel labCargo;
    private javax.swing.JLabel labCedula;
    private javax.swing.JLabel labContra;
    private javax.swing.JLabel labCorreo;
    private javax.swing.JLabel labCuentaBan;
    private javax.swing.JLabel labDir;
    private javax.swing.JLabel labEmple;
    private javax.swing.JLabel labFechaNac;
    private javax.swing.JLabel labGenero;
    private javax.swing.JLabel labLogo;
    private javax.swing.JLabel labNombre;
    private javax.swing.JLabel labNombreUsu;
    private javax.swing.JLabel labSal;
    private javax.swing.JLabel labSede;
    private javax.swing.JLabel labSede1;
    private javax.swing.JLabel labSede2;
    private javax.swing.JLabel labTel;
    private javax.swing.JPopupMenu opReporte;
    private javax.swing.JCheckBoxMenuItem sede;
    private javax.swing.JCheckBoxMenuItem sedes;
    private javax.swing.JTextField tCedula;
    private javax.swing.JTextField tContra;
    private javax.swing.JTextField tCorreo;
    private javax.swing.JTextField tCuentaBan;
    private javax.swing.JTextField tDir;
    private javax.swing.JTextField tIdGerente;
    private javax.swing.JTextField tNombre;
    private javax.swing.JTextField tNombreUsu;
    private javax.swing.JTextField tSal;
    private javax.swing.JTextField tTel;
    private javax.swing.JPopupMenu users;
    private javax.swing.JCheckBoxMenuItem usuario;
    private javax.swing.JCheckBoxMenuItem usuarios;
    private javax.swing.JCheckBoxMenuItem ventas;
    // End of variables declaration//GEN-END:variables
}
