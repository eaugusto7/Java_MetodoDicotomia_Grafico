package gui_movimento_u_variado;

import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class RadioButton extends JFrame {

    private JRadioButton arco, arma_pistola, canhao, foguete;
    private JRadioButton ang15, ang30, ang45, ang60, ang75, ang90;

    private JLabel myLabel, myLabel2, myLabel3;

    ImageIcon icon = new ImageIcon("bow.png");
    ImageIcon icon2 = new ImageIcon("gun.png");
    ImageIcon icon3 = new ImageIcon("cannon.png");
    ImageIcon icon4 = new ImageIcon("rocket.png");

    private ButtonGroup grupo1, grupo2;
    private RadioButtonHandler handler;

    public RadioButton() {
        super("Busca Reunião");

        setLayout(new FlowLayout());
        handler = new RadioButtonHandler();

        myLabel = new JLabel("");
        arco = new JRadioButton("Arco", false);
  
        add(myLabel);
        add(arco);
        grupo1 = new ButtonGroup();
        grupo1.add(arco);
        arco.addItemListener(handler);
    }

    //Classe Estática do Metodo da Dicotomia
    public static double dicotomia(double xa, double xb, double precisao, double v_inicial,
            double tempo_total, String nome_grafico) throws FileNotFoundException, IOException {
        //Recebe throws pois trabalha com abertura de arquivos
        nome_grafico += ".png";

        double xc, anterior;
        double erro_relativo = 1.0;
        int i = 0;

        anterior = xb;
        xc = xa;
        System.out.println("Tabela: ");

        //Instancia o gráfico
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(xc, "Valor", "" + i);

        //Repetições da dicotomia - ainda não interage de acordo com as repetições
        while (erro_relativo > precisao) {
            erro_relativo = Math.abs(xc - anterior);
            System.out.print("Xa: " + xa);
            System.out.print("\tXb: " + xb);
            System.out.print("\tXc: " + xc);
            System.out.print("\tPrec: " + erro_relativo);
            System.out.println("");

            anterior = xc;
            xc = (xa + xb) / 2.0;

            if (Equacao(xa, v_inicial, tempo_total, 9.8) * Equacao(xc, v_inicial, tempo_total, 9.8) < 0.0) {
                xb = xc;
            } else if (Equacao(xb, v_inicial, tempo_total, 9.8) * Equacao(xc, v_inicial, tempo_total, 9.8) < 0.0) {
                xa = xc;
            }
            //Adiciona valores para colocar nos gráficos
            ds.addValue(xc, "Valor", "" + i);
            i++;
        }

        //Deveria estar exigindo na tela
        JFreeChart grafico = ChartFactory.createLineChart("Gráfico de Dicotomia", "Numero de iterações",
                "Altura", ds, PlotOrientation.VERTICAL, true, true, false);

        //Grava o grafico em uma imagem na pasta do projeto
        OutputStream arquivo = new FileOutputStream(nome_grafico);
        ChartUtilities.writeChartAsPNG(arquivo, grafico, 800, 600); //Dimensões 550x400
        arquivo.close(); //Fecha a gravação do arquivo

        return xc;
    }

    private class RadioButtonHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent event) {
            if (arco.isSelected()) {
                
            }
        }
    }
}
