import java.util.*;

public class App {

    static class Pagina {
        int numero;
        int contador;
        boolean referenciada;

        public Pagina(int numero) {
            this.numero = numero;
            this.contador = 0;
            this.referenciada = true;
        }

        @Override
        public String toString() {
            return numero + ":" + String.format("%8s", Integer.toBinaryString(contador & 0xFF)).replace(' ', '0');
        }
    }

    private int capacidade;
    private int pageFaults;
    private List<Pagina> memoria = new ArrayList<>();

    public App(int capacidade) {
        this.capacidade = capacidade;
        this.pageFaults = 0;
    }

    public void acessoPagina(int numero) {
        //System.out.println("\nAcessando página: " + numero);
        boolean achou = false;

        for (Pagina p : memoria) {
            if (p.numero == numero) {
                p.referenciada = true;
                achou = true;
                //System.out.println("Página " + numero + " já está na memória (HIT).");
                break;
            }
        }

        if (!achou) {
            pageFaults++;
            //System.out.println("PAGE FAULT! Página " + numero + " não está na memória.");

            if (memoria.size() == capacidade) {
                atualizarContadores(); // Atualiza antes de decidir quem sai
                Pagina menosUsada = Collections.min(memoria, Comparator.comparingInt(p -> p.contador));
                //System.out.println("Removendo página menos usada: " + menosUsada.numero);
                memoria.remove(menosUsada);
            }

            Pagina nova = new Pagina(numero);
            memoria.add(nova);
        }

        atualizarContadores();
        mostrarEstadoMemoria();
    }

    private void atualizarContadores() {
        for (Pagina p : memoria) {
            p.contador >>= 1;
            if (p.referenciada) {
                p.contador |= 0b10000000;
                p.referenciada = false;
            }
        }
    }

    private void mostrarEstadoMemoria() {
        //System.out.print("Estado da memória: ");
        for (Pagina p : memoria) {
            //System.out.print("[" + p + "] ");
        }
        //System.out.println();
    }

    public int getPageFaults() {
        return pageFaults;
    }

    public static void main(String[] args) {
        List<Integer> paginas = Arrays.asList(0, 1, 2, 3, 0, 1, 4, 0, 1, 2, 3, 4);
        int capacidade = 3;

        long inicio = System.nanoTime();

        App aging = new App(capacidade);
        for (int pagina : paginas) {
            aging.acessoPagina(pagina);
        }

        long fim = System.nanoTime();
        long tempo = fim - inicio;

        //System.out.println("\nTotal de page faults: " + aging.getPageFaults());
        //System.out.println("Tempo de execução: " + tempo + " ns");
        //System.out.printf("Tempo de execução: %.6f ms\n", tempo / 1_000_000.0);
    }
}
