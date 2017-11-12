package Ejercicio13;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class VentanaPrincipal {

	// La ventana principal, en este caso, guarda todos los componentes:
	JFrame ventana;

	// Paneles:
	JPanel panelIzq;
	JPanel panelDer;
	JPanel panelRBotones;

	// Botón de cargar y nuevo
	JButton cargarB = new JButton("Cargar");
	JButton nuevoB = new JButton("Nuevo");

	// Radio Buttons:
	JRadioButton rbNegro;
	JRadioButton rbAzul;
	JRadioButton rbBlanco;
	JRadioButton rbVerde;

	// La label en la que pinto las imágenes:
	JLabel imagenGrande;
	
	File ultimaRuta=null;

	// Constructor, marca el tamaño y el cierre del frame
	public VentanaPrincipal() {
		ventana = new JFrame();
		ventana.setBounds(100, 50, 900, 700);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// Inicializa todos los componentes del frame
	public void inicializarComponentes() {

		// Definimos el layout:
		ventana.setLayout(new GridBagLayout());

		// COSAS DEL LADO IZQUIERDO
		panelIzq = new JPanel();
		panelIzq.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30),
				BorderFactory.createLineBorder(Color.BLACK)));
		panelIzq.setLayout(new GridLayout(1, 1));
		GridBagConstraints settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.weighty = 1;
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelIzq, settings);

		imagenGrande = new JLabel();
		imagenGrande.setHorizontalAlignment(SwingConstants.CENTER);
		panelIzq.add(imagenGrande);

		// COSAS DEL LADO DERECHO
		panelDer = new JPanel();
		panelDer.setLayout(new GridBagLayout());
		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.ipadx = 80;
		settings.insets = new Insets(0, 0, 0, 30); // Le he dado un poco de inset por la derecha para cuadrar la
													// pantalla.
		settings.fill = GridBagConstraints.BOTH;
		ventana.add(panelDer, settings);

		panelRBotones = new JPanel();
		panelRBotones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Colores",
				TitledBorder.CENTER, TitledBorder.ABOVE_TOP));
		panelRBotones.setLayout(new GridLayout(4, 1, 0, 30));

		// Los botones de Cargar y nuevo:
		cargarB = new JButton("Cargar");
		// El iconito de cargar:
		cargarB.setIcon(cargarIcono("imagenes\\load.png"));

		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.fill = GridBagConstraints.BOTH;
		panelDer.add(cargarB, settings);

		nuevoB = new JButton("Nuevo");
		// El iconito de nuevo:
		nuevoB.setIcon(cargarIcono("imagenes\\new.png"));
		nuevoB.setHorizontalTextPosition(SwingConstants.LEFT);

		settings = new GridBagConstraints();
		settings.gridx = 1;
		settings.gridy = 0;
		settings.weightx = 1;
		settings.fill = GridBagConstraints.BOTH;
		panelDer.add(nuevoB, settings);

		// Radio Buttons:
		ButtonGroup grupo = new ButtonGroup();
		rbNegro = new JRadioButton("Negro");
		rbAzul = new JRadioButton("Azul");
		rbBlanco = new JRadioButton("Blanco");
		rbVerde = new JRadioButton("Verde");
		grupo.add(rbNegro);
		grupo.add(rbAzul);
		grupo.add(rbBlanco);
		grupo.add(rbVerde);
		panelRBotones.add(rbNegro);
		panelRBotones.add(rbAzul);
		panelRBotones.add(rbBlanco);
		panelRBotones.add(rbVerde);

		settings = new GridBagConstraints();
		settings.gridx = 0;
		settings.gridy = 1;
		settings.gridwidth = 2;
		settings.weightx = 1;
		settings.fill = GridBagConstraints.HORIZONTAL;
		settings.insets = new Insets(70, 10, 0, 10);
		panelDer.add(panelRBotones, settings);

	}

	public void inicializarListeners() {
		cargarB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// Utilizo el file Chooser para determinar qué imagen cargar:

				JFileChooser chooser = new JFileChooser();
				if (ultimaRuta!=null) {
					chooser.setCurrentDirectory(ultimaRuta);
				}
				FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG Imagenes", "zip", "png");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(ventana);
				if (returnVal == JFileChooser.APPROVE_OPTION) { // SI HEMOS SELECIONADO UN FICHERO:
					ultimaRuta=chooser.getCurrentDirectory();

					// Obtengo el fichero
					File selectedFile = chooser.getSelectedFile();

					// Cargo la imagen:
					BufferedImage imagenBuffer = null;
					try {
						imagenBuffer = ImageIO.read(selectedFile);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					ImageIcon imagenIcon = new ImageIcon(imagenBuffer.getScaledInstance(imagenGrande.getWidth(),
							imagenGrande.getHeight(), Image.SCALE_SMOOTH));
					imagenGrande.setIcon(imagenIcon);
				}

			}
		});

	}

	// Método que me devuelve un image icon de tamaño 25x25 para una ruta de un
	// fichero dado.
	public ImageIcon cargarIcono(String ruta) {
		BufferedImage buffAux = null;
		try {
			buffAux = ImageIO.read(new File(ruta));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ImageIcon(buffAux.getScaledInstance(25, 25, Image.SCALE_SMOOTH));
	}

	public void inicializar() {
		// IMPORTANTE, PRIMERO HACEMOS LA VENTANA VISIBLE Y LUEGO INICIALIZAMOS LOS
		// COMPONENTES.
		ventana.setVisible(true);
		inicializarComponentes();
		inicializarListeners();
	}
}
