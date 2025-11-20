public class TorreDeHanoi {
    private Pilha torreA;
    private Pilha torreB;
    private Pilha torreC;
    private int contador = 0;

    // Constructor
    public TorreDeHanoi(int numDeDiscos) {
        torreA = new Pilha(numDeDiscos);
        torreB = new Pilha(numDeDiscos);
        torreC = new Pilha(numDeDiscos);

        for (int i = numDeDiscos; i > 0; i--) {
            torreA.push(new Disco(i));
        }
    }

    // Getter
    public Pilha getTorre(char idTorre) {
        switch (idTorre) {
            case 'A':
                return torreA;
            case 'B':
                return torreB;
            case 'C':
                return torreC;
            default:
                return null;
        }
    }

    // Método para verificar se alguma das torres está na configuração inicial (vitória)
    public boolean isVitoria() {
        return torreA.isPilhaVazia() && (torreB.isPilhaVazia() || torreC.isPilhaVazia());
    }

    // Lógica da movimentação dos discos
    public Integer moverDisco(char origem, char destino) {
        Pilha PilhaOrigem = getTorre(origem);
        Pilha PilhaDestino = getTorre(destino);

        if (origem == destino || PilhaOrigem.isPilhaVazia() || (PilhaDestino.peek() != null && PilhaOrigem.peek().getTamanho() > PilhaDestino.peek().getTamanho())) {
            return null;
        }
    
        PilhaDestino.push(PilhaOrigem.pop());
        contador++;
        return contador;

    }
}


