import java.util.Random;
import javax.swing.JOptionPane;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Main instance = new Main();
        instance.execute();
    }

    public void execute() throws InterruptedException {
        String s = JOptionPane.showInputDialog(null,"Insira o tamanho do vetor: ");      
        int tam = Integer.parseInt(s);
        
        s = JOptionPane.showInputDialog(null,"Insira a quantidade de threads: ");
        int nThreads = Integer.parseInt(s);
        
        if(tam<nThreads){
            JOptionPane.showMessageDialog(null, "Quantidade de threads superior ao tamanho do vetor");
            System.exit(0);
        }
        int resto = tam%nThreads;
        int tamV = tam;
        
        if(resto != 0){
            while(tamV%nThreads !=0)
                tamV++;
        }
        
        int [] vetorA = new int [tamV];
        int [] vetorB = new int [tamV];
        int [] vetorResultado = new int [tamV];
        
        Random r = new Random();
        
        System.out.println("Vetor A:");
        for (int i =0; i<tam; i++){
            vetorA[i] = r.nextInt(1000);
            System.out.print(vetorA[i] + " ");
        }
        System.out.println();
        
        System.out.println("Vetor B:");
        for (int i =0; i<tam; i++){
            vetorB[i] = r.nextInt(1000);
            System.out.print(vetorB[i] + " ");
        }
        System.out.println();
        
        MinhaThread [] vetorThreads = new MinhaThread[nThreads];
        
        for(int i = 0; i<nThreads; i++){
            vetorThreads[i] = new MinhaThread(vetorA,vetorB,vetorResultado,nThreads);
        }
        
        for(int i = 0; i<nThreads; i++){
            vetorThreads[i].start();
        }
        
        for(int i = 0; i<nThreads; i++){
            vetorThreads[i].join();
        }
        
        System.out.println("Vetor Resultado:");
        for (int i = 0; i < tamV; i++) {
            System.out.print(vetorResultado[i] + " ");
        }
        System.out.println();
    }

    public class MinhaThread extends Thread {
        int [] vetorA, vetorB,vetorResultado;
        int nThreads;
        
        MinhaThread(int [] vetorA, int [] vetorB, int [] vetorResultado, int nThreads){
            this.vetorA=vetorA;
            this.vetorB=vetorB;
            this.vetorResultado=vetorResultado;
            this.nThreads=nThreads;
        }
    
        @Override
        public void run(){
            String nome = this.getName();
            char c = nome.charAt(nome.length()-1);
            int id = Integer.parseInt(c+"");
            
            int parte = vetorA.length/nThreads; //100
            int salto = id*parte;
            
            for(int i=salto;i<(salto+parte);i++){
                vetorResultado[i] = vetorA[i]+vetorB[i];
            }
            
            vetorResultado[id] = vetorA[id]+vetorResultado[id];
        }
    }
}
