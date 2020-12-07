package Q3;

public class Main {

    public static void main(String[] args) {
        Mesa mesa = new Mesa();

        for (int i = 0 ; i < 6; i++) {
            Aluno a = new Aluno(mesa);
            a.start();
        }
    }
}
