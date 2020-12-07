package Q3;

import java.util.HashMap;

public class Mesa extends Thread {
    // Bebedores serão identificados com um Mapa para otimizar busca
    private HashMap<Long, Bebedor> bebedores;
    private int capacidade;

    public Mesa(int lugares) {
        bebedores = new HashMap();
        capacidade = lugares;
    }

    public synchronized boolean sentar(Bebedor b) {
        if (bebedores.size() < capacidade && !bebedores.containsKey(b.getId())) {
            bebedores.put(b.getId(), b);
            System.out.println("Bebedor " + b.getId() + " sentou na mesa");
            return true;
        }
        return false;
    }

    public synchronized void levantar(Bebedor b) {
        if (bebedores.size() > 0 && bebedores.containsKey(b.getId())) {
            bebedores.remove(b.getId());
        }
    }

    public synchronized HashMap<Long, Bebedor> getBebedores() {
        return bebedores;
    }
}
