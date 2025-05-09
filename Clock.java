import java.util.*;

public class Clock {

    static class Pagina {
        int numero;
        boolean bitReferencia;

        public Pagina(int numero) {
            this.numero = numero;
            this.bitReferencia = true;
        }
    }

    private Pagina[] memoria;
    private int ponteiro;
    private int capacidade;
    private int pageFaults;

    public Clock(int capacidade) {
        this.capacidade = capacidade;
        this.memoria = new Pagina[capacidade];
        this.ponteiro = 0;
        this.pageFaults = 0;
    }

    public void acessarPagina(int numero) {
        boolean encontrado = false;

        for (Pagina p : memoria) {
            if (p != null && p.numero == numero) {
                p.bitReferencia = true;
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            pageFaults++;

            while (true) {
                if (memoria[ponteiro] == null) {
                    memoria[ponteiro] = new Pagina(numero);
                    avancarPonteiro();
                    break;
                } else if (!memoria[ponteiro].bitReferencia) {
                    memoria[ponteiro] = new Pagina(numero);
                    avancarPonteiro();
                    break;
                } else {
                    memoria[ponteiro].bitReferencia = false;
                    avancarPonteiro();
                }
            }
        }

        imprimirMemoria(numero, encontrado);
    }

    private void avancarPonteiro() {
        ponteiro = (ponteiro + 1) % capacidade;
    }

    private void imprimirMemoria(int numero, boolean hit) {
        //System.out.print("Acesso à página " + numero + " -> Memória: [ ");
        for (Pagina p : memoria) {
            if (p != null) {
                //System.out.print(p.numero + " ");
            } else {
                //System.out.print("_ ");
            }
        }
        //System.out.println("] | Page Fault: " + (hit ? "Não" : "Sim"));
    }

    public int getPageFaults() {
        return pageFaults;
    }

    public static void main(String[] args) {
        List<Integer> paginas = Arrays.asList(0, 1, 2, 3, 0, 1, 4, 0, 1, 2, 3, 4);
        int capacidade = 3;

        long inicio = System.nanoTime(); // Início do tempo

        Clock relogio = new Clock(capacidade);

        for (int pagina : paginas) {
            relogio.acessarPagina(pagina);
        }

        long fim = System.nanoTime(); // Fim do tempo

        //System.out.println("\nTotal de Page Faults: " + relogio.getPageFaults());

        long duracao = fim - inicio;
        //System.out.println("Tempo de execução: " + duracao + " ns");
        //System.out.printf("Tempo de execução: %.6f ms\n", duracao / 1_000_000.0);
    }
}
