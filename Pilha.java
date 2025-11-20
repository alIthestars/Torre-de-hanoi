import java.util.Arrays;

public class Pilha {

    private int tamanhoMaximo;
    private Disco[] stackArray;
    private int ultimoElemento;

    public Pilha(int tamanho){
        tamanhoMaximo = tamanho;
        stackArray = new Disco[tamanhoMaximo];
        ultimoElemento = -1;
    }
   
    public void push(Disco disco){
        if(!isPilhaCheia()){
              ultimoElemento++;
              stackArray[ultimoElemento] = disco;
        }
    }
    
    public Disco pop(){
        if(isPilhaVazia()){
            return null;
        }

        int posicaoUltimoElemento = ultimoElemento;
        ultimoElemento--;
        Disco antigoUltimoElemento = stackArray[posicaoUltimoElemento];
        stackArray[posicaoUltimoElemento] = null;            
        
        return antigoUltimoElemento;        
    }
    
    public Disco peek(){
        if(isPilhaVazia()){
            return null;
        }
        return stackArray[ultimoElemento];
    }

    public boolean isPilhaVazia() {
        return ultimoElemento == -1;
    }
    
    public boolean isPilhaCheia() {
        return ultimoElemento == tamanhoMaximo - 1;
    }

    public int getTamanhoMaximo() {
        return tamanhoMaximo;
    }

    public Disco getDisco(int index) {
        if (index >= 0 && index <= ultimoElemento) {
            return stackArray[index];
        } else {
            return null;
        }
    }

    @Override
    public String toString(){
        return "Pilha: " + Arrays.toString(stackArray);
    }
}


