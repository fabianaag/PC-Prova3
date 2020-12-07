package Q3;

public class Main {
    public static void main(String[] args) {
        // O bar do Auri, tem uma mesa de 10 lugares
        // a única regra é que ninguém pode beber sozinho.

        Mesa mesa = new Mesa(3);
        mesa.start();

        Semaforo sem = new Semaforo();

        for (int i = 0; i < 15; i++) {
            Bebedor bb = new Bebedor(mesa, sem);
            bb.start();
        }

    }
}
