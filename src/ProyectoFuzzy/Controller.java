package ProyectoFuzzy;
import Models.Dificultad;
import Models.Estado;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import javafx.concurrent.Task;
public class Controller {
    public Label tiempo;
    public Label dificultad;
    public Button btnAyuda;
    public Button btnSiguiente;
    public Button flogic;

    public TextField r0,r1,r2,r3,r4,r5,r6;
    public TextField t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24;
    public ImageView correctImage;
    public Label simPlus;
    public Pane barraSuma;
    public Label labelAyudas;
    public Label emocionId;
    private int errores = 0;
    private int ayudas = 0;
    private int time = 0;
    private long horaInicio = 0;
    private long horaFin = 0;
    private Dificultad dif = Dificultad.FACIL;
    private Estado estado = Estado.INICIAL;
    public Label labelErrores;
    public static FunctionBlock fb;
    Thread t;


    Task task = new Task<Void>() {
        @Override
        public Void call() throws Exception {
            while (true) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        emocionId.setText(leeDatos().toUpperCase());
                    }
                });
                Thread.sleep(1000);
            }
        }
    };


    public void generarEjercicio() {
        t = new Thread(task);
        t.setDaemon(true);
        t.start();
        switch (estado){
            case INICIAL:
                estado = Estado.CURSO;
                btnAyuda.setDisable(false);
                btnSiguiente.setText("TERMINAR");
                ajustarComponentes();
                horaInicio = System.currentTimeMillis();
                break;
            case CURSO:
                Alert alert = new Alert(Alert.AlertType.NONE, "La respuesta es incorrecta, sigue intentando", ButtonType.OK);
                if(!validarSuma()){
                    errores++;
                    labelErrores.setText(String.valueOf(errores));
                    alert.show();
                }else{
                    horaFin = System.currentTimeMillis();
                    mostrarValoresFin();
                    estado = Estado.TERMINADO;

                }
                break;
            case TERMINADO:
                try {
                    obtenerDificultad();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                reinicializarComp();
                generarEjercicio();
        }
    }

    public static void iniciarFuzzyLogic(){
        String filename = "tipper.fcl";
        FIS fis = FIS.load(filename, true);

        if (fis == null) {
            System.err.println("Can't load file: '" + filename + "'");
            System.exit(1);
        }

        // Get default function block
        fb = fis.getFunctionBlock(null);
        System.out.println("Se inicio");
    }

    public static String leeDatos() {
        try {
            File myObj = new File("C:\\Users\\abely\\Desktop\\Maestria\\semestre 1\\Introducion IA\\Proyecto logica difusa\\src\\Reconicimiento-Emociones\\result.txt"); //Ruta al archivo donde se guarda la emoción
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (data.isEmpty()) {
                    break;
                }
                return data;

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Ocurrió un error.");
            e.printStackTrace();
        }
        return null;
    }


    public void obtenerDificultad() throws Exception{
        String emocion = leeDatos();
        double numeEmocion = 0;

        switch(emocion){
            case "aburrido":
                numeEmocion = 0.5;
                break;
            case "enganchado":
                numeEmocion = 1.5;
                break;
            case "excitado":
                numeEmocion = 2.5;
                break;
            case "concentrado":
                numeEmocion = 3.5;
                break;
            case "interesado":
                numeEmocion = 4.5;
                break;
            case "relajado":
                numeEmocion = 5.5;
        }

        // Set inputs
        fb.setVariable("ayudas", ayudas);
        fb.setVariable("tiempo", time);
        fb.setVariable("errores", errores);
        fb.setVariable("emocion", numeEmocion);
        // Evaluate
        fb.evaluate();

        // Show output variable's chart
        fb.getVariable("nota").defuzzify();
        System.out.println("La emocion es: "+leeDatos());
        double n = fb.getVariable("nota").getValue();
        boolean subirDif = false;
        if (n>=70)
            subirDif = true;

        if (subirDif){
            switch (dif){
                case FACIL:
                    dif = Dificultad.REGULAR;
                    break;
                case REGULAR:
                    dif = Dificultad.DIFICIL;
            }
        }
        System.out.println("La nota es: "+ n);
    }

    private void mostrarValoresFin() {
        r0.setVisible(false);
        r1.setVisible(false);
        r2.setVisible(false);
        r3.setVisible(false);
        r4.setVisible(false);
        r5.setVisible(false);
        r6.setVisible(false);

        t1.setVisible(false);
        t2.setVisible(false);
        t3.setVisible(false);
        t4.setVisible(false);
        t5.setVisible(false);
        t6.setVisible(false);
        t7.setVisible(false);
        t8.setVisible(false);
        t9.setVisible(false);
        t10.setVisible(false);
        t11.setVisible(false);
        t12.setVisible(false);
        t13.setVisible(false);
        t14.setVisible(false);
        t15.setVisible(false);
        t16.setVisible(false);
        t17.setVisible(false);
        t18.setVisible(false);
        t19.setVisible(false);
        t20.setVisible(false);
        t21.setVisible(false);
        t22.setVisible(false);
        t23.setVisible(false);
        t24.setVisible(false);

        btnSiguiente.setText("SIGUIENTE");
        btnAyuda.setVisible(false);
        barraSuma.setVisible(false);
        simPlus.setVisible(false);
        correctImage.setVisible(true);



        int t = (int)((horaFin - horaInicio)/1000);
        time = t;
        tiempo.setText(" "+t+" seg");


    }

    private boolean validarSuma(){
        int suma = 0;
        int res = -1;
        switch (dif){
            case FACIL:

                suma = Integer.parseInt(t10.getText() +  t9.getText()) + Integer.parseInt(t4.getText() +  t3.getText()) ;

                if (!(r2.getText().equals("")  || r3.getText().equals("")  || r4.getText().equals("")))
                    res = Integer.parseInt( r2.getText() + r3.getText() + r4.getText());
                else
                    if (!(r3.getText().equals("") || r4.getText().equals("")))
                        res = Integer.parseInt(r3.getText() + r4.getText());

                System.out.println("Suma: "+suma);
                System.out.println("Respuesta: "+res);

                break;
            case REGULAR:

                suma = Integer.parseInt(t11.getText() + t10.getText() +t9.getText() ) + Integer.parseInt(t5.getText() + t4.getText() + t3.getText());
                if (!(r1.getText().equals("")  || r2.getText().equals("")  || r3.getText().equals("") || r4.getText().equals("")))
                    res = Integer.parseInt( r1.getText() + r2.getText() + r3.getText() + r4.getText());
                else
                    if (!(r2.getText().equals("") || r3.getText().equals("") || r4.getText().equals("")))
                        res = Integer.parseInt(r2.getText()+r3.getText() + r4.getText());

                System.out.println("Suma: "+suma);
                System.out.println("Respuesta: "+res);

                break;
            case DIFICIL:

                suma = Integer.parseInt(t6.getText() + t5.getText() + t4.getText()+ t3.getText()+ t2.getText()+ t1.getText())+
                        Integer.parseInt(t12.getText() + t11.getText() + t10.getText()+ t9.getText()+ t8.getText()+ t7.getText())+
                        Integer.parseInt(t18.getText() + t17.getText() +t16.getText() +t15.getText()+t14.getText()+t13.getText());
                if (!(r0.getText().equals("") || r1.getText().equals("")  || r2.getText().equals("")  || r3.getText().equals("") || r4.getText().equals("")|| r5.getText().equals("") || r6.getText().equals("")))
                    res = Integer.parseInt( r0.getText()+r1.getText() + r2.getText() + r3.getText() + r4.getText()+ r5.getText()+ r6.getText());
                else
                    if (!( r1.getText().equals("") || r2.getText().equals("") || r3.getText().equals("") || r4.getText().equals("") || r5.getText().equals("") || r6.getText().equals("")))
                        res = Integer.parseInt(r1.getText()+r2.getText()+r3.getText() + r4.getText()+r5.getText()+r6.getText());

                System.out.println("Suma: "+suma);
                System.out.println("Respuesta: "+res);



        }
        return suma == res && verificarCarry();
    }

    private String numero(){
        Random rn = new Random();
        return String.valueOf(rn.nextInt(10));
    }


    public void maximoChar(KeyEvent keyEvent) {
        TextField tex = (TextField) keyEvent.getSource();
        String num = tex.getText();

        if (num.length() == 1 || keyEvent.getCharacter().matches("\\D")  )
            keyEvent.consume();

    }

    private void ajustarComponentes(){
        btnAyuda.setVisible(true);
        switch (dif){
            case FACIL:
                dificultad.setText("  FACIL");
                r2.setVisible(true);
                r3.setVisible(true);
                r4.setVisible(true);
                t3.setVisible(true);
                t3.setEditable(false);
                t3.setText(numero());
                t4.setVisible(true);
                t4.setEditable(false);
                t4.setText(numero());

                t9.setVisible(true);
                t9.setEditable(false);
                t9.setText(numero());
                t10.setVisible(true);
                t10.setEditable(false);
                t10.setText(numero());

                t16.setVisible(true);

                break;
            case REGULAR:
                dificultad.setText("  REGULAR");
                r1.setVisible(true);
                r2.setVisible(true);
                r3.setVisible(true);
                r4.setVisible(true);

                t3.setVisible(true);
                t3.setEditable(false);
                t3.setText(numero());
                t4.setVisible(true);
                t4.setEditable(false);
                t4.setText(numero());
                t5.setVisible(true);
                t5.setEditable(false);
                t5.setText(numero());

                t9.setVisible(true);
                t9.setEditable(false);
                t9.setText(numero());
                t10.setVisible(true);
                t10.setEditable(false);
                t10.setText(numero());
                t11.setVisible(true);
                t11.setEditable(false);
                t11.setText(numero());

                t16.setVisible(true);
                t17.setVisible(true);
                break;
            case DIFICIL:
                dificultad.setText("  DIFICIL");
                r0.setVisible(true);
                r1.setVisible(true);
                r2.setVisible(true);
                r3.setVisible(true);
                r4.setVisible(true);
                r5.setVisible(true);
                r6.setVisible(true);

                t1.setVisible(true);
                t1.setEditable(false);
                t1.setText(numero());
                t2.setVisible(true);
                t2.setEditable(false);
                t2.setText(numero());
                t3.setVisible(true);
                t3.setEditable(false);
                t3.setText(numero());
                t4.setVisible(true);
                t4.setEditable(false);
                t4.setText(numero());
                t5.setVisible(true);
                t5.setEditable(false);
                t5.setText(numero());
                t6.setVisible(true);
                t6.setEditable(false);
                t6.setText(numero());
                t7.setVisible(true);
                t7.setEditable(false);
                t7.setText(numero());
                t8.setVisible(true);
                t8.setEditable(false);
                t8.setText(numero());
                t9.setVisible(true);
                t9.setEditable(false);
                t9.setText(numero());
                t10.setVisible(true);
                t10.setEditable(false);
                t10.setText(numero());
                t11.setVisible(true);
                t11.setEditable(false);
                t11.setText(numero());
                t12.setVisible(true);
                t12.setEditable(false);
                t12.setText(numero());
                t13.setVisible(true);
                t13.setEditable(false);
                t13.setText(numero());
                t14.setVisible(true);
                t14.setEditable(false);
                t14.setText(numero());
                t15.setVisible(true);
                t15.setEditable(false);
                t15.setText(numero());
                t16.setVisible(true);
                t16.setEditable(false);
                t16.setText(numero());
                t17.setVisible(true);
                t17.setEditable(false);
                t17.setText(numero());
                t18.setVisible(true);
                t18.setEditable(false);
                t18.setText(numero());

                t20.setVisible(true);
                t21.setVisible(true);
                t22.setVisible(true);
                t23.setVisible(true);
                t24.setVisible(true);

        }
    }


    public void obtenerAyuda(ActionEvent actionEvent) {
        ayudas++;
        labelAyudas.setText(String.valueOf(ayudas));
        int num = 0;
        switch (dif){
            case FACIL:
                num = Integer.parseInt(t9.getText())+ Integer.parseInt(t3.getText());
                if (indicarErrorResp(r4,num))
                    break;
                if (indicarErrorCarry(t16,num))
                    break;
                if (t16.getText().equals(""))
                    num = Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());
                else
                    num = Integer.parseInt(t16.getText())+ Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());

                if (indicarErrorResp(r3,num))
                    break;
                if (num > 9) {
                    num =Integer.parseInt( String.valueOf(num).substring(0,1));
                    if (indicarErrorResp(r2,num))
                        break;
                }

                break;
            case REGULAR:

                num = Integer.parseInt(t9.getText())+ Integer.parseInt(t3.getText());
                if (indicarErrorResp(r4,num))
                    break;
                if (indicarErrorCarry(t16,num))
                    break;

                if (t16.getText().equals(""))
                    num = Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());
                else
                    num = Integer.parseInt(t16.getText())+ Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());

                if (indicarErrorResp(r3,num))
                    break;

                if (indicarErrorCarry(t17,num))
                    break;

                if (t17.getText().equals(""))
                    num = Integer.parseInt(t11.getText())+ Integer.parseInt(t5.getText());
                else
                    num = Integer.parseInt(t17.getText())+ Integer.parseInt(t11.getText())+ Integer.parseInt(t5.getText());

                if (indicarErrorResp(r2,num))
                    break;

                if (num > 9) {
                    num =Integer.parseInt( String.valueOf(num).substring(0,1));
                    if (indicarErrorResp(r1,num))
                        break;
                }
                break;
            case DIFICIL:
                num = Integer.parseInt(t13.getText())+Integer.parseInt(t7.getText())+ Integer.parseInt(t1.getText());
                if (indicarErrorResp(r6,num))
                    break;
                if (indicarErrorCarry(t20,num))
                    break;

                if (t20.getText().equals(""))
                    num = Integer.parseInt(t14.getText()) + Integer.parseInt(t8.getText())+ Integer.parseInt(t2.getText());
                else
                    num = Integer.parseInt(t20.getText()) + Integer.parseInt(t14.getText()) + Integer.parseInt(t8.getText())+ Integer.parseInt(t2.getText());

                if (indicarErrorResp(r5,num))
                    break;
                if (indicarErrorCarry(t21,num))
                    break;

                if (t21.getText().equals(""))
                    num = Integer.parseInt(t15.getText())+Integer.parseInt(t9.getText())+ Integer.parseInt(t3.getText());
                else
                    num = Integer.parseInt(t21.getText()) + Integer.parseInt(t15.getText())+Integer.parseInt(t9.getText())+ Integer.parseInt(t3.getText());

                if (indicarErrorResp(r4,num))
                    break;
                if (indicarErrorCarry(t22,num))
                    break;

                if (t22.getText().equals(""))
                    num = Integer.parseInt(t16.getText())+Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());
                else
                    num =Integer.parseInt(t22.getText()) + Integer.parseInt(t16.getText())+Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());

                if (indicarErrorResp(r3,num))
                    break;
                if (indicarErrorCarry(t23,num))
                    break;

                if (t23.getText().equals(""))
                    num = Integer.parseInt(t17.getText())+Integer.parseInt(t11.getText())+ Integer.parseInt(t5.getText());
                else
                    num =Integer.parseInt(t23.getText())+ Integer.parseInt(t17.getText())+Integer.parseInt(t11.getText())+ Integer.parseInt(t5.getText());


                if (indicarErrorResp(r2,num))
                    break;
                if (indicarErrorCarry(t24,num))
                    break;

                if (t24.getText().equals(""))
                    num = Integer.parseInt(t18.getText())+Integer.parseInt(t12.getText())+ Integer.parseInt(t6.getText());
                else
                    num =Integer.parseInt(t24.getText())+Integer.parseInt(t18.getText())+Integer.parseInt(t12.getText())+ Integer.parseInt(t6.getText());

                if (indicarErrorResp(r1,num))
                    break;
                if (num > 9) {
                    num =Integer.parseInt( String.valueOf(num).substring(0,1));
                    if (indicarErrorResp(r0,num))
                        break;
                }

                break;
        }
    }

    public boolean verificarCarry( ) {
        int num = 0;
        switch (dif){
            case FACIL:
                num = Integer.parseInt(t9.getText())+ Integer.parseInt(t3.getText());
                if (indicarErrorResp(r4,num))
                    return false;
                if (indicarErrorCarryTerm(t16,num))
                    return false;
                if (t16.getText().equals(""))
                    num = Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());
                else
                    num = Integer.parseInt(t16.getText())+ Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());

                if (indicarErrorResp(r3,num))
                    return false;
                if (num > 9) {
                    num =Integer.parseInt( String.valueOf(num).substring(0,1));
                    if (indicarErrorResp(r2,num))
                        return false;
                }

                break;
            case REGULAR:

                num = Integer.parseInt(t9.getText())+ Integer.parseInt(t3.getText());
                if (indicarErrorResp(r4,num))
                    return false;
                if (indicarErrorCarryTerm(t16,num))
                    return false;

                if (t16.getText().equals(""))
                    num = Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());
                else
                    num = Integer.parseInt(t16.getText())+ Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());

                if (indicarErrorResp(r3,num))
                    return false;

                if (indicarErrorCarryTerm(t17,num))
                    return false;

                if (t17.getText().equals(""))
                    num = Integer.parseInt(t11.getText())+ Integer.parseInt(t5.getText());
                else
                    num = Integer.parseInt(t17.getText())+ Integer.parseInt(t11.getText())+ Integer.parseInt(t5.getText());

                if (indicarErrorResp(r2,num))
                    return false;

                if (num > 9) {
                    num =Integer.parseInt( String.valueOf(num).substring(0,1));
                    if (indicarErrorResp(r1,num))
                        return false;
                }
                break;
            case DIFICIL:
                num = Integer.parseInt(t13.getText())+Integer.parseInt(t7.getText())+ Integer.parseInt(t1.getText());
                if (indicarErrorResp(r6,num))
                    return false;
                if (indicarErrorCarryTerm(t20,num))
                    return false;

                if (t20.getText().equals(""))
                    num = Integer.parseInt(t14.getText()) + Integer.parseInt(t8.getText())+ Integer.parseInt(t2.getText());
                else
                    num = Integer.parseInt(t20.getText()) + Integer.parseInt(t14.getText()) + Integer.parseInt(t8.getText())+ Integer.parseInt(t2.getText());

                if (indicarErrorResp(r5,num))
                    return false;
                if (indicarErrorCarryTerm(t21,num))
                    return false;

                if (t21.getText().equals(""))
                    num = Integer.parseInt(t15.getText())+Integer.parseInt(t9.getText())+ Integer.parseInt(t3.getText());
                else
                    num = Integer.parseInt(t21.getText()) + Integer.parseInt(t15.getText())+Integer.parseInt(t9.getText())+ Integer.parseInt(t3.getText());

                if (indicarErrorResp(r4,num))
                    return false;
                if (indicarErrorCarryTerm(t22,num))
                    return false;

                if (t22.getText().equals(""))
                    num = Integer.parseInt(t16.getText())+Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());
                else
                    num =Integer.parseInt(t22.getText()) + Integer.parseInt(t16.getText())+Integer.parseInt(t10.getText())+ Integer.parseInt(t4.getText());

                if (indicarErrorResp(r3,num))
                    return false;
                if (indicarErrorCarryTerm(t23,num))
                    return false;

                if (t23.getText().equals(""))
                    num = Integer.parseInt(t17.getText())+Integer.parseInt(t11.getText())+ Integer.parseInt(t5.getText());
                else
                    num =Integer.parseInt(t23.getText())+ Integer.parseInt(t17.getText())+Integer.parseInt(t11.getText())+ Integer.parseInt(t5.getText());


                if (indicarErrorResp(r2,num))
                    return false;
                if (indicarErrorCarryTerm(t24,num))
                    return false;

                if (t24.getText().equals(""))
                    num = Integer.parseInt(t18.getText())+Integer.parseInt(t12.getText())+ Integer.parseInt(t6.getText());
                else
                    num =Integer.parseInt(t24.getText())+Integer.parseInt(t18.getText())+Integer.parseInt(t12.getText())+ Integer.parseInt(t6.getText());

                if (indicarErrorResp(r1,num))
                    return false;
                if (num > 9) {
                    num =Integer.parseInt( String.valueOf(num).substring(0,1));
                    if (indicarErrorResp(r0,num))
                        return false;
                }

                break;
        }
        return true;
    }

    public boolean indicarErrorCarryTerm(TextField t, int num){
        if (num > 9){
            if (!t.getText().equals("")){
                if (String.valueOf(num).charAt(0) != t.getText().charAt(0)){
                    return true;
                }
            } else{
                return true;
            }

        }else
        if (!t.getText().equals("")){
            return true;
        }
        return false;
    }
    

    public boolean indicarErrorCarry(TextField t, int num){
        if (num > 9){
            if (!t.getText().equals("")){
                if (String.valueOf(num).charAt(0) != t.getText().charAt(0)){
                    t.setStyle("-fx-border-color: red");
                    return true;
                }
            } else{
                t.setStyle("-fx-border-color: red");
                return true;
            }

        }else
            if (!t.getText().equals("")){
                t.setStyle("-fx-border-color: red");
                return true;
            }
        return false;
    }

    public boolean indicarErrorResp(TextField t, int num){
        if (t.getText().equals("")){
            t.setStyle("-fx-border-color: red");
            return true;
        }
        if (num>9){
            if (String.valueOf(num).charAt(1) != t.getText().charAt(0)){
                t.setStyle("-fx-border-color: red");
                return true;
            }
        }else{
            if (String.valueOf(num).charAt(0) != t.getText().charAt(0)){
                t.setStyle("-fx-border-color: red");
                return true;
            }
        }
        return false;
    }

    public void cambiarStyle(MouseEvent mouseEvent) {
        TextField textField = (TextField) mouseEvent.getSource();
        textField.setStyle("");
    }

    private void reinicializarComp(){
        estado = Estado.INICIAL;
        correctImage.setVisible(false);
        labelAyudas.setText("0");
        labelErrores.setText("0");
        tiempo.setText("");
        errores = 0;
        ayudas = 0;
        r0.clear();
        r1.clear();
        r2.clear();
        r3.clear();
        r4.clear();
        r5.clear();
        r6.clear();
        t1.clear();
        t2.clear();
        t3.clear();
        t4.clear();
        t5.clear();
        t6.clear();
        t7.clear();
        t8.clear();
        t9.clear();
        t10.clear();
        t11.clear();
        t12.clear();
        t13.clear();
        t14.clear();
        t15.clear();
        t16.clear();
        t17.clear();
        t18.clear();
        t19.clear();
        t20.clear();
        t21.clear();
        t22.clear();
        t23.clear();
        t24.clear();

        barraSuma.setVisible(true);
        simPlus.setVisible(true);
    }

    public void mostrarGraficas(ActionEvent actionEvent) {
        JFuzzyChart.get().chart(fb);
    }
}
