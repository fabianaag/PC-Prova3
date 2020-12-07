package TESTE;

public class Main {

    public static void main(String[] args) {
        Mesa mesa = new Mesa();

        for (int i = 0 ; i < 10; i++) {
            Aluno a = new Aluno(mesa);
            a.start();
        }
    }
}
