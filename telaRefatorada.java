import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class telaRefatorada extends JFrame {

    private static final String WIN_MESSAGE =
            "<html><b><font color='green'>Parabéns! Você ganhou!</font></b></html>";
    private static final String ERROR_MESSAGE =
            "<html><b><font color='red'>Opa! Movimento não permitido :(</font></b></html>";

    private JTextField inputDiscosField;
    private JComboBox<String> origemBox;
    private JComboBox<String> destinoBox;
    private TorreDeHanoiRefatorada torreDeHanoi;
    private JPanel torresPanel;
    private JLabel contadorLabel;
    private JLabel mensagemLabel;

    private int contadorMovimentos = 0;
    private int movimentosIdeais = 0;

    public telaRefatorada() {
        super("Sinfonia dos Aneis");
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
    }

    private void inicializarComponentes() {
        adicionarTitulo();
        adicionarEntradaDiscos();
        adicionarSeletoresDeMovimento();
        adicionarBotaoMover();
        inicializarLabelsAuxiliares();
    }

    private void adicionarTitulo() {
        JLabel titulo = criarLabel("A Sinfonia dos Aneis - Torre de Hanoi",
                20, 0, 800, 50, new Font("Arial", Font.BOLD, 36));
        add(titulo);
    }

    private void adicionarEntradaDiscos() {
        JLabel inputLabel = criarLabel("Insira a quantidade de discos:",
                20, 100, 250, 25);
        inputDiscosField = new JTextField();
        inputDiscosField.setBounds(20, 130, 150, 25);
        add(inputLabel);
        add(inputDiscosField);

        JButton iniciarButton = criarBotao("Iniciar", 20, 165, 100, 30, Color.GREEN);
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });
        add(iniciarButton);
    }

    private void adicionarSeletoresDeMovimento() {
        origemBox = criarComboBox("Escolha a haste de origem:", 20, 210);
        destinoBox = criarComboBox("Escolha a haste de destino:", 20, 260);
    }

    private void adicionarBotaoMover() {
        JButton moverButton = criarBotao("Mover disco",
                20, 310, 150, 30, Color.PINK);
        moverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moverDisco();
            }
        });
        add(moverButton);
    }

    private void inicializarLabelsAuxiliares() {
        contadorLabel = criarLabel("", 20, 350, 400, 50);
        mensagemLabel = criarLabel("", 20, 450, 400, 50);
        mensagemLabel.setVisible(false);

        add(contadorLabel);
        add(mensagemLabel);
    }

    private void iniciarJogo() {
        try {
            int numDeDiscos = Integer.parseInt(inputDiscosField.getText());
            torreDeHanoi = new TorreDeHanoiRefatorada(numDeDiscos);
            movimentosIdeais = (int) Math.pow(2, numDeDiscos) - 1;

            configurarTorresPanel();
            atualizarContador();
        } catch (NumberFormatException e) {
            exibirMensagem("<html><b><font color='red'>Digite um número válido!</font></b></html>");
        }
    }

    private void configurarTorresPanel() {
        if (torresPanel != null) {
            remove(torresPanel);
        }

        torresPanel = new JPanel(new GridLayout(1, 3));
        torresPanel.setBounds(350, 130, 450, 400);
        atualizarTorres();

        add(torresPanel);
        revalidate();
        repaint();
    }

    private void atualizarTorres() {
        torresPanel.removeAll();
        torresPanel.add(criarTorrePanel('A'));
        torresPanel.add(criarTorrePanel('B'));
        torresPanel.add(criarTorrePanel('C'));
    }

    private JPanel criarTorrePanel(char nomeHaste) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Haste " + nomeHaste);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);

        pilhaRefatorada torre = torreDeHanoi.getTorre(TorreDeHanoiRefatorada.TorreID.valueOf(String.valueOf(nomeHaste)));

        for (int i = torre.getTamanhoMaximo() - 1; i >= 0; i--) {
            discoRefatorado disco = torre.getDisco(i);
            if (disco != null) {
                JLabel discoLabel = new JLabel("[" + disco.getTamanho() + "]");
                discoLabel.setFont(new Font("Arial", Font.BOLD, 24));
                discoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(discoLabel);
            }
        }
        return panel;
    }

    private void moverDisco() {
        char origem = origemBox.getSelectedItem().toString().charAt(0);
        char destino = destinoBox.getSelectedItem().toString().charAt(0);

        TorreDeHanoiRefatorada.TorreID o =
                TorreDeHanoiRefatorada.TorreID.valueOf(String.valueOf(origem));

        TorreDeHanoiRefatorada.TorreID d =
                TorreDeHanoiRefatorada.TorreID.valueOf(String.valueOf(destino));

        Integer movimento = torreDeHanoi.moverDisco(o, d);

        if (movimento != null) {
            contadorMovimentos = movimento;
            atualizarContador();
            atualizarTorres();
            torresPanel.revalidate();
            torresPanel.repaint();

            if (torreDeHanoi.isVitoria()) {
                exibirMensagem(WIN_MESSAGE);
            }
        } else {
            exibirMensagemTemporal(ERROR_MESSAGE);
        }
    }

    private void atualizarContador() {
        contadorLabel.setText("Movimentos: " + contadorMovimentos +
                " / Movimentos ideais: " + movimentosIdeais);
    }

    private void exibirMensagem(String mensagem) {
        mensagemLabel.setText(mensagem);
        mensagemLabel.setVisible(true);
    }

    private void exibirMensagemTemporal(final String mensagem) {
        exibirMensagem(mensagem);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mensagemLabel.setVisible(false);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private JLabel criarLabel(String texto, int x, int y, int largura, int altura, Font fonte) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, largura, altura);
        label.setFont(fonte);
        return label;
    }

    private JLabel criarLabel(String texto, int x, int y, int largura, int altura) {
        return criarLabel(texto, x, y, largura, altura, new Font("Arial", Font.PLAIN, 18));
    }

    private JButton criarBotao(String texto, int x, int y, int largura, int altura, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, largura, altura);
        botao.setBackground(cor);
        return botao;
    }

    private JComboBox<String> criarComboBox(String texto, int x, int y) {
        JLabel label = criarLabel(texto, x, y, 250, 25);
        JComboBox<String> comboBox = new JComboBox(new String[]{"A", "B", "C"});
        comboBox.setBounds(x, y + 25, 50, 25);
        add(label);
        add(comboBox);
        return comboBox;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new telaRefatorada().setVisible(true);
            }
        });
    }
}
