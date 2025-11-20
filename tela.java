import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class tela extends JFrame {
    private JTextField textField;
    private JComboBox<String> origemBox;
    private JComboBox<String> destinoBox;
    private TorreDeHanoi torreDeHanoi;
    private JPanel torresPanel;
    private JLabel contadorLabel;
    private JLabel mensagemLabel;
    private int contador = 0; // Inicializa o contador
    private int movimentosIdeais = 0; // Inicializa a quantidade ideal de movimentos

    // Método para mover o disco e atualizar o contador
    private void moverDiscoEAtualizarContador(char origem, char destino) {
        Integer movimento = torreDeHanoi.moverDisco(origem, destino);
        if (movimento != null) {
            contador = movimento; // Atualiza o contador com o valor retornado pelo método
            contadorLabel.setText("Movimentos: " + contador + " / Movimentos ideais: " + movimentosIdeais); // Atualiza o texto do JLabel

            // Verifica se alguma das hastes está na configuração inicial
            if (torreDeHanoi.isVitoria()) {
                mensagemLabel.setText("<html><b><font color='green'>Parabens! Voce ganhou amigão.</font></b></html>");
                mensagemLabel.setVisible(true);
            }
        } else {
            // Movimento não permitido
            mensagemLabel.setText("<html><b><font color='red'>Opa! Esse movimento não é permitido :(</font></b></html>");
            mensagemLabel.setVisible(true); // Torna a mensagem visível
            // Remove a mensagem após um curto período de tempo
            Timer timer = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mensagemLabel.setVisible(false); // Torna a mensagem invisível
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public JPanel exibirTorre(char nomeHaste, Pilha torre) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
        // Adiciona um JLabel com o nome da haste
        JLabel nameLabel = new JLabel("Haste " + nomeHaste);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(nameLabel);
    
        for (int i = torre.getTamanhoMaximo() - 1; i >= 0; i--) {
            Disco disco = torre.getDisco(i);
            if (disco != null) {
                JLabel label = new JLabel("[" + String.valueOf(disco.getTamanho()) + "]");
                label.setFont(new Font("Arial", Font.BOLD, 24));
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(label);
            }
        }
    
        return panel;
    }

    public tela() {
        super("Sinfonia dos Aneis");
        setSize(850, 600);
        setLayout(null);

        JLabel label = new JLabel("A Sinfonia dos Aneis - Torre de Hanoi", SwingConstants.LEFT);
        label.setBounds(20, 0, 800, 50);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        getContentPane().add(label);

        JLabel inputLabel = new JLabel("Insira a quantidade de discos:", SwingConstants.LEFT);
        inputLabel.setBounds(20, 100, 250, 50);
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        getContentPane().add(inputLabel);

        textField = new JTextField();
        textField.setBounds(20, 150, 150, 25);
        getContentPane().add(textField);

        JButton iniciarButton = new JButton("Iniciar");
        iniciarButton.setBounds(20, 175, 150, 25);
        iniciarButton.setBackground(Color.GREEN);
        getContentPane().add(iniciarButton);

        // Adicionando JLabels para indicar as escolhas das hastes de origem e destino
        JLabel origemTextLabel = new JLabel("Escolha a haste de origem:");
        origemTextLabel.setBounds(20, 200, 200, 25);
        getContentPane().add(origemTextLabel);

        origemBox = new JComboBox<>(new String[]{"A", "B", "C"});
        origemBox.setBounds(20, 225, 50, 25);
        getContentPane().add(origemBox);

        JLabel destinoTextLabel = new JLabel("Escolha a haste de destino:");
        destinoTextLabel.setBounds(20, 250, 200, 25);
        getContentPane().add(destinoTextLabel);

        destinoBox = new JComboBox<>(new String[]{"A", "B", "C"});
        destinoBox.setBounds(20, 275, 50, 25);
        getContentPane().add(destinoBox);

        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numDeDiscos = Integer.parseInt(textField.getText());
                torreDeHanoi = new TorreDeHanoi(numDeDiscos);
                movimentosIdeais = (int) (Math.pow(2, numDeDiscos) - 1); // Calcula a quantidade ideal de movimentos

                // Cria um JPanel para mostrar as torres
                torresPanel = new JPanel();
                torresPanel.setLayout(new GridLayout(1, 3)); // Layout de grade para exibir as torres lado a lado

                // Adiciona os painéis das torres ao JPanel
                torresPanel.add(exibirTorre('A', torreDeHanoi.getTorre('A')));
                torresPanel.add(exibirTorre('B', torreDeHanoi.getTorre('B')));
                torresPanel.add(exibirTorre('C', torreDeHanoi.getTorre('C')));

                // Define a posição e o tamanho do JPanel
                torresPanel.setBounds(350, 150, 400, 600);

                // Adiciona o JPanel à janela
                getContentPane().add(torresPanel);

                // Adiciona o JLabel do contador de movimentos
                contadorLabel = new JLabel("Movimentos: 0 / Movimentos ideais: " + movimentosIdeais);
                contadorLabel.setBounds(20, 300, 400, 25);
                getContentPane().add(contadorLabel);

                // Adiciona o JLabel para exibir a mensagem de vitória
                mensagemLabel = new JLabel();
                mensagemLabel.setBounds(20, 350, 400, 25);
                getContentPane().add(mensagemLabel);

                // Atualiza a janela
                revalidate();
                repaint();
            }
        });

        JButton moverButton = new JButton("Mover disco");
        moverButton.setBounds(20, 325, 150, 25);
        moverButton.setBackground(Color.PINK);
        getContentPane().add(moverButton);

        moverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char origem = ((String) origemBox.getSelectedItem()).charAt(0);
                char destino = ((String) destinoBox.getSelectedItem()).charAt(0);
                moverDiscoEAtualizarContador(origem, destino);

                // Remove todos os componentes do JPanel das torres
                torresPanel.removeAll();

                // Adiciona os painéis das torres atualizadas ao JPanel
                torresPanel.add(exibirTorre('A', torreDeHanoi.getTorre('A')));
                torresPanel.add(exibirTorre('B', torreDeHanoi.getTorre('B')));
                torresPanel.add(exibirTorre('C', torreDeHanoi.getTorre('C')));

                // Atualiza o JPanel das torres
                torresPanel.revalidate();
                torresPanel.repaint();
            }
        });

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new tela().setVisible(true);
            }
        });
    }
}
