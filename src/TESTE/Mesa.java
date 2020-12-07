package TESTE;

import java.util.ArrayList;

public class Mesa {
    private int capacidade = 4;
    private ArrayList<Aluno> alunos = new ArrayList<>(capacidade);

    public boolean temEspacoDisponivel() {
        synchronized (alunos) {
            return alunos.size() < capacidade;
        }
    }

    public boolean sentar(Aluno aluno) {
        synchronized (alunos) {
            if (alunos.size() < capacidade && !alunos.contains(aluno)) {
                System.out.println("Aluno " + aluno.getId() + " sentou na mesa");
                return alunos.add(aluno);
            }
            return false;
        }
    }

    public void levantar(Aluno aluno) {
        synchronized (alunos) {
            alunos.remove(aluno);
        }
    }

    public synchronized ArrayList<Aluno> getAlunos() {
        return alunos;
    }
}
