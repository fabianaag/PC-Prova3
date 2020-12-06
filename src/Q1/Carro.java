package Q1;

import java.util.ArrayList;

public class Carro extends Thread {
    public int capacidade;
    public ArrayList<Passageiro> passageiros;

    public boolean estaCarregando;
    public boolean estaDescarregando;

    // Criando travas(locks) de carregamento supondo que vários carros pudessem existir
    private final Object carregamentoTrava = new Object();
    private final Object descarregamentoTrava = new Object();

    public Carro(int C) {
        capacidade = C;
        passageiros = new ArrayList<Passageiro>(C);
        estaCarregando = true;
    }

    public void correr() {
        System.out.println("Carro atingiu capacidade, irá correr");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Carro chegou ao destino, irá descarregar passageiros");
    }

    public void embarcar(Passageiro t) {
        synchronized (carregamentoTrava) {
            if (estaCarregando && passageiros.size() < capacidade && !passageiros.contains(t)) {
                System.out.println("Carro sendo embarcado por Thread " + t.getName());
                passageiros.add(t);
            }
        }
    }

    public synchronized void desembarcar(Passageiro t) {
        synchronized (descarregamentoTrava) {
            if (estaDescarregando && passageiros.size() > 0 && passageiros.contains(t)) {
                System.out.println("Carro sendo desembarcado por Thread " + t.getName());
                passageiros.remove(t);
            }
        }
    }


    @Override
    public void run() {
        while (true) {
            synchronized (passageiros) {
                if (this.passageiros.size() < this.capacidade && !estaDescarregando) {
                    System.out.println("Esperando mais passageiros");
                } else if (this.passageiros.size() == this.capacidade) {
                    correr();
                    descarregar();
                } else if (this.passageiros.size() == 0) {
                    System.out.println("Todos passageiros entregues, carregando novos passageiros");
                    carregar();
                }
            }

            // Carro espera 1seg para próxima ação
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized void carregar() {
        estaCarregando = true;
        estaDescarregando = false;
    }

    private synchronized void descarregar() {
        estaDescarregando = true;
        estaCarregando = false;
    }
}
